import React, { useState } from 'react';
import { View, TextInput, StyleSheet, Text } from "react-native";
import { Button as PaperButton } from 'react-native-paper';

const LoginComponent = ({navigation}) => {
  const [token, setToken] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    sessionStorage.setItem('username', username);
    sessionStorage.setItem('password', password);
    // Simulate a login process
    // const fakeUserId = Math.random().toString(36).substring(7); // Generate a random user ID
    // setUsername(fakeUserId);
    let loginItem = { username: username, password: password };
    // Replace 'http://localhost:3000/login' with your backend's login URL
    const response = fetch('https://backendmpp-vsrjvd47ea-od.a.run.app/users/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: JSON.stringify(loginItem),
    }).then(response => {
      return response.json();
    }).then(data => {
      //console.log(data);
      const { token } = data;
      setToken(token);
      sessionStorage.setItem('token', token);
      console.log("frontend name:", username);
      console.log("frontend pass:", password);
      console.log("frontend token:", token);
      if (data.token) {
        navigation.navigate("Home");
      } else {
        window.alert('Log In Failed.');
      }
    }).catch(error => {
      console.log(error.message);
      window.alert(error.message);
    });
  };

  /*const LoginComponent = ({navigation}) => {
    const [token, setToken] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
      sessionStorage.setItem('username', username);
      sessionStorage.setItem('password', password);
      // Simulate a login process
      // const fakeUserId = Math.random().toString(36).substring(7); // Generate a random user ID
      // setUsername(fakeUserId);
      let loginItem = { username: username, password: password };
      // Replace 'http://localhost:3000/login' with your backend's login URL
      const response = fetch('http://localhost:3000/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginItem),
        credentials: 'include',
      }).then(response => {
        return response.json();
      }).then(data=>{
        //setToken(data.token);
        //sessionStorage.setItem('token', token);
        console.log("frontend name:", username);
        console.log("frontend pass:", password);
        //console.log("frontend token:", token);
        navigation.navigate("Home");}).catch(error => {
        window.alert(error.message);
      });
    };*/

  return (
    <View style={styles.container}>
      <View style={styles.box}>
        <View>
          <Text style={styles.label}>Username</Text>
        </View>
        <View>
          <TextInput
            style={styles.textInput}
            value={username}
            onChangeText={setUsername}
            placeholder="Enter username"
          />
        </View>
        <View>
          <Text style={styles.label}>Password</Text>
        </View>
        <View>
          <TextInput
            style={styles.textInput}
            value={password}
            onChangeText={setPassword}
            placeholder="Enter password"
            //secureTextEntry={true} // Hides the password input
          />
        </View>
        <View>
          <PaperButton mode="contained" labelStyle={styles.buttonLabel} onPress={handleLogin} style={{backgroundColor: '#363135', marginBottom:10}}>Log In</PaperButton>
        </View>
        <View>
          <PaperButton mode="contained" labelStyle={styles.buttonLabel} onPress={() => navigation.navigate('Register')} style={{backgroundColor: '#363135'}}>Register</PaperButton>
        </View>
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
    padding: 20,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
  textInput: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    paddingLeft: 10,
    marginBottom: 10,
    borderRadius: 5,
  },
  label: {
    fontWeight: 'bold',
    marginBottom: 5,
  },
  buttonLabel: {
    color: 'white',
  },
});

export default LoginComponent;
