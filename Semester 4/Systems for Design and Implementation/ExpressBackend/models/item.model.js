const itemsQuery = require("../helpers/itemsQueries");
const characterQuery = require("../helpers/charactersQueries");
let items = [];
let fakeItems=[];
const itemName=["Halberd", "Dragon Heart","Noble Sorcerer Ashes","Roar","Flamedrake Talisman","Tailoring Tools",
    "Bone Peddler's Bell", "Battle Hammer","Aspects of the Crucible: Tail","Barricade Shield","Repeating Hit","Nightrider Glaive",
    "Assassin's Crimson Dagger","Deathroot","Talisman Pouch","Banished Knight Oleg","Blue-danger Charm","Blue-feathered Branchsword",
    "Grovel for Mercy","Patches' Bell Bearing","Aspects of the Crucible: Horn", "Somber Smithing Stone","Old Fang","Beast Blood","Godrick's Great Rune",
    "Rememberance of the Grafted","Demi-human ashes","Spelldrake Talisman","Nightrider Flail","Lhutel the Headless","Rusted Anchor","Radagon's Scarseal",
    "Opaline Bubbletear","Crimsonburst crystaltear","Ornamental Straight Sword","Gold Beast Crest Shield","Winged Sword Insignia","Flame of the Fell God",
    "Skeletal Bandit Ashes","Kaiden Sellsword Ashes","Giant Hunt","Ice Spear","Holy-shrouding cracked tear","Lightning-shrouding cracked tear","Magic-shrouding cracked tear",
    "Cerulean crystal tear","Ruptured crystal tear","Smithing-Stone Miner's Bell Bearing","Memory Stone","Crystal release","Great Rune of the Unborn","Rememberance of the Full-Moon",
    "Moon Queen","Meteorite","Loretta's Greatbow","Loretta's Slash","Greatblade Phalanx","Black Knife Tiche","Glintstone Sprcerer Ashes","Crucible Knot Talisman",
    "Adula's Moonblade","Ancient Death Rancor","Meat Peddler's Bell Bearing","Red-feathered Branchsword","Assassin's Cerulean Dagger","Black Knifeprint","Cerulean Amber Medallion",
    "Twinsage Sorcerer ashes","Godskin Noble hood","Godskin Noble robe","Godskin Noble bracelets","Godskin Noble trousers","Magma Wyrm's Scalesword","Moonveil","Greenburst crystal tear",
    "Flame-shrouding cracked tear","Mad Pumpkin Head ashes","Poison Moth Flight","Commander's Standard","Unalloyed Gold Needle","Nox Flowing Sword","Gargoyle's Black Axe","Gargoyle's Black Blades",
    "Somberstone Miner Bell Bearing","Gravity Stone Chunk","Radahn's Great Rune","Rememberance of the Starscourge","Death's Poker","Putrid Corpse ashes","Gold Scarab","Battlemage Hugues","Kindred of Rot ashes",
    "Crystal Torrent","Ruins Greatsword","Godskin Apostle Hood","Godskin Apostle Robes","Opaline Hardtear","Stonebard cracked tear","Bloodhound's Step","Redmane Knight Ogha's spirit ashes","Golden Seed",
    "Great Club","Marais Executioner's Sword","Briar Greatshield","Black Knife","Godskin Peeler","Scouring Black Flame","Tibia's Summons","Glovewort Picker's Bell Bearing","Hero's Rune",
    "Erdtree Greatshield","Dragon Greatclaw","Dragonclaw Shield","Godfrey Icon","Ritual Sword talisman","Concealing Veil","Shared Order","Crimsonspill crystal tear","Speckled Hardtear",
    "Great Omenkiller Cleaver","Gravity Stone Fan","Ancient Dragon Knight Kristoff ashes","Lansseax's Glaive","Perfumer Tricia ashes","Leaden Hardtear","Cerulean Hidden Tear","Fallingstar Beat Jaw","Kindrer of Rot's Exultation",
    "Inquisitor's Girandole","Jar Cannon","Rykard's Great Rune","Rememberance of the Blasphemous","Magma Breath","Bloodhound Knight Fioh ashes","Godskin Stitcher","Noble Presence","Medicine Peddler's Bell Bearing",
    "Ordovis's Greatsword ashes","Crucible Helm","Crucible Armour","Crucible Gauntlets","Crucible Greaves","Omenkiller Rollo ashes","Rememberance of the Omen King","Morgott's Great Rune","Onyx Lord's Greatsword",
    "Lord's Rune","Rememberance of Hoarah Loux","Twinbird Kite Shield","Viridian Amber Medallion","Soldjard of Fortune ashes","Phantom Slash","Great Grave Glovewort","Rotten Gravekeeper's Cloak","Blackflame Monk Amon ashes",
    "Night Cavalry armour","Night Cavalry helm","Ancient Dragon Smithing Stone","Meteorite of Astel","Golden Order Greatsword","Rimed Rowa","Explosive Ghostflame","Godskin Swaddling Cloth","Black Flame Ritual","Rememberance of the Fire Giant",
    "Veteran's Prosthesis","Fingerprint helm","Fingerprint Armour","Vyke's Dragonbolt","Death Ritual Spear","Zamor Curved Sword","Zamor Mask","Golden Seed","Meteorite of Astel",
    "Loretta's Mastery","Loretta's War Sickle","Malenia's Great Rune","Rememberance of the Rot Goddess","Ancestral Follower ashes","Rememberance of the Regal Ancestor","Silver Tear Mask","Mogh's Great Rune","Rememberance of the Blood Lord",
    "Fia's Mist","Siluria's Tree","Rememberance of the Lichdragon","Staff of the Avatar","Dragonscale Blade","Frozen Lightning Spear","Rememberance of the Naturalborn","Black Flame Tornado","Rememberance of the Black Blade","Rememberance of the Dragonlord","Malformed Dragon helm",
    "Bloodflame Talons","Scepter of the All-Knowing","Elden Rememberance"];
