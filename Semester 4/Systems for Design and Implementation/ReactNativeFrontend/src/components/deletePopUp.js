import React from "react";
import { Modal, View, Text, StyleSheet } from 'react-native';
import { Button as PaperButton } from 'react-native-paper';

const DeletePopUp = ({ modalVisible, setModalVisible, handleDelete }) => {
  return (
    <Modal
      visible={modalVisible}
      transparent
      onRequestClose={() => setModalVisible(false)}>
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: '#a59b90' }}>
        <View style={{ backgroundColor: 'white', padding: 20, borderRadius: 10 }}>
          <Text>Are you sure you want to delete this record?</Text>
          <View style={{ flexDirection: 'row', justifyContent: 'space-between', marginTop: 20 }}>
            <PaperButton mode="contained" onPress={() => setModalVisible(false)} style={[styles.button, { backgroundColor: '#363135' }]}>
              Cancel
            </PaperButton>
            <PaperButton mode="contained" onPress={handleDelete} style={[styles.button, { backgroundColor: '#D2042D' }]}>
              Delete
            </PaperButton>
          </View>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  button: {
    marginVertical: 5,
    borderRadius: 20,
  },
});

export default DeletePopUp;
