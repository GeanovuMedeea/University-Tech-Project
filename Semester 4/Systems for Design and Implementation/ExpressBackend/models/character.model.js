const characterQuery = require('../helpers/charactersQueries');
let characters =[];
let tempuid = 1;
const {faker} = require('@faker-js/faker');
//const {faker} = require("@faker-js/faker");
function mustBeInArray(array, cid) {
    return new Promise((resolve, reject) => {
        const row = array.find(char => char.cid === parseInt(cid));
        if (!row) {
            reject({
                message: `CID ${cid} does not exist in array.`,
                status: 404
            })
        }
        resolve(row)
    })
}

const affinities = ["Scarlet Rot", "Fire", "Frostbite", "Holy", "Poison", "Sleep", "Hemorrhage", "Standard, Pierce, Slash, Strike"];

const names =["Radagon of the Golden Order", "Starscourge Radahn","Malenia, Blade of Miquella", "Elden Beast", "Maliketh, the Black Blade", "Rennala, Queen of the Full Moon",
    "Mimic Tear", "Astel, Naturalborn of the Void","Morgott, Omen King", "Margit, the Fell Omen", "Mogh, Lord of Blood", "Godskin Apostle",
    "Godskin Noble", "Fire Giant", "Dragonlord Placidusax", "Erdtree Sentinel", "Rykard, Lord of Blasphemy", "Azula, Glintsone Dragon",
    "Godrick the Grafted", "Vyke, Rountable Hold Knight", "Sir Gideon Ofnir, the All-knowing", "Godfrey, First Elden Lord", "Lichdragon Fortissax", "Bernahl, Recussant",
    "Crucible Knight", "Dragonkin Soldier of Nokstella", "Regal Ancestor Spirit", "Royal Knight Loretta", "Magma Wyrm Makar","Ancestor Spirit",
    "Commander Niall","Loretta, Knight of the Haligtree","Red Wolf of Radagon","Leonine Misbegotten","Hoarah Loux, Warrior","Grave Warden Duelist",
    "Soldier of Godrick","Beastman of Farum Azula","Bell Bearing Hunter","Black Knife Assassin","Bloodhound Knight Darriwil","Deathbird","Demi-Human Chief",
    "Erdtree Burial Watchdog","Flying Dragon Agheel","Grafted Scion","Mad Pumpkin Head","Night's Cavalry","Patches",
    "Stonedigger Troll","Tibia Mariner","Ulcerated Tree Spirit","Ancient Hero of Zamor","Cemetery Shade","Miranda the Blighted Bloom",
    "Runebear","Adan, Thief of Fire","Alabaster Lord","Alecto, Black Knife Ringleader","Bols, Carian Knight","Cleanrot Knight",
    "Crystalian","Death Rite Bird","Erdtree Avatar","Glintstone Dragon Smarag","Royal Revenant","Spirit-Caller Snail",
    "Commander O'Neil","Decaying Ekzykes","Frenzied Duelist","Fallingstar Beast","Nox Swordstress & Nox Priest","Battlemage Hugues",
    "Black Blade Kindred","Elder Dragon Greyoll","Flying Dragon Greyll","Ancient Dragon Lansseax","Demi-Human Queen Gilika","Elemer of the Briar",
    "Necromancer Garris","Miranda the Blighted Bloom","Perfumer Tricia","Sanguine Noble","Wormface","Red Wolf of the Champion",
    "Magma Wyrm","Full-Grown Fallingstar Beast","Demi-Human Queen Margot","Demi-Human Queen Maggie","Kindred of Rot","Ulcerated Tree Spirit",
    "Draconic Tree Sentinel","Fell Twins","Onyx Lord","Esgar, Priest of Blood","Borealis the Freezing Fog","Beast Clergyman",
    "Stray Mimic Tear","Astel, Stars of Darkness","Great Wyrm Theodorix","Misbegotten Crusader","Putrid Avatar","Valiant Gargoyle",
    "Crucible Knight Siluria","Fia's Champions"];

