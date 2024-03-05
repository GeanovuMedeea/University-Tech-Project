// CharacterContext.js
import React, { createContext, useState } from 'react';

export const CharacterContext = createContext();

const initialCharacters = [
  { cid: 1, name: 'Radagon Of The Golden Order', location: 'Stone Platform', hp: 13339, strongVS: 'Frostbite, Scarlet Rot, Poison, Holy', weakTo: 'Fire', immuneTo: 'Sleep, Hemorrhage' },
  { cid: 2, name: 'Starscourge Radahn', location: 'Redmane Castle', hp: 9572, strongVS: 'Holy, Sleep', weakTo: 'Pierce, Scarlet Rot', immuneTo: null },
  { cid: 3, name: 'Malenia, Blade of Miquella', location: 'Elphael, Brace of the Haligtree', hp: 33251, strongVS: 'Poison, Scarlet Rot, Holy', weakTo: 'Fire, Frostbite, Hemorrhage', immuneTo: null }
];

export const CharacterProvider = ({ children }) => {
  const [characters, setCharacters] = useState(initialCharacters);

  const addCharacter = (newCharacter) => {
    setCharacters([...characters, newCharacter]);
  };

  const updateCharacter = (id, updatedCharacter) => {
    setCharacters(characters.map(character => character.cid === id ? updatedCharacter : character));
  };

  const deleteCharacter = (id) => {
    setCharacters(characters.filter(character => character.cid !== id));
  };

  return (
    <CharacterContext.Provider value={{ characters, addCharacter, updateCharacter, deleteCharacter }}>
      {children}
    </CharacterContext.Provider>
  );
};
