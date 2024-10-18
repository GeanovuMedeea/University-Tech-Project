const express = require('express');
const router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get("/", (req, res) => {
  res.send("Hello World!")
})

router.get("/ping", (req, res) => {
  res.send({ success: true })
})


module.exports = router;
