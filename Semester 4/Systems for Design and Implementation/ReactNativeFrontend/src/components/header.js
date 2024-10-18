import React from 'react';
import { View, Image, Text, StyleSheet } from 'react-native';
import LogoImage from '../assets/images/Elden-Ring-Logo.png';
import { Button as PaperButton} from 'react-native-paper';

function Header({ accessibilityLabel }) {
  return (
    <View
      accessible accessibilityLabel={accessibilityLabel}
      style={styles.container}
      testID='header-view'>
      <Image
        source={LogoImage}
        style={styles.logo}
        accessibilityLabel="Logo"
        testID="logo-image"
      />
      <Text style={styles.text}
            testID='header-text'
      >Database of the champions' stats</Text>
      <View styles={styles.container}>
        <PaperButton mode="contained" onPress={() => { sessionStorage.removeItem('token');sessionStorage.removeItem('username');sessionStorage.removeItem('password');navigation.navigate('Login');}} style={[styles.roundedButton, { backgroundColor: '#a59b90', marginLeft: 10 }]}>
          Log out
        </PaperButton>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#363135',
    padding: 10,
    flexDirection: 'row',
    alignItems: 'center',
  },
  logo: {
    width: 100,
    height: 50,
    marginRight: 30,
  },
  text: {
    color: 'white',
    fontSize: 16,
  },
  button: {
    paddingLeft: 10,
  },
});

export default Header;
