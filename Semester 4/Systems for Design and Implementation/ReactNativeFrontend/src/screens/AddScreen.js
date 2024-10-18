// AddScreen.js
import React, { useContext, useState} from 'react';
import { View, TextInput, Button, Alert, StyleSheet } from "react-native";
import { CharacterContext } from '../CharacterContext';
import { Button as PaperButton } from 'react-native-paper';

const AddScreen = ({ navigation }) => {
  const [name, setName] = useState('');
  const [location, setLocation] = useState('');
  const [hp, setHP] = useState('');
  const [strongVs, setStrongVs] = useState('');
  const [weakTo, setWeakTo] = useState('');
  const [immuneTo, setImmuneTo] = useState('');
  const {addCharacter} = useContext(CharacterContext);

  const handleAdd = () => {
    const newCharacter = {
      cid: parseInt(1000),
      name,
      location,
      hp: parseInt(hp),
      strongVs: strongVs,
      weakTo,
      immuneTo
    };

    addCharacter(newCharacter);
    navigation.navigate('Home');

  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Name"
        value={name}
        onChangeText={setName}
      />
      <TextInput
        style={styles.input}
        placeholder="Location"
        value={location}
        onChangeText={setLocation}
      />
      <TextInput
        style={styles.input}
        placeholder="HP"
        value={hp}
        onChangeText={setHP}
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        placeholder="StrongVs"
        value={strongVs}
        onChangeText={setStrongVs}
      />
      <TextInput
        style={styles.input}
        placeholder="WeakTo"
        value={weakTo}
        onChangeText={setWeakTo}
      />
      <TextInput
        style={styles.input}
        placeholder="ImmuneTo"
        value={immuneTo}
        onChangeText={setImmuneTo}
      />
      <PaperButton mode="contained" onPress={handleAdd} style={styles.button}>
        Add
      </PaperButton>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: 'flex-start',
    flex: 1,
    alignItems: 'center',
    padding: 30,
    backgroundColor: '#a59b90',
    paddingHorizontal: 20,
  },
  button: {
    marginVertical: 10,
    paddingVertical: 10,
    backgroundColor: '#363135',
  },
  input: {
    backgroundColor: 'white',
    width: '100%',
    padding: 10,
    marginBottom: 10,
    borderRadius: 5,
  },
});

export default AddScreen;
