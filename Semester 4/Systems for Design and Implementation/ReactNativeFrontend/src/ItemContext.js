// CharacterContext.js
import React, { createContext, useState, useEffect, useContext } from "react";
import { Alert } from "react-native";
import {PouchDBContext} from "./database";

export const ItemContext = createContext();

export const ItemProvider = ({ children }) => {
  const [items, setItems] = useState([]);
  const { localDB, storeDelItemEntry,storeItemEntry,updateDocumentByField } = useContext(PouchDBContext);
  const [dbItems, setDbItems] = useState([]);
  const [newItem, setNewItem] = useState('');
  const [dbDelItem, setDbDelItem] = useState([]);
  const [newDelItem, setNewDelItem] = useState('');
  //const [items, setItems] = useState( [...initialCharacters].sort((a, b) => {return a.name.localeCompare(b.name);}));

  /*useEffect(() => {
      fetchCharacters();
  }, []);*/

  const fetchItems = () => {
    const token = sessionStorage.getItem('token');
    fetch('https://backendmpp-vsrjvd47ea-od.a.run.app/items/all',{
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
    }).then(response => {
        return response.json();
      })
      .then(data => {
        setItems(data);
      }).catch(error => {
      if (error.message === 'Failed to fetch') {
        //window.alert('The server is not running. Please start the server and try again.');
      } else {
        window.alert(error.message);
      }
    });
  };

  const fetchItem = (id) => {
    const token = sessionStorage.getItem('token');
    return new Promise((resolve, reject) => {
      fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/items/${id}`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        credentials: 'include',
      }).then(response => {
          return response.json();
        })
        .then(data => {
          resolve(data);
        }).catch(error => {
        const tempItem = items.find(item => item.lid === id);
        resolve(tempItem);
      });
    });
  };

  const addIt = async (newItem) => {
    console.log("ni",newItem);
    if (!newItem.lid) return;
    try {
      //setDbChars(newChars);
      await storeItemEntry(newItem,localDB);
      //localDB.info().then(info=>{console.log("db",info)});
    } catch (error) {
      console.error(error);
    }
  };

  const addDelIt = async (newDelItem) => {
    if (!newDelItem.lid) return;
    try {
      //setDbDelChars(newDelChars);
      await storeDelItemEntry(newDelItem,localDB);
      //const db = await getDBConnection();
      //await deleteChars(db, newDelChars);
      //console.log("here",getCharacters(db));
    } catch (error) {
      console.error(error);
    }
  };

  const updateIt = async (newItem) => {
    if (!newItem.lid) return;
    try {
      //setDbDelChars(newDelChars);
      const fieldName='lid';
      await updateDocumentByField(localDB,fieldName,newItem.lid,newItem);
      //const db = await getDBConnection();
      //await deleteChars(db, newDelChars);
      //console.log("here",getCharacters(db));
    } catch (error) {
      console.error(error);
    }
  };

  const addItem = (newItem) => {
    const token = sessionStorage.getItem('token');
    fetch('https://backendmpp-vsrjvd47ea-od.a.run.app/items', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
      body: JSON.stringify(newItem),
    }).then(response => {
      if (!response.ok) {
        newItem.lid=items.length+1;
        setItems([...items, newItem]);
        addIt(newItem);
        //setItems(items.filter(item => item.lid !== newItem.lid));
        throw new Error('Failed to add item!');
      }
      return response.json();
    }).catch(error => {
      newItem.lid=items.length+1;
      setItems([...items, newItem]);
      addIt(newItem);
      //window.alert(error.message);
    });
    //setItems([...items, newCharacter].sort((a, b) => a.name.localeCompare(b.name)));
  };


  const updateItem = (id, updatedItem) => {
    const token = sessionStorage.getItem('token');
    //setItems(updatedCharacters.sort((a, b) => a.name.localeCompare(b.name)));
    fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/items/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
      body: JSON.stringify(updatedItem),
    }).then(response => {
      if (!response.ok) {
        const updatedItems = items.map(item => item.lid === id ? updatedItem : item);
        setItems(updatedItems);
        const tempItem = items.find(item => item.lid === id);
        updateIt(tempItem);
        throw new Error('Failed to update item!');
      }
      return response.json();
    }).catch(error => {
      const updatedItems = items.map(item => item.lid === id ? updatedItem : item);
      setItems(updatedItems);
      const tempItem = items.find(item => item.lid === id);
      updateIt(tempItem);
      //window.alert(error.message);
    });
    //setItems(updatedCharacters);
  };

  const deleteItem = (id) => {
    const token = sessionStorage.getItem('token');
    fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/items/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
    }).then(response => {
      if (!response.ok) {
        const tempItem = items.find(item => item.lid === id);
        setItems(items.filter(item => item.lid !== id));
        addDelIt(tempItem);
        //setItems([...items,tempCharacter]);
        throw new Error('Failed to delete item!');
      }
      return response.json();
    }).catch(error => {
      const tempItem = items.find(item => item.lid === id);
      setItems(items.filter(item => item.lid !== id));
      addDelIt(tempItem);
      //window.alert(error.message);
    });
  };


  return (
    <ItemContext.Provider value={{ items, setItems,addItem, updateItem, deleteItem,fetchItem,fetchItems }}>
      {children}
    </ItemContext.Provider>
  );
};
