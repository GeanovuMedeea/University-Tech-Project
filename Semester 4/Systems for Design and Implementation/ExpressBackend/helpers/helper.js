/*const {faker, da} = require("@faker-js/faker");
const characterQuery = require('./charactersQueries');
const {getMaxLid} = require("./itemsQueries");
const {getMaxCidByUid} = require("./charactersQueries");

let fakeItems=[];

const getNewId = async () => {
    let cid = await characterQuery.getMaxCid();
    cid = cid.cid+1;
    if(cid)
        return cid;
    return 1;
}

const getNewItemId = async () => {
    let lid = await getMaxLid() ? await getMaxLid(): 0;
    lid = lid.lid+1;
    if(lid)
        return lid;
    return 1;
}

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

function generateItem(cid) {
    return {
        cid:cid,
        itemName:faker.helpers.arrayElement(itemName),
        itemDrop:faker.number.int({min:1000,max:100000}),
        runeCount:faker.number.int({min:1000,max:100000}),
    };
}
const generateItems = async (uid)=>{
    const lid = {lid: await getNewItemId()};
    let tempcid = await getMaxCidByUid(uid);
    let cid = tempcid.cid;
    let newItem = generateItem(cid);
    newItem = {...lid, ...newItem};
    fakeItems.push(newItem);
    return fakeItems;
};

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

module.exports = {
    getNewId,
    getNewItemId,
    mustBeInArray,
    generateCharacter,
    generateCharacters,
    generateItem,
    generateItems,
    itemMustBeInArray,
}*/
