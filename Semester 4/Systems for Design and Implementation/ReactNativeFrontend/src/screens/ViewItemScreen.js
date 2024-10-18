import React, { useState, useEffect, useContext } from "react";
import { View, Text, ScrollView, StyleSheet, Alert } from 'react-native';
import {CharacterContext} from "../CharacterContext";
import {ItemContext} from "../ItemContext";

const ViewItemScreen = ({ navigation, route }) => {
  const { fetchItem } = useContext(ItemContext);
  const { id,characterName } = route.params;
  const [item, setItem] = useState({
    lid:'',
    cid: '',
    itemName: '',
    itemDrop: '',
    runeCount: ''
  });

  useEffect(() => {
    viewItem();
  }, []);

  const viewItem = () => {
    fetchItem(id)
      .then(item => {
        setItem(item);
      })
  };

  return (
    <View style={styles.container}>
      <View style={styles.box}>
        <ScrollView contentContainerStyle={styles.content}>
          <Text style={styles.label}>Character:</Text>
          <Text style={styles.text}>{characterName}</Text>

          <Text style={styles.label}>Item Name:</Text>
          <Text style={styles.text}>{item.itemName}</Text>

          <Text style={styles.label}>Item Drop:</Text>
          <Text style={styles.text}>{item.itemDrop}</Text>

          <Text style={styles.label}>Rune Count:</Text>
          <Text style={styles.text}>{item.runeCount}</Text>
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

export default ViewItemScreen;
