const sql = require('mssql');
const config = require('../config');
let poolConnection;
async function connect() {
    try {
        if (!poolConnection || !poolConnection.connected) {
            //console.log(`Database connecting...`);
            poolConnection = await sql.connect(config);
            //console.log('Database connection successful');
        } else {
            //console.log('Database already connected');
        }
    } catch (error) {
        console.error(`Error connecting to database: ${JSON.stringify(error)}`);
    }
}

async function disconnect() {
    try {
        if (poolConnection && poolConnection.connected) {
            //console.log('Database connection closed');
            await poolConnection.close();
        } else {
            console.log('Database already disconnected');
        }
    } catch (error) {
        console.error(`Error closing database connection: ${error}`);
    }
}

async function executeQuery(query) {
    await connect();
    const request = poolConnection.request();
    const result = await request.query(query);

    return result.rowsAffected[0];
}

async function createWithNoUid(data) {
    await connect();
    const request = poolConnection.request();

    request.input('cid', sql.Int, data.cid);
    request.input('name', sql.NVarChar(100), data.name);
    request.input('location', sql.NVarChar(100), data.location);
    request.input('hp', sql.Int, data.hp);
    request.input('strongVs', sql.NVarChar(100), data.strongVs);
    request.input('weakTo', sql.NVarChar(100), data.weakTo);
    request.input('immuneTo', sql.NVarChar(100), data.immuneTo);

    const result = await request.query(
        `INSERT INTO CharacterInformation (cid, name, location,hp,strongVs, weakTo,immuneTo) VALUES (@cid, @name, @location,@hp,@strongVs, @weakTo,@immuneTo)`
    );
    return result.rowsAffected[0];
}

async function create(data,uid) {
    await connect();
    const request = poolConnection.request();

    request.input('cid', sql.Int, data.cid);
    request.input('name', sql.NVarChar(100), data.name);
    request.input('location', sql.NVarChar(100), data.location);
    request.input('hp', sql.Int, data.hp);
    request.input('strongVs', sql.NVarChar(100), data.strongVs);
    request.input('weakTo', sql.NVarChar(100), data.weakTo);
    request.input('immuneTo', sql.NVarChar(100), data.immuneTo);
    request.input('uid', sql.Int, uid);

    const result = await request.query(
        `INSERT INTO CharacterInformation (cid, name, location,hp,strongVs, weakTo,immuneTo) VALUES (@cid, @name, @location,@hp,@strongVs, @weakTo,@immuneTo)`
    );
    const temp = await request.query(
        `INSERT INTO UserCharacters (uid,cid) VALUES (@uid, @cid)`
    );
    return result.rowsAffected[0];
}

async function readAll(uid) {
    await connect();
    const request = poolConnection.request();
    const uidAsNumber = parseInt(uid);
    //console.log("uid: ",uidAsNumber);
    const result = await request.input('uid', sql.Int, uidAsNumber).
    query(`SELECT ci.*
        FROM CharacterInformation AS ci
        INNER JOIN UserCharacters AS uc ON ci.cid = uc.cid
        WHERE uc.uid = @uid`);
    //console.log(result.recordsets[0]);
    return result.recordsets[0];
}

async function read(cid) {
    await connect();

    const request = poolConnection.request();
    const result = await request
        .input('cid', sql.Int, +cid)
        .query(`SELECT * FROM CharacterInformation WHERE cid = @cid`);

    return result.recordset[0];
}

async function saveAll(entries, uid) {
    try {
        //await connect();
        //const request = await poolConnection.request();
        if (entries instanceof Promise) {
            entries = await entries;
            //console.log(entries);
        }

        //console.log("in save all");
        //console.log(entries);

        //await request.query(
        //    `DELETE FROM CharacterInformation`
        //);
        //console.log("after save all");

        for (const entry of entries) {
            const existingCharacter = await read(entry.cid);
            if (!existingCharacter)
                await create(entry, uid);
        }

    } catch (error) {
        console.error('Error saving entries:', error);
    } finally {
        //console.log('All entries saved successfully');
    }
}


async function update(cid, data) {
    await connect();

    const request = poolConnection.request();

    request.input('cid', sql.Int, +cid);
    request.input('name', sql.NVarChar(100), data.name);
    request.input('location', sql.NVarChar(100), data.location);
    request.input('hp', sql.Int, data.hp);
    request.input('strongVs', sql.NVarChar(100), data.strongVs);
    request.input('weakTo', sql.NVarChar(100), data.weakTo);
    request.input('immuneTo', sql.NVarChar(100), data.immuneTo);

    const result = await request.query(
        `UPDATE CharacterInformation SET name=@name, location=@location, hp=@hp, strongVs=@strongVs, weakTo=@weakTo, immuneTo=@immuneTo WHERE cid = @cid`
    );
    return result.rowsAffected[0];
}

async function deleteCharacter(cid) {
    await connect();
    //console.log("delete!!!");
    //console.log(cid);
    if(cid) {
        const cidAsNumber = parseInt(cid);
        const request = poolConnection.request();
        const result = await request
            .input('cid', sql.Int, cidAsNumber)
            .query(`DELETE
                    FROM CharacterInformation
                    WHERE cid = @cid`);
        return result.rowsAffected[0];
    }
    //console.log("Inside deleteCharacter");
}

async function getMaxCid() {
    await connect(); // Assuming this connects to your database
    const request = poolConnection.request();
    const result = await request.query(`SELECT MAX(cid) AS cid FROM CharacterInformation`);
    return result.recordset[0];
}

async function getMaxCidByUid(uid) {
    await connect(); // Assuming this connects to your database
    const request = poolConnection.request();
    const uidAsNumber = parseInt(uid);
    //console.log("uid: ",uidAsNumber);
    const result = await request.input('uid', sql.Int, uidAsNumber)
        .query(`SELECT MAX(cid) AS cid FROM UserCharacters WHERE uid = @uid`);
    console.log("r cid:", result.recordset[0]);
    return result.recordset[0];
}

module.exports = {
    readAll,
    create,
    read,
    update,
    deleteCharacter,
    getMaxCidByUid,
    saveAll,
    getMaxCid
};
