// App.js
import React from 'react';
import {NavigationContainer} from "@react-navigation/native";
import {createStackNavigator} from '@react-navigation/stack';
import HomeScreen from './screens/HomeScreen';
import AddScreen from './screens/AddScreen';
import EditScreen from './screens/EditScreen';
import { CharacterProvider } from './CharacterContext'; // Import the CharacterProvider


const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <CharacterProvider> {/* Wrap your components with CharacterProvider */}
        <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Add" component={AddScreen} />
        <Stack.Screen name="Edit" component={EditScreen} />
      </Stack.Navigator>
      </CharacterProvider>
    </NavigationContainer>
  );
}
