/*import React, { useContext,useState,useEffect } from "react";
import { View, Text, Alert,StyleSheet, TextInput } from "react-native";
import { DataTable, Button as PaperButton} from 'react-native-paper';
import { CharacterContext } from '../CharacterContext';
import { ItemContext } from '../ItemContext';
import DeletePopUp from "../components/DeletePopUp";
import WeaknessChart from "../components/WeaknessChart";
import { IconButton } from 'react-native-paper';
import { PouchDBContext, synchronizeWithServer } from "../database";
import PouchDB from 'pouchdb';
import {isReachable} from '../ServerReachable';

const HomeScreen = ({ navigation }) => {
  const { characters, setItems,fetchCharacters, deleteCharacter } = useContext(CharacterContext);
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedCharacter, setSelectedCharacter] = useState(null);
  const [itemsPerPage, setItemsPerPage] = useState(5);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(4);
  const [pagCharacters, setPagCharacters] = useState([]);
  const [nrOfCharacters,setNrOfCharacters] =useState();
  //let retryAttempts=true;
  const [retryAttempts,setRetryAttempts]=useState(true);

  function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  const fetchCharactersPaginated = async (page, itemsPerPage) => {
    //console.log("bf",characters);
    try {
      //console.log("char",characters);
      const token = sessionStorage.getItem('token');
      const response = await fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/characters/?page=${page}&pageSize=${itemsPerPage}`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      //console.log(data.paginatedCharacters);
      //console.log(data.totalPage);
      setPagCharacters(data.paginatedCharacters);
      setTotalPages(data.totalPages);
      setNrOfCharacters(data.charLength);
      //const tempRetryAttempts = true;
      //setRetryAttempts(tempRetryAttempts);
    } catch (error) {
      //console.log("retryatte", retryAttempts);
      //if (!retryAttempts)
      //  window.alert("Server Error. The server is not running. Please start the server and try again.");
      //const tempRetryAttempts = false;
      //setRetryAttempts(tempRetryAttempts);
      //await delay(1000);
      //window.alert("Server Error. The server is not running. Please start the server and try again.");
    }
  };

  const serverDownPagCharacters = (currentPage, itemsPerPage, characters)=>{
    //console.log("sd",characters);
    //console.log("cp",currentPage);
    //console.log("ip",itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = currentPage * itemsPerPage;

    const paginatedCharacters = characters
      .slice()
      .sort((a, b) => a.name.localeCompare(b.name))
      .slice(startIndex, endIndex);

    //console.log("pag",paginatedCharacters);

    const totalPagesTemp = Math.ceil(characters.length / itemsPerPage);
    const charLengthTemp = paginatedCharacters.length;
    setTotalPages(totalPagesTemp);
    setNrOfCharacters(charLengthTemp);
    setPagCharacters(paginatedCharacters);
    //console.log("p",pagCharacters);
  };

  const syncBackend = async () => {
    const tempRetryAttempts=true;
    setRetryAttempts(tempRetryAttempts);
    await synchronizeWithServer(`https://mpp-backend-424109.ew.r.appspot.com/characters/charsync`,'char');
    await delay(2000);
    await synchronizeWithServer(`https://mpp-backend-424109.ew.r.appspot.com/items/itemsync`,'item');
    await delay(2000);
    await synchronizeWithServer(`https://mpp-backend-424109.ew.r.appspot.com/characters/chardelsync`,'charDel');
    await delay(2000);
    await synchronizeWithServer(`https://mpp-backend-424109.ew.r.appspot.com/items/itemdelsync`,'itemDel');
    await delay(2000);
  };

  /*
    async function syncDataWithServer(entries) {
     // Implement your server sync logic here
     console.log('Syncing data with server:', entries);
    }

    // Retrieve and sync data
    const query = `SELECT * FROM user_entries;`;
    const result = db.exec(query);
    const entries = result[0].values;
    syncDataWithServer(entries);


  useEffect(() => {
    const fetchDataInterval = setInterval(async () => {
      //console.log(await isReachable());
      if(!await isReachable())
      {
        //console.log("not reach:", retryAttempts);
        if(retryAttempts) {
          window.alert("Server Error. The server is not running. Please start the server and try again.");
          const tempRetryAttempts = false;
          setRetryAttempts(tempRetryAttempts);
        }
        console.log(retryAttempts);
        serverDownPagCharacters(currentPage,itemsPerPage,characters);
      }
      else {
        console.log("before not retry:", retryAttempts);
        if(!retryAttempts)
        {
          console.log("inside not retry:", retryAttempts);
          syncBackend();
          const tempRetryAttempts = true;
          setRetryAttempts(tempRetryAttempts);
        }
        //console.log("Inside items",items);
        fetchCharactersPaginated(currentPage, itemsPerPage);
        //fetchCharacters();
      }
    }, 1000);
    return () => clearInterval(fetchDataInterval);
  }, [characters, currentPage, pagCharacters, itemsPerPage, retryAttempts, isReachable]);

  const handleDeleteConfirmation = (id) => {
    setSelectedCharacter(id);
    setModalVisible(true);
  };

  const handleDelete = () => {
    if (selectedCharacter) {
      deleteCharacter(selectedCharacter);
      setItemsPerPage(5);
      setCurrentPage(1);
      setSelectedCharacter(null);
      setModalVisible(false);
    }
  };
  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage- 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handleLastPrevPage = () => {
    setCurrentPage(1);
  };

  const handleLastNextPage = () => {
    setCurrentPage(totalPages);
  };

  //const username = sessionStorage.getItem('username');
  //const password = sessionStorage.getItem('password');

  return (
    <View style={{backgroundColor:'#a59b90'}}>
      <View styles={styles.container}>
        <PaperButton mode="contained" onPress={() => navigation.navigate('Add')} style={[styles.roundedButton, { backgroundColor: '#363135' }]}>
          Add new record
        </PaperButton>
      </View>
      <DataTable testID="data-table">
        <DataTable.Header>
          <DataTable.Title style={{ flex: 1.8 }}>Name</DataTable.Title>
          <DataTable.Title style={{ flex: 2 }}>Location</DataTable.Title>
          <DataTable.Title style={{ flex: 0.5 }}>HP</DataTable.Title>
          <DataTable.Title style={ { flex: 2 }}>StrongVs</DataTable.Title>
          <DataTable.Title style={ { flex: 1.5 }}>WeakTo</DataTable.Title>
          <DataTable.Title style={{ flex: 2 }}>ImmuneTo</DataTable.Title>
          <DataTable.Title >Details</DataTable.Title>
          <DataTable.Title >Edit</DataTable.Title>
          <DataTable.Title >Delete</DataTable.Title>
        </DataTable.Header>
        {Array.isArray(pagCharacters) && pagCharacters.length > 0 ? (
          Array.from(pagCharacters).map((item) => (
            <DataTable.Row key={item.cid} onPress={() => navigation.navigate("View Details", { id: item.cid })}>
              <DataTable.Cell style={{ flex: 1.8 }}>{item.name}</DataTable.Cell>
              <DataTable.Cell style={{ flex: 2 }}>{item.location}</DataTable.Cell>
              <DataTable.Cell style={{ flex: 0.5 }}>{item.hp}</DataTable.Cell>
              <DataTable.Cell style={{ flex: 2 }}>{item.strongVs}</DataTable.Cell>
              <DataTable.Cell style={{ flex: 1.5 }}>{item.weakTo}</DataTable.Cell>
              <DataTable.Cell style={{ flex: 2 }}>{item.immuneTo}</DataTable.Cell>
              <DataTable.Cell>
                <PaperButton mode="contained" onPress={() => navigation.navigate("View Details", { id: item.cid })} style={styles.button}>
                  Details
                </PaperButton>
              </DataTable.Cell>
              <DataTable.Cell>
                <PaperButton mode="contained" onPress={() => navigation.navigate("Edit", { id: item.cid })} style={styles.button}>
                  Edit
                </PaperButton>
              </DataTable.Cell>
              <DataTable.Cell>
                <PaperButton testID="delete-button" mode="contained" onPress={() => handleDeleteConfirmation(item.cid)} style={[styles.button, { backgroundColor: "#D2042D" }]}>
                  Delete
                </PaperButton>
              </DataTable.Cell>
            </DataTable.Row>
          ))
        ) : (
          <DataTable.Row>
            <DataTable.Cell>No data available</DataTable.Cell>
          </DataTable.Row>
        )}
        <View style={styles.paginationContainer}>
          <Text>
            Nr. Rows:
          </Text>
          <TextInput
            style={styles.inputPages}
            onChangeText={value => {
              const newValue = parseInt(value);
              setItemsPerPage(newValue);
              if (newValue === 0) {
                setCurrentPage(1);
                setItemsPerPage(5);
              } else {
                console.log(retryAttempts);
                if(retryAttempts)
                  fetchCharactersPaginated(1, newValue);
                else
                  serverDownPagCharacters(1, newValue, characters);
              }
            }}
          />
          <Text style={{ paddingLeft: 15 }}>
            Go to Page:
          </Text>
          <TextInput
            style={styles.inputRows}
            onChangeText={value => {
              setCurrentPage(parseInt(value));
              if (value === '0') {
                setCurrentPage(1);
                setItemsPerPage(5);
              } else {
                console.log(retryAttempts);
                if(retryAttempts)
                  fetchCharactersPaginated(value, itemsPerPage);
                else
                  serverDownPagCharacters(value, itemsPerPage, characters);
              }
            }}
          />
          <View style={{marginLeft:15, marginRight: 15, alignItems:'center'}}>
            <Text>
              {currentPage === 0 || itemsPerPage === 0
                ? `Page: 1 out of 1, ${nrOfCharacters} entries`
                : `Page: ${currentPage} out of ${totalPages}, ${nrOfCharacters} entries`}
            </Text>
          </View>
          <IconButton
            icon="skip-previous"
            onPress={handleLastPrevPage}
          />
          <IconButton
            icon="chevron-left"
            onPress={handlePrevPage}
          />
          <IconButton
            icon="chevron-right"
            onPress={handleNextPage}
          />
          <IconButton
            icon="skip-next"
            onPress={handleLastNextPage}
          />
        </View>
      </DataTable>
      <PaperButton mode="contained" onPress={() => navigation.navigate("Chart")} style={[styles.roundedButton, { backgroundColor: "#363135" }]}>
        View Weakness Chart
      </PaperButton>
      <PaperButton mode="contained" onPress={() => navigation.navigate("Items")} style={[styles.roundedButton, { backgroundColor: "#363135",marginTop:10 }]}>
        View Loot
      </PaperButton>
      <PaperButton
        mode="contained"
        onPress={syncBackend}
        style={[styles.roundedButton, { backgroundColor: "#363135", marginTop: 10 }]}
      >
        Sync Backend
      </PaperButton>
      <DeletePopUp modalVisible={modalVisible} setModalVisible={setModalVisible} handleDelete={handleDelete}/>
    </View>
  );
};

const styles = StyleSheet.create({
  inputRows: {
    marginTop:10,
    height:25,
    marginLeft:15,
    marginBottom: 10,
    borderRadius: 5,
    borderColor:'black',
    borderWidth: 1,
  },
  inputPages: {
    marginTop:10,
    height:25,
    marginLeft:15,
    marginBottom: 10,
    borderRadius: 5,
    borderColor:'black',
    borderWidth: 1,
  },
  paginationContainer:{
    paddingLeft:"40vh",
    flexDirection:'row',
    alignItems: 'center',
    backgroundColor:'white',
  },
  button: {
    backgroundColor: '#d9b13b',
    borderRadius: 20,
    marginVertical: 5,
  },
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  text: {
    color: 'white',
    fontSize: 16,
  },
});

export default HomeScreen;
*/