const location=["Isolated Divine Tower","UNDERGROUND","Ainsel River","Ainsel River Main","Cathedral of the Forsaken","Deeproot Depths","Frenzied Flame Proscription",
    "Grand Cloister","Hallowhorn Grounds","Lake of Rot","Mohgwyn Palace","Mohgwyn Dynasty Mausoleum","Night's Sacred Ground","Nokron, Eternal City","Nokstella, Eternal City",
    "Siofra Aqueduct","Siofra River","Subterranean Shunning-Grounds","Uhl Palace Ruins", "Stormveil Castle","Raya Lucaria Academy",
    "Leyndell, Royal Capital","Volcano Manor","Miquella's Haligtree/Elphael, Brace of the Haligtree","Crumbling Farum Azula","Elden Ring Minor Legacy Dungeons: Castle Morne",
    "Redmane Castle","The Shaded Castle","Caria Manor","Castle Sol","Ruin-Strewn Precipice","Leyndell, Ashen Capital","Subterranean Shunning-Grounds","Ainsel River",
    "Lake of Rot","Elden Ring Dungeons that allow the use of Torrent: Siofra River","Mohgwyn Palace","Nokron, Eternal City","Deeproot Depths",
    "Artist's Shack (Limgrave)","Bridge of Sacrifice","Chapel of Anticipation","Church of Dragon Communion","Church of Elleh",
    "Coastal Cave","Deathtouched Catacombs","Divine Tower of Limgrave","Dragon-Burnt Ruins","Forlorn Hound Evergaol","Fort Haight","Fringefolk Hero's Grave","Gatefront Ruins",
    "Groveside Cave","Highroad Cave","Limgrave Colosseum","Limgrave Tunnels","Minor Erdtree (Mistwood)","Mistwood","Mistwood Ruins","Murkwater Catacombs","Murkwater Cave",
    "Siofra River Well","Starfall Crater","Stormfoot Catacombs","Stormgate","Stormhill","Stormhill Evergaol","Stormhill Shack","Stranded Graveyard","Summonwater Village",
    "Third Church of Marika","Warmaster's Shack","Waypoint Ruins","Ailing Village","Bridge of Sacrifice","Callu Baptismal Church","Castle Morne",
    "Church of Pilgrimage","Demi-Human Forest Ruins","Earthbore Cave","Forest Lookout Tower","Fourth Church of Marika","Impaler's Catacombs","Isolated Merchant's Shack","Minor Erdtree (Weeping Peninsula)",
    "Morne Tunnel","Oridys's Rise","Tombsward Catacombs","Tombsward Cave","Tombsward Ruins","Tower of Return","Weeping Evergaol","Witchbane Ruins",
    "Academy Crystal Cave","Academy Gate Town","Ainsel River Well","Artist's Shack (Liurnia)","Bellum Church","Bellum Highway","Black Knife Catacombs","Boil Prawn Shack","Caria Manor",
    "Carian Study Hall","Cathedral of Manus Celes","Chelona's Rise","Church of Inhibition","Church of Irith","Church of Vows","Cliffbottom Catacombs","Converted Fringe Tower",
    "Converted Tower","Cuckoo's Evergaol","Deep Ainsel Well","Divine Tower of Liurnia","Frenzied Flame Village","Frenzy-Flaming Tower","Grand Lift of Dectus","Highway Lookout Tower",
    "Jarburg","Kingsrealm Ruins","Lakeside Crystal Cave","Laskyar Ruins","Lunar Estate Ruins","Malefactor's Evergaol","Minor Erdtree (Liurnia Northeast)","Minor Erdtree (Liurnia Southwest)",
    "Moonfolk Ruins","Moonlight Altar","Purified Ruins","Ranni's Rise","Raya Lucaria Academy","Raya Lucaria Crystal Tunnel","Renna's Rise","Revenger's Shack","Ringleader's Evergaol",
    "Road's End Catacombs","Rose Church","Royal Grave Evergaol","Ruin-Strewn Precipice","Seluvis's Rise","Slumbering Wolf's Shack","Stillwater Cave","Temple Quarter","Testu's Rise",
    "The Four Belfries","Village of the Albinaurics","Caelem Ruins","Caelid Catacombs","Caelid Colosseum","Caelid Waypoint Ruins","Cathedral of Dragon Communion","Church of the Plague",
    "Divine Tower of Caelid","Forsaken Ruins","Fort Gael","Gael Tunnel","Gaol Cave","Gowry's Shack","Minor Erdtree (Caelid)","Minor Erdtree Catacombs","Redmane Castle","Sellia Crystal Tunnel",
    "Sellia Gateway","Sellia, Town of Sorcery","Shack of the Rotting","Smoldering Church","Street of Sages Ruins","Swamp Lookout Tower","Swamp of Aeonia","Wailing Dunes","War-Dead Catacombs"
    ,"Abandoned Cave","Bestial Sanctum","Dragonbarrow Cave","Divine Tower of Caelid","Deep Siofra Well","Fort Faroth","Isolated Merchant's Shack","Lenne's Rise","Minor Erdtree (Dragonbarrow)",
    "Sellia Evergaol","Sellia Hideaway","Altus Tunnel","Dominula, Windmill Village","East Windmill Pasture","Golden Lineage Evergaol","Grand Lift of Dectus","Highway Lookout Tower",
    "Lux Ruins","Minor Erdtree (Altus Plateau)","Mirage Rise","Old Altus Tunnel","Perfumer's Grotto","Perfumer's Ruins","Sage's Cave","Sainted Hero's Grave","Second Church of Marika",
    "Stormcaller Church","The Shaded Castle","Unsightly Catacombs","Village Windmill Pasture","West Windmill Pasture","Woodfolk Ruins","Writheblood Ruins","Wyndham Catacombs","Wyndham Ruins",
    "Corpse-Stench Shack","Craftsman's Shack","Fort Laiedd","Gelmir Hero's Grave","Hermit Village","Hermit's Shack","Minor Erdtree (Mt. Gelmir)","Seethewater Cave","Volcano Cave","Volcano Manor",
    "Auriza Hero's Grave","Auriza Side Tomb","Divine Tower of East Altus","Divine Tower of West Altus","Hermit Merchant's Shack","Minor Erdtree (Capital Outskirts)","Minor Erdtree Church","Sealed Tunnel",
    "Elden Throne","Leyndell Catacombs","Leyndell Colosseum","Leyndell, Ashen Capital","Leyndell, Royal Capital","Castle Sol","Church of Repose","First Church of Marika",
    "Flame Peak","Forbidden Lands","Forge of the Giants","Grand Lift of Rold","Giant-Conquering Hero's Grave","Giants' Mountaintop Catacombs","Guardians' Garrison","Heretical Rise",
    "Lord Contender's Evergaol","Minor Erdtree (Mountaintops East)","Shack of the Lofty","Spiritcaller's Cave","Stargazers' Ruins","Zamor Ruins",
    "Hidden Path to the Haligtree","Albinauric Rise","Apostate Derelict","Cave of the Forlorn","Consecrated Snowfield Catacombs","Minor Erdtree (Consecrated Snowfield)",
    "Ordina, Liturgical Town","Yelough Anix Ruins","Yelough Anix Tunnel","Elphael, Brace of the Haligtree"];

