// App.js
/*import React, { useEffect, useState } from "react";
import {NavigationContainer} from "@react-navigation/native";
import {createStackNavigator} from '@react-navigation/stack';
import HomeScreen from './screens/HomeScreen';
import AddScreen from './screens/AddScreen';
import EditScreen from './screens/EditScreen';
import { CharacterProvider } from './CharacterContext';
import ViewScreen from './screens/ViewScreen';
import Footer from './components/footer';
import Header from './components/header';
import WeaknessChart from './components/WeaknessChart';
//add routing!!! when you search in browser http://localhost:3000/Add it goes to the add screen and so forth
//add unit tests (jest or playwright)
//split code into components
//use materialui
import {  View,Linking, Text, Alert} from "react-native";
import NetInfo from '@react-native-community/netinfo';
import isOnline from 'is-online';
import WebSocketComponent from './WebSocketComponent';
import { ItemProvider } from "./ItemContext";
import ItemsScreen from "./screens/ItemsScreen";
import AddItemScreen from "./screens/AddItemScreen";
import ViewItemScreen from "./screens/ViewItemScreen";
import EditItemScreen from "./screens/EditItemScreen";
import {PouchDBProvider } from "./database";
import LoginComponent from "./screens/Login";
import Register from "./screens/Register";


const prefix = 'c';

const linking = {
  prefixes: [prefix],
  config: {
    screens: {
      Home: 'home',
      Add: 'home/add',
      "View Details": 'home/details',
      Edit: 'home/edit',
      Chart: 'home/chart',
      Items:'home/items',
      "Add Item": 'home/items/add',
      "Edit Item":'home/items/edit',
      "View Item Details": 'home/items/details',
      Login: 'login',
      Register: 'register'
    },
  },
};

const Stack = createStackNavigator();
export default function App() {
  useEffect(() => {
    const checkOnlineStatus = async () => {
      try {
        const online = await isOnline();
        if(!online) {
          //window.alert("Please check your internet connection!");
        }
      } catch (error) {
        //window.alert(error);
      }
    };
    //checkOnlineStatus();
    //const interval = setInterval(checkOnlineStatus, 5000);
    //return () => clearInterval(interval);
  }, []);

  /*const [isOnline, setIsOnline] = useState(window.navigator.onLine);

  useEffect(() => {
    function handleOnline() {
      setIsOnline(true);
    }

    function handleOffline() {
      setIsOnline(false);
    }

    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);
    window.alert(isOnline);
    if(!isOnline)
      window.alert("Please check your internet connection!");

  }, []);

  return (
    <NavigationContainer linking={linking}>
      <View style={{ flex: 1 }}>
        <Header
          accessibilityLabel="header"
        />
        <PouchDBProvider>
        <CharacterProvider>
          <ItemProvider>
          <WebSocketComponent />
          <Stack.Navigator initialRouteName="Login">
            <Stack.Screen name="Home" component={HomeScreen}/>
            <Stack.Screen name="Add" component={AddScreen} />
            <Stack.Screen name="Edit" component={EditScreen} />
            <Stack.Screen name="View Details" component={ViewScreen} />
            <Stack.Screen name="Chart" component={WeaknessChart} />
            <Stack.Screen name="Items" component={ItemsScreen} />
            <Stack.Screen name="Add Item" component={AddItemScreen}/>
            <Stack.Screen name="View Item Details" component={ViewItemScreen}/>
            <Stack.Screen name="Edit Item" component={EditItemScreen}/>
            <Stack.Screen name="Login" component={LoginComponent}/>
            <Stack.Screen name="Register" component={Register}/>
          </Stack.Navigator>
          </ItemProvider>
        </CharacterProvider>
        </PouchDBProvider>
        <Footer accessibilityLabel="footer"/>
      </View>
    </NavigationContainer>
  );
}*/

// App.js
import React, { useEffect, useState } from "react";
import {NavigationContainer} from "@react-navigation/native";
import {createStackNavigator} from '@react-navigation/stack';
import HomeScreen from './screens/HomeScreen';
import { PlayersProvider } from './PlayersContext';

import {  View,Linking, Text, Alert} from "react-native";

const prefix = 'c';

const linking = {
  prefixes: [prefix],
  config: {
    screens: {
      Home: 'home'
    },
  },
};

const Stack = createStackNavigator();
export default function App() {

  return (
    <NavigationContainer linking={linking}>
      <View style={{ flex: 1 }}>
        <PlayersProvider>
          <Stack.Navigator initialRouteName="Login">
            <Stack.Screen name="Home" component={HomeScreen}/>
          </Stack.Navigator>
        </PlayersProvider>
      </View>
    </NavigationContainer>
  );
}