import {PlayersContext} from "../PlayersContext";
import React, { useContext,useState,useEffect } from "react";
import { FlatList } from "react-native-web";
import { ScrollView, View, Text } from "react-native";
import {Button as PaperButton} from 'react-native-paper';
import { isElementSelected } from "@testing-library/react-native/build/helpers/accessiblity";

const styles = {
  app: {
    flex: 10, // the number of columns you want to devide the screen into
    marginHorizontal: "auto",
    width: 1500
  },
  item: {
    flex: 1,
    maxWidth: "25%", // 100% devided by the number of rows you want
    alignItems: "center",

    // my visual styles; not important for the grid
    padding: 10,
    backgroundColor: "rgba(249, 180, 45, 0.25)",
    borderWidth: 1.5,
    borderColor: "#fff"
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#a59b90',
  },
  box: {
    width: '80%',
    borderRadius: 10,
    padding: 20
  },
  content: {
    justifyContent: 'flex-start',
    paddingVertical: 10,
    alignItems: 'center'
  },
  label: {
    fontWeight: 'bold',
    marginBottom: 5,
  },
  text: {
    marginBottom: 10,
  },
  button: {
    backgroundColor: '#d9b13b',
    borderRadius: 20,
    marginVertical: 5,
  },
};

// RN Code
const Item = ({ item }) => {
  //const [image,setImage ] = useState(false);

  //const handleImageState = () => {
  //  setImage(true);
  //};

  return(
    <View style={styles.container}
      //onPress={() => handleImageState()}
    >
      <View style={{
        borderRadius: 10,
        backgroundColor: item.isTitular === false ? 'yellow': 'gray',
        width: '80%',
        padding: 20,
        alignItems: 'center'
      }}>
        <ScrollView contentContainerStyle={styles.content}>
          <Text style={styles.label}>Name:</Text>
          <Text style={styles.text}>{item.Name}</Text>

          <Text style={styles.label}>Number:</Text>
          <Text style={styles.text}>{item.shirt_no}</Text>
        </ScrollView>
      </View>
    </View>
  );
};

const HomeScreen = ({ navigation }) => {
  const { players,fetchPlayers } = useContext(PlayersContext);
  const [sortType,setSortType ] = useState(0);
  useEffect(() => {
    const fetchDataInterval = setInterval(async () => {
      console.log("in effect:", players);
      //console.log(await isReachable());
      await fetchPlayers(sortType);
    }, 1000);
    return () => clearInterval(fetchDataInterval);
  }, [players,sortType, setSortType()]);

  return (
    <View style={styles.app}>
      <View styles={styles.container}>
        <PaperButton mode="contained" onPress={() => setSortType(0)} style={[styles.roundedButton, { backgroundColor: '#363135', margin:10 }]}>
          Sort By Jersey Number
        </PaperButton>
        <PaperButton mode="contained" onPress={() => setSortType(1)} style={[styles.roundedButton, { backgroundColor: '#363135',  margin:10 }]}>
          Sort By Name
        </PaperButton>
      </View>
      <FlatList
        data={players}
        numColumns={10}
        renderItem={Item}
        keyExtractor={(item) => item.id}
      />
    </View>
  );
}

export default HomeScreen;