const getNewId = async () => {
    let cid = await characterQuery.getMaxCid();
    cid = cid.cid+1;
    if(cid)
        return cid;
    return 1;
}

function generateCharacter() {
    return {
        name:faker.helpers.arrayElement(names),
        location:faker.helpers.arrayElement(location),
        hp:faker.number.int({min:1000,max:100000}),
        strongVs:faker.helpers.arrayElements(affinities,{min:1,max:4}).toString(),
        weakTo:faker.helpers.arrayElements(affinities,{min:1,max:4}).toString(),
        immuneTo:faker.helpers.arrayElements(affinities,{min:1,max:4}).toString()
    };
}

const generateCharacters = async (array) => {
    if (array.length < 20) {
        for (let i = array.length + 1; i <= 20; i++) {
            const cid = { cid: await getNewId() };
            let newCharacter = generateCharacter();
            newCharacter = { ...cid, ...newCharacter };
            array.push(newCharacter);
        }
    }
    return array;
};
const loadCharactersFromFile = async (array, uid) => {
    try {
        //const fileContent = fs.readFileSync("./characters.json", { encoding: 'utf8' });

        //const parsedCharacters = JSON.parse(fileContent);
        tempuid=uid;
        const parsedCharacters = await characterQuery.readAll(uid);
        //console.log(uid);
        //console.log(parsedCharacters);
        //console.log("length:",parsedCharacters.length);
        //console.log("pc:", parsedCharacters);
        if (parsedCharacters.length < 1) {
            //let generated= await generateCharacters(array);
            //console.log("g:", generated);
            await characterQuery.saveAll(characters, uid);
            let readCharacters = await characterQuery.readAll(uid);
            await setCharacters(readCharacters);
            return readCharacters;
        }
        return Promise.resolve(parsedCharacters);
    } catch (error) {
        if (error.code === 'ENOENT') {
            console.error(`File ./characters.json not found.`);
        } else {
            console.error('Error reading file:', error.message);
        }
        return Promise.reject({
            message: 'Error reading characters data',
            status: 500
        });
    }
};

