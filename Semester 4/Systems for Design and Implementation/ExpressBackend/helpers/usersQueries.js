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

async function create(data) {
    await connect();
    const request = poolConnection.request();

    request.input('uid', sql.Int, data.uid);
    request.input('username', sql.NVarChar(100), data.username);
    request.input('password', sql.NVarChar(100), data.password);

    const result = await request.query(
        `INSERT INTO Users (uid, username, password) VALUES (@uid, @username, @password)`
    );
    return result.rowsAffected[0];
}

async function getMaxUid() {
    await connect(); // Assuming this connects to your database
    const request = poolConnection.request();
    const result = await request.query(`SELECT MAX(uid) AS uid FROM Users`);

    return result.recordset[0];
}

async function read(uid) {
    await connect();

    const request = poolConnection.request();
    const result = await request
        .input('uid', sql.Int, +uid)
        .query(`SELECT * FROM Users WHERE uid = @uid`);

    return result.recordset[0];
}

async function findByUsername(username) {
    await connect();
    const request = poolConnection.request();
    request.input('username', sql.NVarChar(100), username);

    const result = await request
        .input('uid', sql.NVarChar(100), username)
        .query(`SELECT * FROM Users WHERE username = @username`);

    return result.recordset[0];
}

async function update(uid, data) {
    await connect();

    const request = poolConnection.request();

    request.input('uid', sql.Int, +uid);
    request.input('username', sql.NVarChar(100), data.username);
    request.input('password', sql.NVarChar(100), data.password);

    const result = await request.query(
        `UPDATE Users SET username=@username, password=@password WHERE uid = @uid`
    );
    return result.rowsAffected[0];
}

async function deleteUser(uid) {
    await connect();
    //console.log("delete!!!");
    //console.log(cid);
    if(uid) {
        const uidAsNumber = parseInt(uid);
        const request = poolConnection.request();
        const result = await request
            .input('uid', sql.Int, uidAsNumber)
            .query(`DELETE
                    FROM Users
                    WHERE uid = @uid`);
        return result.rowsAffected[0];
    }
    //console.log("Inside deleteCharacter");
}


module.exports = {
    create,
    read,
    update,
    deleteUser,
    findByUsername,
    getMaxUid
};
