//const localDB = new PouchDB('local_entries');

import React from 'react';
import PouchDB from 'pouchdb';

let localDB;

if (process.env.NODE_ENV !== 'test') {
    localDB = new PouchDB('local_entries');
}

export const PouchDBContext = React.createContext(
  /*{localDB,
  deleteAllFromDB,
  storeCharEntry,
  storeItemEntry,
  storeDelItemEntry,
  storeDelCharEntry,
  fetchLocalEntries,
  sendEntriesToServer,
  synchronizeWithServer,
  updateDocumentByField,}*/
);

export function PouchDBProvider({ children }) {
  return (
    <PouchDBContext.Provider value={{
      localDB,
      deleteAllFromDB,
      storeCharEntry,
      storeItemEntry,
      fetchLocalEntries,
      sendEntriesToServer,
      synchronizeWithServer,
      updateDocumentByField,
      storeDelCharEntry,
      storeDelItemEntry
    }}>
      {children}
    </PouchDBContext.Provider>
  );
}

function delay(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function isValidCharEntry(entry) {
  return typeof entry === 'object' &&
    entry.hasOwnProperty('cid') &&
    entry.hasOwnProperty('name') &&
    entry.hasOwnProperty('location') &&
    entry.hasOwnProperty('hp') &&
    entry.hasOwnProperty('strongVs') &&
    entry.hasOwnProperty('weakTo') &&
    entry.hasOwnProperty('immuneTo');
}

function isValidItemEntry(entry) {
  return typeof entry === 'object' &&
    entry.hasOwnProperty('lid') &&
    entry.hasOwnProperty('cid') &&
    entry.hasOwnProperty('itemName') &&
    entry.hasOwnProperty('itemDrop') &&
    entry.hasOwnProperty('runeCount');
}

export async function storeCharEntry(entry,localDB) {
  console.log("char to add",entry);
  //console.log("entry",entry);
  if (!isValidCharEntry(entry)) {
    console.error('Invalid entry:', entry);
    return;
  }
  //const allDocs = await fetchLocalEntries(localDB);
  //console.log("ad",allDocs);
  //const charEntries = allDocs.filter(entry => entry.type === 'char');
  //console.log("ce",charEntries);
  //const docToUpdate = charEntries.find(doc => doc.cid === entry.cid);
  //console.log("dc",docToUpdate);

  entry._id="char"+Math.random().toString();
  entry.type = 'char';
  //console.log("entry",entry);
  try {
    const response = await localDB.put(entry);
    console.log('New entry stored locally', response);
  } catch (error) {
    console.error('Error storing new entry locally', error);
  }
  //localDB.info().then(info=>{console.log("after",info)});
}

export async function storeItemEntry(entry,localDB) {
  if (!isValidItemEntry(entry)) {
    console.error('Invalid entry:', entry);
    return;
  }

  entry.type = 'item';
  entry._id="item"+Math.random().toString();
  localDB.put(entry).then(response => {
    console.log('Entry stored locally', response);
  }).catch(error => {
    console.error('Error storing entry locally', error);
  });
}

export async function storeDelCharEntry(entry,localDB) {
  if (!entry.cid) {
    console.error('Invalid entry:', entry);
    return;
  }
  const allDocs = await fetchLocalEntries(localDB);

  entry._id="delchar"+Math.random().toString();
  entry.type = 'charDel';
  localDB.put(entry).then(response => {
    console.log('Entry stored locally', response);
  }).catch(error => {
    console.error('Error storing entry locally', error);
  });

  const itemEntriesToDelete = allDocs.filter(doc => doc.type === 'item' && doc.cid === entry.cid);
  await itemEntriesToDelete.forEach(async (itemEntry) => {
    try {
      delete itemEntry._id;
      delete itemEntry._rev;
      await storeDelItemEntry(itemEntry,localDB);
      //await localDB.remove(itemEntry._id, itemEntry._rev);
      console.log('Item entry deleted:', itemEntry._id);
    } catch (error) {
      console.error('Error deleting item entry:', error);
    }
  });
}

export async function storeDelItemEntry(entry,localDB) {
  if (!entry.lid) {
    console.error('Invalid entry:', entry);
    return;
  }
  entry._id="delitem"+Math.random().toString();
  entry.type = 'itemDel';
  localDB.put(entry).then(response => {
    console.log('Entry stored locally', response);
  }).catch(error => {
    console.error('Error storing entry locally', error);
  });
}

export async function fetchLocalEntries(localDB) {
  try {
    const result = await localDB.allDocs({
      include_docs: true,
      attachments: true
    });
    return result.rows.map(row => row.doc);
  } catch (error) {
    console.error('Error fetching local entries:', error);
    return [];
  }
}

export async function sendEntriesToServer(entries,address) {
  try {
    console.log("3.send address",address);
    console.log("4.in send",entries);

    const entriesWithoutType = entries.map(entry => {
      const { type, _id, _rev, ...rest } = entry;
      return rest;
    });
    console.log("5.entries without type,_id,_rev", entriesWithoutType);
    const token = sessionStorage.getItem('token');
    const response = await fetch(address, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
      body: JSON.stringify(entriesWithoutType)
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    console.log('Entries sent to server successfully');
  } catch (error) {
    console.error('Error sending entries to server:', error);
  }
}

async function updateDocumentByField(localDB, fieldName, fieldValue, newData) {
  try {
    const allDocs = await fetchLocalEntries(localDB);

    const filteredDocs = allDocs.filter(doc => doc.type !== 'charDel' && doc.type!=='itemDel');
    const docToUpdate = filteredDocs.find(doc => doc[fieldName] === fieldValue);
    const updatedData = { ...newData };
    delete updatedData._id;
    delete updatedData._rev;

    if (!docToUpdate) {
      if(updatedData.type==='item' || updatedData.lid)
        await storeItemEntry(updatedData,localDB);
      else
        await storeCharEntry(updatedData,localDB);
      return;
    }
    Object.assign(docToUpdate, updatedData);
    console.log("updated object",docToUpdate);
    await localDB.put(docToUpdate);
    console.log('Document updated successfully');
  } catch (error) {
    console.error('Error updating document by field:', error);
  }
}

export async function synchronizeWithServer(address,type) {
  const tempEntries = await fetchLocalEntries(localDB);
  const entries = tempEntries.filter(doc => doc.type === type);
  console.log("1. address",address);
  console.log("2. entries to send",entries);
  if (entries.length > 0) {
    await sendEntriesToServer(entries,address);
    // Optionally, remove the entries from the local database after successful synchronization
    await Promise.all(entries.map(entry => localDB.remove(entry._id, entry._rev)));
  } else {
    console.log('No entries to synchronize');
  }
}

export async function deleteAllFromDB() {
  try {
    const entries = fetchLocalEntries();
    await Promise.all(entries.map(entry => localDB.remove(entry._id, entry._rev)));
    console.log('Database deleted successfully');
  } catch (error) {
    console.error('Error deleting database:', error);
  }
}
