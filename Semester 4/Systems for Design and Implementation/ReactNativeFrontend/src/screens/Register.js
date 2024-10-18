import React, { useState } from 'react';
import { View, TextInput, StyleSheet, Text } from "react-native";
import { Button as PaperButton } from 'react-native-paper';

const RegisterComponent = ({navigation}) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = async () => {
    // Simulate a login process
    // const fakeUserId = Math.random().toString(36).substring(7); // Generate a random user ID
    // setUsername(fakeUserId);

    // Store the user ID in Session Storage
    // sessionStorage.setItem('username', username);
    // sessionStorage.setItem('password', password);

    let registerItem = { username: username, password: password };
    const response = fetch('https://backendmpp-vsrjvd47ea-od.a.run.app/users/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify(registerItem)
      //credentials: 'include',
    }).then(response => {
      return response.json();
    }).then(data=>{
      console.log("register data:",data);
      console.log("frontend name:", username);
      console.log("frontend pass:", password);
      if (data.token) {
        navigation.navigate("Login");
      } else {
        window.alert('Register Failed.');
      }}).catch(error => {
      window.alert(error.message);
    });
  };

  return (
    <View style={styles.container}>
      <View style={styles.box}>
        <Text style={styles.label}>Username</Text>
        <TextInput
          style={styles.textInput}
          value={username}
          onChangeText={setUsername}
          placeholder="Enter username"
        />
        <Text style={styles.label}>Password</Text>
        <TextInput
          style={styles.textInput}
          value={password}
          onChangeText={setPassword}
          placeholder="Enter password"
          //secureTextEntry={true} // Hides the password input
        />
        <PaperButton mode="contained" labelStyle={styles.buttonLabel} onPress={handleRegister} style={{backgroundColor: '#363135', marginBottom:10}}>Register</PaperButton>
        <PaperButton mode="contained" labelStyle={styles.buttonLabel} onPress={() => navigation.navigate('Login')} style={{backgroundColor: '#363135'}}>Already Registered? Log In</PaperButton>
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

export default RegisterComponent;