const {faker} = require('@faker-js/faker');

function generateItem(cid) {
    return {
        cid:cid,
        itemName:faker.helpers.arrayElement(itemName),
        itemDrop:faker.number.int({min:1000,max:100000}),
        runeCount:faker.number.int({min:1000,max:100000}),
    };
}

const getNewItemId = async () => {
    let lid = await itemsQuery.getMaxLid() ? await itemsQuery.getMaxLid(): 0;
    lid = lid.lid+1;
    if(lid)
        return lid;
    return 1;
}
const generateItems = async (uid)=>{
    const lid = {lid: await getNewItemId()};
    let tempcid = await characterQuery.getMaxCidByUid(uid);
    let cid = tempcid.cid;
    let newItem = generateItem(cid);
    newItem = {...lid, ...newItem};
    fakeItems.push(newItem);
    return fakeItems;
};

function itemMustBeInArray(array, lid) {
    return new Promise((resolve, reject) => {
        const row = array.find(it => it.lid === parseInt(lid));
        if (!row) {
            reject({
                message: `LID ${lid} does not exist in array.`,
                status: 404
            })
        }
        resolve(row)
    })
}
const loadItemsFromFile = async (array, uid) => {
    try {
        //const fileContent = fs.readFileSync("./items.json", { encoding: 'utf8' });
        //const parsedItems = JSON.parse(fileContent);
        const parsedItems = await itemsQuery.readAll(uid);
        //console.log("items read:", parsedItems, uid);
        //console.log("items from db: ",parsedItems);
        //console.log(parsedItems);
        if (parsedItems.length < 1) {
            //console.log("inside items");
            //let generated= await generateItems(array);
            //console.log("uid", uid);
            //console.log(generated);
            //await delay(3000);
            //console.log(generated);
            items = await generateItems(uid);
            //console.log("items?:", items, uid);
            await itemsQuery.saveAll(items, uid);
            const readItems = await itemsQuery.readAll(uid);
            await setItems(readItems);
            return readItems;
        }
        return Promise.resolve(parsedItems);
    } catch (error) {
        if (error.code === 'ENOENT') {
            console.error(`File ./items.json not found.`);
        } else {
            console.error('Error reading file:', error.message);
        }
        return Promise.reject({
            message: 'Error reading items data',
            status: 500
        });
    }
};

async function setItems(itemData) {
    if (itemData instanceof Promise) {
        itemData.then(data => {
            items = data;
            itemsQuery.saveAll(items);
        }).catch(error => {
            console.error('Error setting items:', error.message);
        });
    } else {
        items = itemData;
        await itemsQuery.saveAll(items);
    }
}