async function setCharacters(charactersData) {
    if (charactersData instanceof Promise) {
        charactersData.then(data => {
            characters = data;
            characterQuery.saveAll(data);
        }).catch(error => {
            console.error('Error setting characters:', error.message);
        });
    } else {
        characters = charactersData;
        await characterQuery.saveAll(charactersData);
    }
}

async function setCharactersWithoutQuery(charactersData) {
    if (charactersData instanceof Promise) {
        charactersData.then(data => {
            characters = data;
        }).catch(error => {
            console.error('Error setting characters:', error.message);
        });
    } else {
        characters = charactersData;
    }
}


async function getCharacters(uid) {
    if(characters.length<1)
        await loadCharactersFromFile(characters, uid);
    return new Promise((resolve, reject) => {
        if (characters instanceof Promise) {
            characters.then(data => {
                if (Array.isArray(data) && data.length > 0) {
                    let tempCharacters=data.slice();
                    resolve(tempCharacters.sort((a, b) => a.name.localeCompare(b.name)));
                } else {
                    reject({
                        message: 'no characters available',
                        status: 202
                    });
                }
            }).catch(error => {
                reject({
                    message: 'Error reading characters data',
                    status: 500
                });
            });
        } else if (Array.isArray(characters) && characters.length > 0) {
            let tempCharacters = characters.slice();
            resolve(tempCharacters.sort((a, b) => a.name.localeCompare(b.name)));
        } else {
            reject({
                message: 'no characters available',
                status: 202
            });
        }
    });
}

function getUnsortedCharacters(){
    return new Promise((resolve, reject) => {
        if (characters instanceof Promise) {
            characters.then(data => {
                if (Array.isArray(data) && data.length > 0) {
                    //console.log("inside unsort");
                    resolve(characters);
                } else {
                    reject({
                        message: 'no characters available',
                        status: 202
                    });
                }
            }).catch(error => {
                reject({
                    message: 'Error reading characters data',
                    status: 500
                });
            });
        } else if (Array.isArray(characters) && characters.length > 0) {
            resolve(characters);
        } else {
            reject({
                message: 'no characters available',
                status: 202
            });
        }
    });
}

