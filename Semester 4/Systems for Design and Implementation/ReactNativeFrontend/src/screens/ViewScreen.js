import React, { useState, useEffect, useContext } from "react";
import { View, Text, ScrollView, StyleSheet, Alert } from 'react-native';
import {CharacterContext} from "../CharacterContext";

const ViewScreen = ({ navigation, route }) => {
  const { fetchCharacter } = useContext(CharacterContext);
  const { id } = route.params;
  const [character, setCharacter] = useState({
    cid: '',
    name: '',
    location: '',
    hp: '',
    strongVs: '',
    weakTo: '',
    immuneTo: ''
  });

  useEffect(() => {
    viewCharacter();
  }, []);

  const viewCharacter = () => {
    fetchCharacter(id)
      .then(character => {
        console.log("id",character);
        setCharacter(character);
      })
  };

  return (
    <View style={styles.container}>
      <View style={styles.box}>
        <ScrollView contentContainerStyle={styles.content}>
          <Text style={styles.label}>Name:</Text>
          <Text style={styles.text}>{character.name}</Text>

          <Text style={styles.label}>Location:</Text>
          <Text style={styles.text}>{character.location}</Text>

          <Text style={styles.label}>HP:</Text>
          <Text style={styles.text}>{character.hp}</Text>

          <Text style={styles.label}>StrongVs:</Text>
          <Text style={styles.text}>{character.strongVs}</Text>

          <Text style={styles.label}>WeakTo:</Text>
          <Text style={styles.text}>{character.weakTo}</Text>

          <Text style={styles.label}>ImmuneTo:</Text>
          <Text style={styles.text}>{character.immuneTo}</Text>
        </ScrollView>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#a59b90',
  },
  box: {
    width: '80%',
    backgroundColor: 'white',
    borderRadius: 10,
    padding: 20
  },
  content: {
    justifyContent: 'flex-start',
    paddingVertical: 10,
  },
  label: {
    fontWeight: 'bold',
    marginBottom: 5,
  },
  text: {
    marginBottom: 10,
  },
});

export default ViewScreen;
