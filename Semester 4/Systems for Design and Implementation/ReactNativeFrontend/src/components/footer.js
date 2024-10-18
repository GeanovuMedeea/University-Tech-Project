import { Text, View } from "react-native";
import React from "react";

function Footer({ accessibilityLabel }){
  return(
  <View
    accessible accessibilityLabel={accessibilityLabel}
    style={{ backgroundColor: '#363135',
    padding: 20,
    justifyContent: 'center',
    alignItems: 'center'}}
    testID="footer-view"
  >
    <Text style={{ color: '#fff' }}
          testID="footer-text"
    >2023-2024 ☆ Geanovu Medeea Elena</Text>
  </View>
  )
}

export default Footer;
