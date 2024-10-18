// CharacterContext.js
import React, { createContext, useState, useEffect, useContext } from "react";
import { Alert } from "react-native";
import {PouchDBContext} from "./database";

export const CharacterContext = createContext();

export const CharacterProvider = ({ children }) => {
  const [characters, setCharacters] = useState([]);
  const { localDB, storeDelCharEntry,storeCharEntry,updateDocumentByField } = useContext(PouchDBContext);
  //const [characters, setCharacters] = useState( [...initialCharacters].sort((a, b) => {return a.name.localeCompare(b.name);}));

  /*useEffect(() => {
      fetchCharacters();
  }, []);*/

  const fetchCharacters = () => {
    const token = sessionStorage.getItem('token');
    fetch('https://backendmpp-vsrjvd47ea-od.a.run.app/characters/all',{
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
    }).then(response => {
        return response.json();
      })
      .then(data => {
        setCharacters(data);
      }).catch(error => {
      if (error.message === 'Failed to fetch') {
        window.alert('The server is not running. Please start the server and try again.');
      } else {
        window.alert(error.message);
      }
    });
  };

  const fetchCharacter = (id) => {
    return new Promise((resolve, reject) => {
      fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/characters/${id}`,{credentials: 'include',})
        .then(response => {
          return response.json();
        })
        .then(data => {
          resolve(data);
        }).catch(error => {
        const tempCharacter = characters.find(character => character.cid === id);
        resolve(tempCharacter);
      });
    });
  };

  const addChar = async (newChar) => {
    console.log("nc",newChar);
    if (!newChar.cid) return;
    try {
      //setDbChars(newChars);
      await storeCharEntry(newChar,localDB);
      //localDB.info().then(info=>{console.log("db",info)});
    } catch (error) {
      console.error(error);
    }
  };

  const addDelChar = async (newDelChar) => {
    if (!newDelChar.cid) return;
    try {
      //setDbDelChars(newDelChars);
      await storeDelCharEntry(newDelChar,localDB);
      //const db = await getDBConnection();
      //await deleteChars(db, newDelChars);
      //console.log("here",getCharacters(db));
    } catch (error) {
      console.error(error);
    }
  };

  const updateChar = async (newChar) => {
    if (!newChar.cid) return;
    try {
      //setDbDelChars(newDelChars);
      const fieldName='cid';
      await updateDocumentByField(localDB,fieldName,newChar.cid,newChar);
      //const db = await getDBConnection();
      //await deleteChars(db, newDelChars);
      //console.log("here",getCharacters(db));
    } catch (error) {
      console.error(error);
    }
  };

  const addCharacter = (newCharacter) => {
    const token = sessionStorage.getItem('token');
    fetch('https://backendmpp-vsrjvd47ea-od.a.run.app/characters/', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
      body: JSON.stringify(newCharacter),
    }).then(response => {
      if (!response.ok) {
        newCharacter.cid=characters.length+1;
        setCharacters([...characters, newCharacter]);
        addChar(newCharacter);
        //setCharacters(characters.filter(character => character.cid !== newCharacter.cid));
        throw new Error('Failed to add character!');
      }
      return response.json();
    }).catch(error => {
      newCharacter.cid=characters.length+1;
      setCharacters([...characters, newCharacter]);
      addChar(newCharacter);
      window.alert(error.message);
    });
    //setCharacters([...characters, newCharacter].sort((a, b) => a.name.localeCompare(b.name)));
  };


  const updateCharacter = (id, updatedCharacter) => {
    const token = sessionStorage.getItem('token');
    //setCharacters(updatedCharacters.sort((a, b) => a.name.localeCompare(b.name)));
    fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/characters/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
      body: JSON.stringify(updatedCharacter),
    }).then(response => {
      if (!response.ok) {
        const updatedCharacters = characters.map(character => character.cid === id ? updatedCharacter : character);
        setCharacters(updatedCharacters);
        const tempCharacter = characters.find(character => character.cid === id);
        //setNewChar(tempCharacter);
        updateChar(tempCharacter);
        throw new Error('Failed to update character!');
      }
      return response.json();
    }).catch(error => {
      const updatedCharacters = characters.map(character => character.cid === id ? updatedCharacter : character);
      setCharacters(updatedCharacters);
      const tempCharacter = characters.find(character => character.cid === id);
      //setNewChar(tempCharacter);
      updateChar(tempCharacter);
      //window.alert(error.message);
    });
    //setCharacters(updatedCharacters);
  };

  const deleteCharacter = (id) => {
    const token = sessionStorage.getItem('token');
    fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/characters/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      credentials: 'include',
    }).then(response => {
      if (!response.ok) {
        const tempCharacter = characters.find(character => character.cid === id);
        setCharacters(characters.filter(character => character.cid !== id));
        //setNewDelChar(tempCharacter);
        addDelChar(tempCharacter);
        //setCharacters([...characters,tempCharacter]);
        throw new Error('Failed to delete character!');
      }
      return response.json();
    }).catch(error => {
      const tempCharacter = characters.find(character => character.cid === id);
      setCharacters(characters.filter(character => character.cid !== id));
      //setNewDelChar(tempCharacter);
      addDelChar(tempCharacter);
      //window.alert(error.message);
    });
  };


  return (
    <CharacterContext.Provider value={{ characters, setCharacters,addCharacter, updateCharacter, deleteCharacter,fetchCharacter,fetchCharacters }}>
      {children}
    </CharacterContext.Provider>
  );
};
