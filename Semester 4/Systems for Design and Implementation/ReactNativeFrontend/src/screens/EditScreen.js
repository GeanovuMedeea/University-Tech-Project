import React, { useState, useEffect, useContext } from "react";
import { View, TextInput, StyleSheet, Alert } from 'react-native';
import { Button as PaperButton } from 'react-native-paper';
import { CharacterContext } from "../CharacterContext";

const EditScreen = ({ navigation, route }) => {
  const {fetchCharacter,updateCharacter} = useContext(CharacterContext);
  const { id } = route.params; // Get id from URL params
  const [message, setMessage] = useState('');
  const [character, setCharacter] = useState({
    name: '',
    location: '',
    hp: '',
    strongVs: '',
    weakTo: '',
    immuneTo: ''
  });

  useEffect(() => {
    getCharacter();
  }, []);

  const getCharacter = () => {
    fetchCharacter(id)
      .then(character => {
        setCharacter(character);
      })
  };

  const handleUpdate = () => {
    updateCharacter(id,character);
    setMessage("Character updated successfully!");
  };

  const handleChange = (key, value) => {
    setCharacter(prevCharacter => ({
      ...prevCharacter,
      [key]: value
    }));
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Name"
        value={character.name}
        onChangeText={value => handleChange('name', value)}
      />
      <TextInput
        style={styles.input}
        placeholder="Location"
        value={character.location}
        onChangeText={value => handleChange('location', value)}
      />
      <TextInput
        style={styles.input}
        placeholder="HP"
        value={character.hp.toString()}
        onChangeText={value => handleChange('hp', value)}
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        placeholder="StrongVs"
        value={character.strongVs}
        onChangeText={value => handleChange('strongVs', value)}
      />
      <TextInput
        style={styles.input}
        placeholder="WeakTo"
        value={character.weakTo}
        onChangeText={value => handleChange('weakTo', value)}
      />
      <TextInput
        style={styles.input}
        placeholder="ImmuneTo"
        value={character.immuneTo}
        onChangeText={value => handleChange('immuneTo', value)}
      />
      <PaperButton mode="contained" onPress={handleUpdate} style={styles.button}>
        Update
      </PaperButton>
      <TextInput style={styles.messageInput} placeholder={message} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#a59b90',
    paddingHorizontal: 20,
  },
  input: {
    backgroundColor: 'white',
    width: '100%',
    padding: 10,
    marginBottom: 10,
    borderRadius: 5,
  },
  button: {
    marginVertical: 10,
    paddingVertical: 10,
    backgroundColor: '#363135',
  },
  messageInput: {
    backgroundColor: '#a59b90',
    width: '100%',
    color: '#363135',
    marginBottom: 10,
    borderRadius: 5,
    height: 100,
  },
});

export default EditScreen;
