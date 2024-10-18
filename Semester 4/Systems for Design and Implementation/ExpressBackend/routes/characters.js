// routes/characters.js

const express = require('express');
const router = express.Router();

const character = require('../models/character.model')
const m = require('../helpers/middlewares')
const passport = require('passport');

// GET all characters
router.get('/all',passport.authenticate('jwt', { session: false }), async (req, res, next) => {
    try {
        const userId = req.user.uid;
        const characters = await character.getCharacters(userId);
        res.json(characters);
    } catch (err) {
        if (err.status) {
            res.status(err.status).json({ message: err.message });
        } else {
            res.status(500).json({ message: err.message });
        }
    }
});

// GET all PAGINATED characters
router.get('/', passport.authenticate('jwt', { session: false }), async (req, res) => {
    try {
        const page = parseInt(req.query.page) || 1; // Default to page 1 if not provided
        const pageSize = parseInt(req.query.pageSize) || 5; // Default to 5 items per page if not provided
        const paginatedCharacters = await character.getPaginatedCharacters(page, pageSize, req.user.uid);
        //console.log("in chars route:", req.user.uid);
        const characters = await character.getCharacters(req.user.uid);
        const totalPages = Math.ceil(characters.length / pageSize);
        const charLength = paginatedCharacters.length;
        res.json({ paginatedCharacters, totalPages, charLength });
    } catch (err) {
        if (err.status) {
            res.status(err.status).json({ message: err.message });
        } else {
            res.status(500).json({ message: err.message });
        }
    }
});

// delete chars from frontend
// route for synchronizing characters (add/update)
router.post('/charsync',passport.authenticate('jwt', { session: false }),async (req, res) => {
    try {
        const charsR = req.body;
        const result = { added: [], updated: [] };
        console.log("chars sync",charsR);

        for (const char of charsR) {
            if (char.cid) {
                const cid = parseInt(char.cid);
                try {
                        await character.updateCharacter(cid, char, req.user.uid);
                        result.updated.push(cid);
                }
                catch (err){
                    const newCharacter = await character.addCharacter(char, req.user.uid);
                    result.added.push(newCharacter.cid);}
                ;
            } else {
                console.warn('Skipping character object without cid:', char);
            }
        }

        res.json({ message: 'Characters synchronized successfully', result });
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: err.message });
    }
});

// Route for synchronizing deleted characters
router.post('/chardelsync',passport.authenticate('jwt', { session: false }), async (req, res) => {
    try {
        const cids = req.body;
        const result = [];
        console.log("chars del",cids);

        for (const cide of cids) {
            const cidt = parseInt(cide.cid)
                await character.deleteCharacter(cidt, req.user.uid);
                result.push(cidt);
        }

        res.json({ message: 'Deleted characters synchronized successfully', result });
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: err.message });
    }
});

// GET a specific character by cid
router.get('/:cid',async (req, res) => {
    const cid = parseInt(req.params.cid)
        await character.getCharacter(cid)
            .then(character => res.json(character))
            .catch(err => {
                if (err.status) {
                    res.status(err.status).json({message: err.message})
                } else {
                    res.status(500).json({message: err.message})
                }
            })
})

// POST - Create a new character
router.post('/', m.checkFieldsCharacter, passport.authenticate('jwt', { session: false }), passport.authenticate('jwt', { session: false }),async (req, res,next) => {
    await character.addCharacter(req.body, req.user.uid)
        .then(character => res.status(201).json({
            message: `The character #${character.cid} has been created`,
            content: character
        }))
        .catch(err => res.status(500).json({ message: err.message }))
})

// PUT - Update a character by cid
router.put('/:cid', m.checkFieldsCharacter, passport.authenticate('jwt', { session: false }), async (req, res) => {
        const cid = parseInt(req.params.cid);
    try {
        const tempCharacter = await character.updateCharacter(cid, req.body, req.user.uid);
        res.json({
            message: `The character #${cid} has been updated`,
            content: tempCharacter
        });
    } catch (err) {
        if (err.status) {
            res.status(err.status).json({ message: err.message });
        } else {
            res.status(500).json({ message: err.message });
        }
    }
});


// DELETE - Delete a character by cid
router.delete('/:cid',  passport.authenticate('jwt', { session: false }),async (req, res) => {
    try {
            const cid = parseInt(req.params.cid);

            await character.deleteCharacter(cid, req.user.uid);

            res.json({
                message: `The character ${cid} has been deleted`
            });
    } catch (err) {
        if (err.status) {
            res.status(err.status).json({ message: err.message });
        } else {
            res.status(500).json({ message: err.message });
        }
    }
});

module.exports = router;
