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
        poolConnection.close();
        //console.log('Database connection closed');
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

async function saveAll(entries, uid) {
    try {
        //await connect();
        //const request = poolConnection.request();
        if (entries instanceof Promise) {
            entries = await entries;
            //console.log(entries);
        }

        //console.log(entries);
        //await request.query(
        //    `DELETE FROM Items`
        //);
        for (const entry of entries) {
            const existingItem = await read(entry.lid);
            if (!existingItem)
                await create(entry, uid);
        }

    } catch (error) {
        console.error('Error saving entries:', error);
    } finally {
        //console.log('All entries saved successfully');
    }
}

async function createWithNoUid(data) {
    await connect();
    const request = poolConnection.request();

    request.input('lid', sql.Int, data.lid);
    request.input('cid', sql.Int, data.cid);
    request.input('itemName', sql.NVarChar(100), data.itemName);
    request.input('itemDrop', sql.Int, data.itemDrop);
    request.input('runeCount', sql.Int, data.runeCount);

    const result = await request.query(
        `INSERT INTO Items (lid,cid,itemName,itemDrop,runeCount) VALUES (@lid,@cid,@itemName,@itemDrop,@runeCount)`
    );

    return result.rowsAffected[0];
}

async function create(data,uid) {
    await connect();
    const request = poolConnection.request();

    request.input('lid', sql.Int, data.lid);
    request.input('cid', sql.Int, data.cid);
    request.input('itemName', sql.NVarChar(100), data.itemName);
    request.input('itemDrop', sql.Int, data.itemDrop);
    request.input('runeCount', sql.Int, data.runeCount);
    request.input('uid', sql.Int, uid);

    const result = await request.query(
        `INSERT INTO Items (lid,cid,itemName,itemDrop,runeCount) VALUES (@lid,@cid,@itemName,@itemDrop,@runeCount)`
    );
    const temp = await request.query(
        `INSERT INTO UserItems (uid,lid) VALUES (@uid, @lid)`
    );
    return result.rowsAffected[0];
}

async function readAll(uid) {
    await connect();
    const request = poolConnection.request();
    const uidAsNumber = parseInt(uid);
    //console.log("uid",uidAsNumber);
    const result = await request.input('uid', sql.Int, uidAsNumber).
    query(`SELECT it.*
        FROM Items AS it
        INNER JOIN UserItems AS ui ON it.lid = ui.lid
        WHERE ui.uid = @uid`);
    //console.log("query items:", result.recordsets[0]);
    //console.log("hello", uidAsNumber);
    //console.log("items",result.recordsets[0]);
    return result.recordsets[0];
}

async function read(lid) {
    await connect();

    const request = poolConnection.request();
    const result = await request
        .input('lid', sql.Int, +lid)
        .query(`SELECT * FROM Items WHERE lid = @lid`);

    return result.recordset[0];
}

async function update(lid, data) {
    await connect();

    const request = poolConnection.request();

    request.input('lid', sql.Int, +lid);
    request.input('cid', sql.Int, data.cid);
    request.input('itemName', sql.NVarChar(100), data.itemName);
    request.input('itemDrop', sql.Int, data.itemDrop);
    request.input('runeCount', sql.Int, data.runeCount);

    const result = await request.query(
        `UPDATE Items SET lid=@lid, cid=@cid, itemName=@itemName,itemDrop=@itemDrop, runeCount=@runeCount WHERE lid = @lid`
    );

    return result.rowsAffected[0];
}

async function deleteItem(lid) {
    await connect();
    const lidAsNumber = Number(lid);

    const request = poolConnection.request();
    const result = await request
        .input('lid', sql.Int, lidAsNumber)
        .query(`DELETE FROM Items WHERE lid = @lid`);
    //console.log("Inside deleteItem");
    return result.rowsAffected[0];
}

async function getMaxLid() {
    await connect(); // Assuming this connects to your database
    const request = poolConnection.request();
    const result = await request.query(`SELECT MAX(lid) AS lid FROM Items`);

    return result.recordset[0];
}

module.exports = {
    read,
    readAll,
    saveAll,
    create,
    update,
    deleteItem,
    getMaxLid
};