async function getItems(uid) {
    if(items.length<1)
        await loadItemsFromFile(items, uid);
    return new Promise((resolve, reject) => {
        if (items instanceof Promise) {
            items.then(data => {
                if (Array.isArray(data) && data.length > 0) {
                    let tempItems=data.slice();
                    resolve(tempItems.sort((a, b) => a.itemName.localeCompare(b.itemName)));
                } else {
                    reject({
                        message: 'no items available',
                        status: 202
                    });
                }
            }).catch(error => {
                reject({
                    message: 'Error reading items data',
                    status: 500
                });
            });
        } else if (Array.isArray(items) && items.length > 0) {
            let tempItems = items.slice();
            resolve(tempItems.sort((a, b) => a.itemName.localeCompare(b.itemName)));
        } else {
            reject({
                message: 'no items available',
                status: 202
            });
        }
    });
}

function getUnsortedItems(){
    return new Promise((resolve, reject) => {
        if (items instanceof Promise) {
            items.then(data => {
                if (Array.isArray(data) && data.length > 0) {
                    resolve(items);
                } else {
                    reject({
                        message: 'no items available',
                        status: 202
                    });
                }
            }).catch(error => {
                reject({
                    message: 'Error reading items data',
                    status: 500
                });
            });
        } else if (Array.isArray(items) && items.length > 0) {
            resolve(items);
        } else {
            reject({
                message: 'no characters available',
                status: 202
            });
        }
    });
}

function getItem(lid) {
    return new Promise((resolve, reject) => {
        if (items instanceof Promise) {
            items
                .then(data => {
                    itemMustBeInArray(data, lid)
                        .then(item => resolve(item))
                        .catch(err => reject(err));
                })
                .catch(error => reject({ message: 'Error reading items data', status: 500 }));
        } else if (Array.isArray(items)) {
            itemMustBeInArray(items, lid)
                .then(item => resolve(item))
                .catch(err => reject(err));
        } else {
            reject({ message: 'No items available', status: 202 });
        }
    });
}


async function addItem(newItem,uid) {
    let lid;
    await itemsQuery.getMaxLid().then(data=>{lid=data.lid+1;});
    return new Promise((resolve, reject) => {
        if (items instanceof Promise) {
            items
                .then(data => {
                    if (!newItem.itemName || !newItem.cid ||parseInt(newItem.cid) <= 0 || isNaN(parseInt(newItem.cid))|| parseInt(newItem.itemDrop) <= 0 || isNaN(parseInt(newItem.itemDrop)) ||  parseInt(newItem.runeCount) <= 0 || isNaN(parseInt(newItem.runeCount)) ) {
                        reject({ status: 400, message: `Missing required fields` });
                        return;
                    }
                    //const lid = getNewItemId(data);
                    newItem.lid=lid;
                    //newItem = { ...lid, ...newItem };
                    data.push(newItem);
                    items=data;
                    itemsQuery.create(newItem,uid);
                    setItems(itemsQuery.readAll(uid));
                    resolve(newItem);
                })
                .catch(error => reject({ message: 'Error reading items data', status: 500 }));
        } else if (Array.isArray(items)) {
            if (!newItem.itemName || !newItem.cid ||parseInt(newItem.cid) <= 0 || isNaN(parseInt(newItem.cid))||parseInt(newItem.itemDrop) <= 0 || isNaN(parseInt(newItem.itemDrop)) ||  parseInt(newItem.runeCount) <= 0 || isNaN(parseInt(newItem.runeCount)) ) {
                reject({ status: 400, message: `Missing required items fields` });
                return;
            }
            newItem.lid=lid;
            //newItem = { ...lid, ...newItem };
            items.push(newItem);
            itemsQuery.create(newItem,uid);
            setItems(itemsQuery.readAll(uid));
            resolve(newItem);
        } else {
            reject({ message: 'No items available', status: 202 });
        }
    });
}


