// EditScreen.js
import React, { useContext, useState, useEffect } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { CharacterContext } from '../CharacterContext';

const EditScreen = ({ navigation, route }) => {
  const [message, setMessage] = useState('');
  const { id } = route.params;
  const { characters, updateCharacter, deleteCharacter } = useContext(CharacterContext);
  const [character, setCharacter] = useState({
    cid: '',
    name: '',
    location: '',
    hp: '',
    strongVS: '',
    weakTo: '',
    immuneTo: ''
  });

  useEffect(() => {
    const selectedCharacter = characters.find(item => item.cid === id);
    if (selectedCharacter) {
      setCharacter(selectedCharacter);
    }
  }, [characters, id]);

  const handleUpdate = () => {
    updateCharacter(id, character);
    setMessage('Character updated successfully');
    //navigation.navigate('Home');

  };

  const handleDelete = () => {
    deleteCharacter(id);

    setMessage('Character deleted successfully');
    //navigation.navigate('Home');

  };

  const handleChange = (key, value) => {
    setCharacter(prevCharacter => ({
      ...prevCharacter,
      [key]: value
    }));
  };

  return (
    <View>
      <TextInput
        placeholder="CID"
        value={character.cid.toString()}
        onChangeText={value => handleChange('cid', value)}
        keyboardType="numeric"
      />
      <TextInput
        placeholder="Name"
        value={character.name}
        onChangeText={value => handleChange('name', value)}
      />
      <TextInput
        placeholder="Location"
        value={character.location}
        onChangeText={value => handleChange('location', value)}
      />
      <TextInput
        placeholder="HP"
        value={character.hp.toString()}
        onChangeText={value => handleChange('hp', value)}
        keyboardType="numeric"
      />
      <TextInput
        placeholder="StrongVS"
        value={character.strongVS}
        onChangeText={value => handleChange('strongVS', value)}
      />
      <TextInput
        placeholder="WeakTo"
        value={character.weakTo}
        onChangeText={value => handleChange('weakTo', value)}
      />
      <TextInput
        placeholder="ImmuneTo"
        value={character.immuneTo}
        onChangeText={value => handleChange('immuneTo', value)}
      />
      <Button title="Update" onPress={handleUpdate} />
      <Button title="Delete" onPress={handleDelete} />
      <TextInput
      placeholder={message}
      />

    </View>
  );
};

export default EditScreen;
