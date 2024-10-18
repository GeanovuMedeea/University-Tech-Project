// routes/items.js

const express = require('express');
const router = express.Router();

const item = require('../models/item.model')
const m = require('../helpers/middlewares')
const passport = require('passport');

// GET all items

router.get('/all', passport.authenticate('jwt', { session: false }),async (req, res, next) => {
    try {
        const items = await item.getItems(req.user.uid);
        res.json(items);
    } catch (err) {
        if (err.status) {
            res.status(err.status).json({ message: err.message });
        } else {
            res.status(500).json({ message: err.message });
        }
    }
});

// Route for synchronizing items (add/update)
router.post('/itemsync',passport.authenticate('jwt', { session: false }),async (req, res) => {
    try {
        const items = req.body;
        console.log("item sync",items);
        const result = { added: [], updated: [] };

        for (const itemR of items) {
            if (itemR.lid) {
                const lid = parseInt(itemR.lid);
                try {
                    await item.updateItem(lid, itemR, req.user.uid);
                    result.updated.push(lid);
                }catch (err){
                    const newItem = await item.addItem(itemR,req.user.uid);
                    result.added.push(newItem.lid);
                };
            } else {
                console.warn('Skipping item object without lid:', itemR);
            }
        }

        res.json({ message: 'Items synchronized successfully', result });
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: err.message });
    }
});

// Route for synchronizing deleted items
router.post('/itemdelsync',passport.authenticate('jwt', { session: false }),async (req, res) => {
    try {
            const lids = req.body;
            console.log("item del", lids);
            const result = [];

            for (const lide of lids) {
                const lidt = parseInt(lide.lid)
                await item.deleteItem(lidt, req.user.uid);
                result.push(lidt);
            }

            res.json({message: 'Deleted items synchronized successfully', result});
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: err.message });
    }
});

// GET all PAGINATED items
router.get('/', passport.authenticate('jwt', { session: false }), async (req, res) => {
    try {
        const page = parseInt(req.query.page) || 1; // Default to page 1 if not provided
        const pageSize = parseInt(req.query.pageSize) || 5; // Default to 5 items per page if not provided
        //console.log("before items route:", req.user.uid);
        const paginatedItems = await item.getPaginatedItems(page, pageSize, req.user.uid);
        const items = await item.getItems(req.user.uid);
        //console.log("in items route:", req.user.uid);
        const totalPages = Math.ceil(items.length / pageSize);
        const itemLength = paginatedItems.length;
        res.json({ paginatedItems, totalPages, itemLength });
    } catch (err) {
        if (err.status) {
            res.status(err.status).json({ message: err.message });
        } else {
            res.status(500).json({ message: err.message });
        }
    }
});

// GET a specific item by lid
router.get('/:lid',  passport.authenticate('jwt', { session: false }),async (req, res) => {
        const lid = parseInt(req.params.lid)
        await item.getItem(lid)
            .then(item => res.json(item))
            .catch(err => {
                if (err.status) {
                    res.status(err.status).json({message: err.message})
                } else {
                    res.status(500).json({message: err.message})
                }
            })
})

// POST - Create a new item
router.post('/',passport.authenticate('jwt', { session: false }), m.checkFieldsItem, async (req, res,next) => {
    await item.addItem(req.body,req.user.uid)
        .then(item => res.status(201).json({
            message: `The item #${item.lid} has been created`,
            content: item
        }))
        .catch(err => res.status(500).json({ message: err.message }))
})

// PUT - Update a item by lid
router.put('/:lid', m.checkFieldsItem,  passport.authenticate('jwt', { session: false }), async (req, res) => {
        const lid = parseInt(req.params.lid)
        await item.updateItem(lid, req.body, req.user.uid)
            .then(item => res.json({
                message: `The item #${lid} has been updated`,
                content: item
            }))
            .catch(err => {
                if (err.status) {
                    res.status(err.status).json({message: err.message})
                }
                res.status(500).json({message: err.message})
            })
})

// DELETE - Delete a item by lid
router.delete('/:lid',  passport.authenticate('jwt', { session: false }),async (req, res) => {
        const lid = parseInt(req.params.lid)

        await item.deleteItem(lid,req.user.uid)
            .then((item) => res.json({
                message: `The item ${lid} has been deleted`
            }))
            .catch(err => {
                if (err.status) {
                    res.status(err.status).json({message: err.message})
                }
                res.status(500).json({message: err.message})
            })
})

module.exports = router;
