// HomeScreen.js
import React, { useContext } from 'react';
import { View, Button } from 'react-native';
import { DataTable } from 'react-native-paper';
import { CharacterContext } from '../CharacterContext';

const HomeScreen = ({ navigation }) => {
  const { characters } = useContext(CharacterContext);

  return (
    <View>
      <DataTable>
        <DataTable.Header>
          <DataTable.Title>Id</DataTable.Title>
          <DataTable.Title>Name</DataTable.Title>
          <DataTable.Title>Location</DataTable.Title>
          <DataTable.Title>HP</DataTable.Title>
          <DataTable.Title>StrongVS</DataTable.Title>
          <DataTable.Title>WeakTo</DataTable.Title>
          <DataTable.Title>ImmuneTo</DataTable.Title>
        </DataTable.Header>
        {characters.map((item) => (
          <DataTable.Row key={item.cid} onPress={() => navigation.navigate('Edit', { id: item.cid })}>
            <DataTable.Cell>{item.cid}</DataTable.Cell>
            <DataTable.Cell>{item.name}</DataTable.Cell>
            <DataTable.Cell>{item.location}</DataTable.Cell>
            <DataTable.Cell>{item.hp}</DataTable.Cell>
            <DataTable.Cell>{item.strongVS}</DataTable.Cell>
            <DataTable.Cell>{item.weakTo}</DataTable.Cell>
            <DataTable.Cell>{item.immuneTo}</DataTable.Cell>
          </DataTable.Row>
        ))}
      </DataTable>
      <Button title="Add New" onPress={() => navigation.navigate('Add')} />
    </View>
  );
};

export default HomeScreen;
