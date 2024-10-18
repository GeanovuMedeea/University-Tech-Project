function mustBeInteger(req, res, next) {
    const id = req.params.id
    if (!Number.isInteger(parseInt(id))) {
        res.status(400).json({ message: 'CID must be an integer' })
    } else {
        next()
    }
}
function checkFieldsCharacter(req, res, next) {
    const { name, location, hp, strongVs, weakTo,immuneTo } = req.body
    if (name && location && hp && strongVs &&weakTo &&immuneTo && !isNaN(parseInt(hp)) && parseInt(hp)>0) {
        next()
    } else {
        console.log(name,location,hp,strongVs,weakTo,immuneTo);
        res.status(400).json({ message: 'fields are not good' })
    }
}

function checkFieldsItem(req, res, next) {
    const { cid, itemName, itemDrop, runeCount } = req.body
    if (cid && !isNaN(parseInt(cid)) && parseInt(cid)>0 && itemName && runeCount && !isNaN(parseInt(itemDrop)) && parseInt(itemDrop)>0&& !isNaN(parseInt(runeCount)) && parseInt(runeCount)>0) {
        next()
    } else {
        console.log(cid,itemName,itemDrop, runeCount);
        res.status(400).json({ message: 'fields are not good' })
    }
}
module.exports = {
    mustBeInteger,
    checkFieldsCharacter,
    checkFieldsItem
}
