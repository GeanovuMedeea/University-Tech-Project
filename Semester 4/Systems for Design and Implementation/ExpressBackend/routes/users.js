const express = require('express');
const {create, getMaxUid, findByUsername} = require('../helpers/usersQueries');
const router = express.Router();
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');

const validateRequestBody = (req, res, next) => {
  if (!req.body.username ||!req.body.password) {
    return res.status(400).json({ message: 'Missing username or password.' });
  }
  next();
};

/*router.post('/register', async (req, res) => {
  const { username, password } = req.body;

  try {
    const hashedPassword = bcrypt.hashSync(password, 10); // Hash the password
    let uid;
    await getMaxUid().then(data=>{uid=data.cid+1;});
    const newUser = { username, password: hashedPassword }; // Assuming you have a way to generate a UID or similar
    const result = await create(newUser); // Adjust this to match your user creation logic

    if (result) {
      res.status(201).send('User registered successfully');
    } else {
      res.status(500).send('Failed to register user');
    }
  } catch (error) {
    console.error('Registration error:', error);
    res.status(500).send('Internal server error');
  }
});*/

router.post('/register', async (req, res) => {
  try {
    const { username, password } = req.body;
    const hashedPassword = await bcrypt.hash(password, 10);
    let uid = await getMaxUid();
    uid = uid.uid+1;
    console.log("r uid:", uid);
    console.log("r username:", username);
    console.log("r hashed pass:",hashedPassword);
    const newUser = await create({ uid:uid, username, password: hashedPassword });
    const token = jwt.sign({ uid: newUser.uid }, process.env.JWT_SECRET, { expiresIn: '1h' });
    res.status(201).json({ token });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

router.post('/login', async (req, res) => {
  try {
    const { username, password } = req.body;
    console.log("l username:", username);
    console.log("l password:", password);
    const user = await findByUsername(username);
    if (!user || !await bcrypt.compare(password, user.password)) {
      return res.status(401).json({ message: 'Invalid username or password.' });
    }
    const token = jwt.sign({ uid: user.uid }, process.env.JWT_SECRET, { expiresIn: '1h' });
    res.json({ token });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});


router.get('/connection', async (req, res) => {
  try {
    let connection = {status: 'connected'};
    res.json(connection);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
});

/*router.post('/login', validateRequestBody, passport.authenticate('local', {
  successRedirect: '/', // Redirect to home page on successful login
  failureRedirect: '/login-failure' // Redirect to login failure page on unsuccessful login
}));*/

module.exports = router;
