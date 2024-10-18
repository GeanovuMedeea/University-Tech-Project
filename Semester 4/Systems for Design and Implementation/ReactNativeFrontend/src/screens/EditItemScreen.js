import React, { useState, useEffect, useContext } from "react";
import { View,Text,ScrollView, TextInput, StyleSheet, Alert } from 'react-native';
import { Button as PaperButton } from 'react-native-paper';
import { CharacterContext } from "../CharacterContext";
import {ItemContext} from "../ItemContext";

const EditItemScreen = ({ navigation, route }) => {
  const {characters,fetchCharacters,fetchCharacter} = useContext(CharacterContext);
  const{fetchItem, updateItem}=useContext(ItemContext);
  const { id } = route.params; // Get id from URL params
  const [message, setMessage] = useState('');
  const [item, setItem] = useState({
    cid: '',
    itemName: '',
    itemDrop: '',
    runeCount: ''
  });

  useEffect(() => {
    getItem();
  }, []);

  const getItem = () => {
    fetchItem(id)
      .then(item => {
        setItem(item);
      })
  };

  const handleUpdate = () => {
    updateItem(id,item);
    setMessage("Item updated successfully!");
  };

  const handleChange = (key, value) => {
    setItem(prevItem => ({
      ...prevItem,
      [key]: value
    }));
  };

  const handleCharacterSelection = (cid) => {
    setItem(prevItem => ({
      ...prevItem,
      cid: cid
    }));
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
        value={item.itemName}
        onChangeText={value => handleChange('itemName', value)}
      />
      <TextInput
        style={styles.input}
        placeholder="Item Drop"
        value={item.itemDrop.toString()}
        onChangeText={value => handleChange('itemDrop', value)}
        keyboardType="numeric"
      />
      <TextInput
        style={styles.input}
        placeholder="Rune Count"
        value={item.runeCount.toString()}
        onChangeText={value => handleChange('runeCount', value)}
        keyboardType="numeric"
      />
      <PaperButton mode="contained" onPress={handleUpdate} style={styles.button}>
        Update
      </PaperButton>
      <TextInput style={styles.messageInput} placeholder={message} />
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
  itemInputs: {
    flex: 1,
    marginLeft: 10,
    padding: 10,
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
  text: {
    fontSize: 20,
    fontWeight: 'bold',
    color: 'black',
  },
});

export default EditItemScreen;
