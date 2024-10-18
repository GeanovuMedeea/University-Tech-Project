import React, { useContext,useState,useEffect } from "react";
import { View, Text, Alert,StyleSheet, TextInput } from "react-native";
import { DataTable, Button as PaperButton} from 'react-native-paper';
import DeletePopUp from "../components/DeletePopUp";
import WeaknessChart from "../components/WeaknessChart";
import { IconButton } from 'react-native-paper';
import { ItemContext } from "../ItemContext";
import {CharacterContext} from "../CharacterContext";
import { synchronizeWithServer } from "../database";
import { isReachable } from "../ServerReachable";

const ItemsScreen = ({ navigation }) => {
  const { items, setItems,fetchItems, deleteItem } = useContext(ItemContext);
  const { characters } = useContext(CharacterContext);
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [itemsPerPage, setItemsPerPage] = useState(5);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(4);
  const [pagItems, setPagItems] = useState([]);
  const [nrOfItems,setNrOfItems] =useState();
  const [retryAttempts,setRetryAttempts]=useState(true);

  function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  const fetchItemsPaginated = async (page,itemsPerPage) => {
    try{
      const token = sessionStorage.getItem('token');
      const response = await fetch(`https://backendmpp-vsrjvd47ea-od.a.run.app/items/?page=${page}&pageSize=${itemsPerPage}`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
      const data = await response.json();
      //console.log(data.paginatedItems);
      //console.log(data.totalPage);
      setPagItems(data.paginatedItems);
      console.log("pag items:", pagItems);
      setTotalPages(data.totalPages);
      setNrOfItems(data.itemLength);
      //const tempRetryAttempts=true;
      //setRetryAttempts(tempRetryAttempts);
    } catch (error) {
      //console.log("retryatte", retryAttempts);

      //if(retryAttempts)
      //  window.alert("Server Error. The server is not running. Please start the server and try again.");
      //const tempRetryAttempts=false;
      //setRetryAttempts(tempRetryAttempts);
      //await delay(1000);
      //await delay(1000);
      //window.alert("Server Error. The server is not running. Please start the server and try again.");
    }
  };

  const serverDownPagItems = (currentPage, itemsPerPage, items)=>{
    //console.log(items);
    //console.log("sd",characters);
    //console.log("cp",currentPage);
    //console.log("ip",itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = currentPage * itemsPerPage;

    const paginatedItems = items
      .slice()
      .sort((a, b) => a.itemName.localeCompare(b.itemName))
      .slice(startIndex, endIndex);

    //console.log("pag",paginatedCharacters);

    const totalPagesTemp = Math.ceil(items.length / itemsPerPage);
    const itemLengthTemp = paginatedItems.length;
    setTotalPages(totalPagesTemp);
    setNrOfItems(itemLengthTemp);
    setPagItems(paginatedItems);
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

  useEffect(() => {
    const fetchDataInterval = setInterval(async () => {
      if (!await isReachable()) {
        if(retryAttempts)
        {
          window.alert("Server Error. The server is not running. Please start the server and try again.");
          const tempRetryAttempts = false;
          setRetryAttempts(tempRetryAttempts);
        }
        //console.log(retryAttempts);
        serverDownPagItems(currentPage,itemsPerPage,items);
      }
      else {
        if(!retryAttempts)
        {
          syncBackend();
          const tempRetryAttempts = true;
          setRetryAttempts(tempRetryAttempts);
        }
        //console.log("Inside items",items);
        fetchItemsPaginated(currentPage, itemsPerPage);
        //fetchItems();
      }
    }, 1000);
    return () => clearInterval(fetchDataInterval);
  }, [items,pagItems,retryAttempts,currentPage,itemsPerPage,items,characters, isReachable]);

  /*useEffect(() => {
    const fetchData = async () => {
      await fetchCharactersPaginated(currentPage, itemsPerPage);
      setPagCharacters(items.slice(0, 5));
      setTimeout(fetchData, 2000); // Fetch data again after 2 seconds
    };

    fetchData(); // Initial fetch
  }, [currentPage, itemsPerPage]);*/


  /*useEffect(() => {
    if (currentPage !== 0 && itemsPerPage !== 0) {
        fetchCharactersPaginated(currentPage, itemsPerPage);
    }
  }, [currentPage,itemsPerPage]);*/

  /*useEffect(() => {
    if (currentPage !== 0 && itemsPerPage !== 0) {
      fetchCharactersPaginated(currentPage, itemsPerPage);
    } else {
      if (pagCharacters.length === 0 && items.length > 0) {
        setPagCharacters(items.slice(0, itemsPerPage));
      }
    }
  }, [currentPage, itemsPerPage, items, fetchCharactersPaginated, pagCharacters]);*/


  const handleDeleteConfirmation = (id) => {
    setSelectedItem(id);
    setModalVisible(true);
  };

  const handleDelete = () => {
    if (selectedItem) {
      deleteItem(selectedItem);
      setItemsPerPage(5);
      setCurrentPage(1);
      setSelectedItem(null);
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

  return (
    <View style={{backgroundColor:'#a59b90'}}>
      <View styles={styles.container}>
        <PaperButton mode="contained" onPress={() => navigation.navigate('Add Item')} style={[styles.roundedButton, { backgroundColor: '#363135' }]}>
          Add new record
        </PaperButton>
      </View>
      <DataTable testID="data-table">
        <DataTable.Header>
          <DataTable.Title style={{ flex: 2 }}>Character</DataTable.Title>
          <DataTable.Title style={{ flex: 2 }}>Item Name</DataTable.Title>
          <DataTable.Title style={{ flex: 2 }}>Item Count</DataTable.Title>
          <DataTable.Title style={ { flex: 2 }}>Rune Count</DataTable.Title>
          <DataTable.Title >Details</DataTable.Title>
          <DataTable.Title >Edit</DataTable.Title>
          <DataTable.Title >Delete</DataTable.Title>
        </DataTable.Header>
        {Array.isArray(pagItems) && pagItems.length > 0 ? (
          Array.from(pagItems).map((item) => {
            const character = characters.find(char => char.cid === item.cid);
            const characterName = character ? character.name : 'Unknown';
            return (
              <DataTable.Row key={item.lid} onPress={() => navigation.navigate("View Item Details", { id: item.lid, characterName: characterName })}>
                <DataTable.Cell>{characterName}</DataTable.Cell>
                <DataTable.Cell style={{ flex: 2 }}>{item.itemName}</DataTable.Cell>
                <DataTable.Cell style={{ flex: 2 }}>{item.itemDrop}</DataTable.Cell>
                <DataTable.Cell style={{ flex: 2 }}>{item.runeCount}</DataTable.Cell>
                <DataTable.Cell>
                  <PaperButton mode="contained" onPress={() => navigation.navigate("View Item Details", { id: item.lid, characterName: characterName })} style={styles.button}>
                    Details
                  </PaperButton>
                </DataTable.Cell>
                <DataTable.Cell>
                  <PaperButton mode="contained" onPress={() => navigation.navigate("Edit Item", { id: item.lid })} style={styles.button}>
                    Edit
                  </PaperButton>
                </DataTable.Cell>
                <DataTable.Cell>
                  <PaperButton testID="delete-button" mode="contained" onPress={() => handleDeleteConfirmation(item.lid)} style={[styles.button, { backgroundColor: "#D2042D" }]}>
                    Delete
                  </PaperButton>
                </DataTable.Cell>
              </DataTable.Row>
            );
          })
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
                if(retryAttempts)
                  fetchItemsPaginated(1, newValue);
                else
                  serverDownPagItems(1, newValue,items);
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
                if(retryAttempts)
                  fetchItemsPaginated(value, itemsPerPage);
                else
                  serverDownPagItems(value,itemsPerPage,items);
              }
            }}
          />
          <View style={{marginLeft:15, marginRight: 15, alignItems:'center'}}>
            <Text>
              {currentPage === 0 || itemsPerPage === 0
                ? `Page: 1 out of 1, ${nrOfItems} entries`
                : `Page: ${currentPage} out of ${totalPages}, ${nrOfItems} entries`}
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

export default ItemsScreen;
