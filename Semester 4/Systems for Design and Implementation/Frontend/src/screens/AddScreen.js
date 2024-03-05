// AddScreen.js
import React, { useContext, useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { CharacterContext } from '../CharacterContext';

const AddScreen = ({ navigation }) => {
  const [cid, setCid] = useState('');
  const [name, setName] = useState('');
  const [location, setLocation] = useState('');
  const [hp, setHP] = useState('');
  const [strongVS, setStrongVS] = useState('');
  const [weakTo, setWeakTo] = useState('');
  const [immuneTo, setImmuneTo] = useState('');
  const [message, setMessage] = useState('');


  const handleAdd = () => {
    const newCharacter = {
      cid: parseInt(cid),
      name,
      location,
      hp: parseInt(hp),
      strongVS,
      weakTo,
      immuneTo
    };

    addCharacter(newCharacter);
    navigation.navigate('Home');

  };

  return (
    <View>
      <TextInput
        placeholder="CID"
        value={cid}
        onChangeText={setCid}
        keyboardType="numeric"
      />
      <TextInput
        placeholder="Name"
        value={name}
        onChangeText={setName}
      />
      <TextInput
        placeholder="Location"
        value={location}
        onChangeText={setLocation}
      />
      <TextInput
        placeholder="HP"
        value={hp}
        onChangeText={setHP}
        keyboardType="numeric"
      />
      <TextInput
        placeholder="StrongVS"
        value={strongVS}
        onChangeText={setStrongVS}
      />
      <TextInput
        placeholder="WeakTo"
        value={weakTo}
        onChangeText={setWeakTo}
      />
      <TextInput
        placeholder="ImmuneTo"
        value={immuneTo}
        onChangeText={setImmuneTo}
      />
      <Button title="Add" onPress={handleAdd} />
    </View>
  );
};

export default AddScreen;