function getCharacter(cid) {
    return new Promise((resolve, reject) => {
        if (characters instanceof Promise) {
            characters
                .then(data => {
                    mustBeInArray(data, cid)
                        .then(character => resolve(character))
                        .catch(err => reject(err));
                })
                .catch(error => reject({ message: 'Error reading characters data', status: 500 }));
        } else if (Array.isArray(characters)) {
            mustBeInArray(characters, cid)
                .then(character => resolve(character))
                .catch(err => reject(err));
        } else {
            reject({ message: 'No characters available', status: 202 });
        }
    });
}


async function addCharacter(newCharacter,uid) {
    let cid = await characterQuery.getMaxCid();
    cid = cid.cid+1;
    //console.log(cid);
    return new Promise((resolve, reject) => {
        if (characters instanceof Promise) {
            characters
                .then(data => {
                    //console.log(newCharacter);
                    if (!newCharacter.name || !newCharacter.location || !newCharacter.hp || parseInt(newCharacter.hp) <= 0 || isNaN(parseInt(newCharacter.hp)) || !newCharacter.strongVs || !newCharacter.weakTo || !newCharacter.immuneTo) {
                        reject({ status: 400, message: `Missing required fields` });
                        return;
                    }
                    //console.log("in add",data);
                    newCharacter.cid=cid;
                    if(uid === 0)
                        uid = tempuid;
                    //console.log("in add",newCharacter);

                    //console.log("in add",newCharacter);
                    //newCharacter = { ...cid, ...newCharacter };
                    //data.push(newCharacter);
                    //console.log("aaa");
                    //console.log(newCharacter);
                    //console.log(characters);
                    characters.then(data=>data.push(newCharacter));
                    //console.log(characters);
                    //console.log("bsadas");
                    characterQuery.create(newCharacter, uid);
                    setCharacters(characterQuery.readAll(uid));
                    resolve(newCharacter);
                })
                .catch(error => reject({ message: 'Error reading characters data', status: 500 }));
        } else if (Array.isArray(characters)) {
            if (!newCharacter.name || !newCharacter.location || !newCharacter.hp || parseInt(newCharacter.hp) <= 0 || isNaN(parseInt(newCharacter.hp)) || !newCharacter.strongVs || !newCharacter.weakTo || !newCharacter.immuneTo) {
                reject({ status: 400, message: `Missing required fields` });
                return;
            }
            //console.log("in add",newCharacter);
            newCharacter.cid=cid;
            if(uid === 0)
                uid = tempuid;
            //console.log("in add",newCharacter);
            //newCharacter = { ...cid, ...newCharacter };
            characters.push(newCharacter);
            characterQuery.create(newCharacter,uid);
            setCharacters(characterQuery.readAll(uid));
            resolve(newCharacter);
        } else {
            console.log(err);
            reject({ message: 'No characters available', status: 202 });
        }
    });
}