async function updateItem(lid, newItem, uid) {
    return new Promise((resolve, reject) => {
        if (items instanceof Promise) {
            items
                .then(data => {
                    itemMustBeInArray(data, parseInt(lid))
                        .then(item => {
                            const index = data.findIndex(it => it.lid === parseInt(lid));
                            if (index === -1) {
                                reject({ status: 404, message: 'Item not found' });
                                return;
                            }
                            newItem.lid=lid;
                            data[index] = newItem;
                            itemsQuery.update(lid,data[index]);
                            setItems(itemsQuery.readAll(uid));
                            resolve(data[index]);
                        })
                        .catch(err => reject(err));
                })
                .catch(error => reject({ message: 'Error reading ITEMS data', status: 500 }));
        } else if (Array.isArray(items)) {
            itemMustBeInArray(items, parseInt(lid))
                .then(item => {
                    const index = items.findIndex(it => it.lid === parseInt(lid));
                    if (index === -1) {
                        reject({ status: 404, message: 'Item not found' });
                        return;
                    }
                    newItem.lid=lid;
                    items[index] = newItem;
                    itemsQuery.update(lid,items[index]);
                    setItems(itemsQuery.readAll(uid));
                    resolve(items[index]);
                })
                .catch(err => reject(err));
        } else {
            reject({ message: 'No items available', status: 202 });
        }
    });
}

async function deleteItem(lid,uid) {
    return new Promise(async (resolve, reject) => {
        try {
            if (items instanceof Promise) {
                items.then(data => {
                    try{
                        if (Array.isArray(data) && data.length > 0) {
                            itemMustBeInArray(data, lid);
                            data = data.filter(item => item.lid !== lid);
                            itemsQuery.deleteItem(lid);
                            setItems(itemsQuery.readAll(uid));
                            //writeItemsToFile(data);
                            items=data;
                            resolve();
                        }}
                    catch (err) {
                        reject({ status: 404, message: `LID ${lid} does not exist in array.` });
                    }
                });
            }
            else{
                try {
                    await itemMustBeInArray(items, lid);
                    items = items.filter(item => item.lid !== lid);
                    await itemsQuery.deleteItem(lid);
                    await setItems(itemsQuery.readAll(uid));
                    resolve();
                } catch (err) {
                    reject({ status: 404, message: `LID ${lid} does not exist in array.` });
                }
            }
        } catch (err) {
            reject(err);
        }
    });
}

async function getPaginatedItems(page, pageSize, uid) {
    //console.log("uid",uid);
    //console.log(items);
    if(items.length<1)
        await loadItemsFromFile(items, uid);
    return new Promise((resolve, reject) => {
        if (page <= 0 || pageSize <= 0) {
            reject({ status: 400, message: 'Invalid page number or page size for items' });
            return;
        }

        if (items instanceof Promise) {
            items.then(data => {
                //console.log(data);
                if (Array.isArray(data) && data.length > 0) {
                    const startIndex = (page - 1) * pageSize;
                    const endIndex = page * pageSize;

                    const paginatedItems = data
                        .slice()
                        .sort((a, b) => a.itemName.localeCompare(b.itemName))
                        .slice(startIndex, endIndex);
                    //console.log("pitems",paginatedItems);
                    resolve(paginatedItems);
                } else {
                    reject({
                        message: 'No items available',
                        status: 202
                    });
                }
            }).catch(error => {
                reject({
                    message: 'Error reading items data',
                    status: 500
                });
            });
        } else if (Array.isArray(items) && items.length > 0) {

            const startIndex = (page - 1) * pageSize;
            const endIndex = page * pageSize;
            const paginatedItems = items
                .slice()
                .sort((a, b) => a.itemName.localeCompare(b.itemName))
                .slice(startIndex, endIndex);
            //console.log("pitems",paginatedItems);
            resolve(paginatedItems);
        } else {
            reject({
                message: 'No items available',
                status: 202
            });
        }
    });
}

async function deleteItemsForCID(cid) {
    try {
        let data = await items;
        const itemsToDelete = data.filter(item => item.cid === cid);
        if (itemsToDelete.length > 0) {
            //console.log(cid);
            itemsToDelete.map(item => deleteItem(item.lid));
            //const itemsToKeep = data.filter(item => item.cid !== cid);
            //await setItems(itemsToKeep);
            //console.log(items);
            console.log("Items deleted successfully");
        } else {
            console.log("No items to delete");
        }
    } catch (error) {
        console.error('Error deleting items:', error);
    }
}


module.exports = {
    setItems,
    addItem,
    getItems,
    getUnsortedItems,
    getPaginatedItems,
    getItem,
    updateItem,
    deleteItem,
    deleteItemsForCID
};
