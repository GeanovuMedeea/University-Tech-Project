// AddScreen.js
import React, { useContext, useState} from 'react';
import { View, TextInput, Button, Alert, StyleSheet, Text, ScrollView } from "react-native";
import { CharacterContext } from '../CharacterContext';
import {ItemContext} from "../ItemContext";
import { Button as PaperButton } from 'react-native-paper';

const AddItemScreen = ({ navigation }) => {
  const [itemName, setItemName] = useState('');
  const [itemDrop, setItemDrop] = useState('');
  const [runeCount, setRuneCount] = useState('');
  const {addItem} = useContext(ItemContext);
  const [cid, setCID] = useState('');
  const {characters} = useContext(CharacterContext);

  const handleAdd = () => {
    const newItem = {
      itemName,
      itemDrop: parseInt(itemDrop),
      runeCount: parseInt(runeCount),
      cid:cid
    };

    addItem(newItem);
    navigation.navigate('Items');

  };
  const handleCharacterSelection = (cid) => {
    setCID(cid);
  };

  return (
    <View style={styles.container}>
      <View style={styles.leftContainer}>
        <Text style={styles.text}>Characters</Text>
        <ScrollView style={styles.characterScrollView}>
          {characters.map(character => (
            <PaperButton
              key={character.cid}
              onPress={() => handleCharacterSelection(character.cid)}
              style={styles.characterButton}
              testID={`characterButton-${character.cid}`}
            >
              {character.name}
            </PaperButton>
          ))}
        </ScrollView>
      </View>
      <View style={styles.rightContainer}>
        <TextInput
          style={styles.input}
          placeholder="Item Name"
          value={itemName}
          onChangeText={setItemName}
        />
        <TextInput
          style={styles.input}
          placeholder="Item Drop"
          value={itemDrop}
          onChangeText={setItemDrop}
          keyboardType="numeric"
        />
        <TextInput
          style={styles.input}
          placeholder="Rune Count"
          value={runeCount}
          onChangeText={setRuneCount}
          keyboardType="numeric"
        />
        <PaperButton mode="contained" onPress={handleAdd} style={styles.button}>
          Add
        </PaperButton>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  leftContainer: {
    flex: 1,
    paddingRight: 10,
  },
  rightContainer: {
    marginTop:40,
    flex: 2,
    paddingLeft: 10,
  },
  container: {
    flex: 1,
    flexDirection: 'row',
    //justifyContent: 'center',
    //alignItems: 'center',
    backgroundColor: '#a59b90',
    paddingHorizontal: 20,
  },
  characterScrollView: {
    flexGrow: 1,
    flex: 1,
    backgroundColor: 'white',
    margin:10,
  },
  characterButton: {
    backgroundColor: '#363135',
    color:'white',
    marginVertical: 5,
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

export default AddItemScreen;