async function updateCharacter(cid, newCharacter, uid) {
    return new Promise((resolve, reject) => {
        if (characters instanceof Promise) {
            characters
                .then(data => {
                    mustBeInArray(data, parseInt(cid))
                        .then(character => {
                            const index = data.findIndex(char => char.cid === parseInt(cid));
                            if (index === -1) {
                                reject({ status: 404, message: `CID ${cid} does not exist in array.` });
                                return;
                            }
                            //console.log("in update",data);
                            newCharacter.cid = cid;
                            data[index] = newCharacter;
                            console.log("in update",newCharacter);
                            characterQuery.update(cid, data[index]);
                            setCharacters(characterQuery.readAll(uid));
                            resolve(data[index]);
                        })
                        .catch(err => reject(err));
                })
                .catch(error => reject({ message: 'Error reading characters data', status: 500 }));
        } else if (Array.isArray(characters)) {
            const index = characters.findIndex(char => char.cid === parseInt(cid));
            if (index === -1) {
                reject({ status: 404, message: `CID ${cid} does not exist in array.` });
                return;
            }
            //console.log("in update",characters);
            newCharacter.cid = cid;
            characters[index] = newCharacter;
            console.log("in update",newCharacter);
            characterQuery.update(cid, characters[index]);
            setCharacters(characterQuery.readAll(uid));
            resolve(characters[index]);
        } else {
            reject({ message: 'No characters available', status: 202 });
        }
    });
}
async function deleteCharacter(cid, uid) {
    return new Promise(async (resolve, reject) => {
        try {
            //const items = await getItems();
            if (characters instanceof Promise) {
                characters.then(async data => {
                    try{
                        await mustBeInArray(data, cid);}
                    catch (err){reject({ status: 404, message: `CID ${cid} does not exist in array.` })};
                    if (Array.isArray(data) && data.length > 0) {
                        //await mustBeInArray(data, cid);
                        const index = data.findIndex(char => char.cid === cid);
                        if (index !== -1) {
                            await characterQuery.deleteCharacter(cid);
                            //data.splice(index, 1);
                            await deleteItemsForCID(cid);
                            await setCharacters(characterQuery.readAll(uid));
                            //let charactersToKeep = data.filter(char => char.cid !== cid);
                            //await setCharactersWithoutQuery(charactersToKeep);
                            //console.log(characters);
                            characters=data;
                            resolve();
                        } else {
                            reject({ status: 404, message: `CID ${cid} does not exist in array.` });
                        }
                    }
                });
            } else {
                try {
                    try{
                        await mustBeInArray(characters, cid);}
                    catch (err){reject({ status: 404, message: `CID ${cid} does not exist in array.` })};                    const index = characters.findIndex(char => char.cid === cid);
                    if (index !== -1) {
                        characters.splice(index, 1); // Remove the character from the array
                        await deleteItemsForCID(cid);
                        await characterQuery.deleteCharacter(cid);
                        await setCharacters(characterQuery.readAll(uid));
                        resolve(); } else {
                        reject({ status: 404, message: `CID ${cid} does not exist in array.` });
                    }
                } catch (err) {
                    reject({ status: 404, message: `CID ${cid} does not exist in array.` });
                }
            }
        } catch (err) {
            reject(err);
        }
    });
}

async function getPaginatedCharacters(page, pageSize, uid) {
    tempuid=uid;
    if(characters.length<1)
        await loadCharactersFromFile(characters, uid);
    //console.log(characters);
    return new Promise((resolve, reject) => {
        if (page <= 0 || pageSize <= 0) {
            reject({ status: 400, message: 'Invalid page number or page size' });
            return;
        }

        if (characters instanceof Promise) {
            characters.then(data => {
                if (Array.isArray(data) && data.length > 0) {
                    const startIndex = (page - 1) * pageSize;
                    const endIndex = page * pageSize;

                    const paginatedCharacters = data
                        .slice()
                        .sort((a, b) => a.name.localeCompare(b.name))
                        .slice(startIndex, endIndex);

                    //console.log(paginatedCharacters);

                    resolve(paginatedCharacters);
                } else {
                    reject({
                        message: 'No characters available',
                        status: 202
                    });
                }
            }).catch(error => {
                reject({
                    message: 'Error reading characters data',
                    status: 500
                });
            });
        } else if (Array.isArray(characters) && characters.length > 0) {
            const startIndex = (page - 1) * pageSize;
            const endIndex = page * pageSize;
            const paginatedCharacters = characters
                .slice()
                .sort((a, b) => a.name.localeCompare(b.name))
                .slice(startIndex, endIndex);
            resolve(paginatedCharacters);
        } else {
            reject({
                message: 'No characters available',
                status: 202
            });
        }
    });
}



module.exports = {
    setCharacters,
    addCharacter,
    getCharacters,
    getUnsortedCharacters,
    getPaginatedCharacters,
    getCharacter,
    updateCharacter,
    deleteCharacter,
    generateCharacters,
    generateCharacter,
    getNewId
};
