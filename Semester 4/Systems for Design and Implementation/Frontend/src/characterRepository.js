// characterRepository.js
import { useContext } from 'react';
import { CharacterContext } from './CharacterContext';
let entityRepository = [
  {
    cid: 1,
    name: 'Radagon Of The Golden Order',
    location: 'Stone Platform',
    hp: 13339,
    strongVS: 'Frostbite, Scarlet Rot, Poison, Holy',
    weakTo: 'Fire',
    immuneTo: 'Sleep, Hemorrhage'
  },
  {
    cid: 2,
    name: 'Starscourge Radahn',
    location: 'Redmane Castle',
    hp: 9572,
    strongVS: 'Holy, Sleep',
    weakTo: 'Pierce, Scarlet Rot',
    immuneTo: null
  },
  {
    cid: 3,
    name: 'Malenia, Blade of Miquella',
    location: 'Elphael, Brace of the Haligtree',
    hp: 33251,
    strongVS: 'Poison, Scarlet Rot, Holy',
    weakTo: 'Fire, Frostbite, Hemorrhage',
    immuneTo: null
  }
];

export const addCharacter = (newCharacter) => {
  const { characters, setCharacters } = useContext(CharacterContext);
  setCharacters([...characters, newCharacter]);
};

export const updateCharacter = (id, updatedCharacter) => {
  const { characters, setCharacters } = useContext(CharacterContext);
  setCharacters(characters.map(character => character.cid === id ? updatedCharacter : character));
};

export const deleteCharacter = (id) => {
  const { characters, setCharacters } = useContext(CharacterContext);
  setCharacters(characters.filter(character => character.cid !== id));
};

export const getCharacters = () => {
  const { characters } = useContext(CharacterContext);
  return characters;
};

