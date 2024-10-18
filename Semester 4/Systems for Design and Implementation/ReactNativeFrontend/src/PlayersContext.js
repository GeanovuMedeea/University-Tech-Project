// CharacterContext.js
import React, { createContext, useState, useEffect, useContext } from "react";
import { Alert } from "react-native";

export const PlayersContext = createContext();

export const PlayersProvider = ({ children }) => {
  const [players, setPlayers] = useState([]);

  const fetchPlayers = (sortType) => {
    fetch(`http://localhost:3000/players/all/${sortType}`,{
      headers: {
        'Content-Type': 'application/json',
      }
    }).then(response => {
      return response.json();
    })
      .then(data => {
        console.log("data", data);
        setPlayers(data);
      }).catch(error => {
      if (error.message === 'Failed to fetch') {
        window.alert('The server is not running. Please start the server and try again.');
      } else {
        window.alert(error.message);
      }
    });
  };

  const fetchPlayer = (id) => {
    return new Promise((resolve, reject) => {
      fetch(`http://localhost:3000/players/${id}`)
        .then(response => {
          return response.json();
        })
        .then(data => {
          resolve(data);
        }).catch(error => {
        const tempCharacter = players.find(character => character.cid === id);
        resolve(tempCharacter);
      });
    });
  };


  return (
    <PlayersContext.Provider value={{ players: players, setPlayers: setPlayers,fetchPlayer,fetchPlayers }}>
      {children}
    </PlayersContext.Provider>
  );
};
