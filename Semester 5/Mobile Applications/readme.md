# Mythos Expert

## Description:
The app will display all entries of stories, mythologies, folk tales present in the database based on a filter of the categories (stories, mythologies, folk tales), and a field in which to search for entries. The entries will be displayed as a list, the user being able to scroll upwards and downwards in the list, and can click on any element in the list to see its details, and see a YouTube video presenting the topic of the entry. The app will have a button made available whenever the user clicks on an element in the list to see other entries which have at least 50% text similarity and at least 3 identical keywords. The similar entries will be displayed in a new list on a new screen. The user will be able to modify all entries, to create a new entity, see the details of an entity, edit the fields of an entity or delete any entity.

## Domain details:
Kotlin types: 
ID | Title | Origin | Year | Keywords | Description | YoutubeLink |
--- | --- | --- | --- |--- |--- |--- |
Int | String | String | Int | String | String | String |

Database types (we will use Firebase - or SQL, will adapt to development needs):
ID | Title | Origin | Year | Keywords | Description | YoutubeLink |
--- | --- | --- | --- |--- |--- |--- |
int | Varchar(255) | Varchar(255) | int | Varchar(255) | text | Varchar(255) |

## CRUD operations:
**Create** – The user will have a button Titled “Add story” available in the main screen. The user will be redirected to a new screen where they can input in designated text boxes the Title, origin, year, a list of keywords, a description and a link corresponding to the story. If a story with the same Title already exists, the operation will be cancelled and an error message will be displayed. The user is then redirected to the main page.</br>
**Read** – The user will be able to click on any element in the list available in the main screen or in the list available if the clicked element from the list has similar stories, and all of the element’s fields will de displayed in another screen. The user is then redirected to the main page.</br>
**Update** - The user will be able to click on the ‘Update’ button found on the same line with any element in the list available in the main screen or in the list available if the clicked element from the list has similar stories. The user will be redirected to a new screen where they can input in designated text boxes the new Title, origin, year, a list of keywords, a description and a link corresponding to the story. If the user doesn’t write anything, the action is cancelled. If the user inputs the same details the entity already has, the operation is cancelled. The user is then redirected to the main page.</br>
**Delete** – The user will be able to click on the ‘Delete’ button found on the same line with any element in the list available in the main screen or in the list available if the clicked element from the list has similar stories. The user will see a pop up which will ask if the user wants to delete the item, which will have two buttons, ‘Cancel’ or ‘Delete’. If the user clicks on ‘Cancel’, the delete operation will be cancelled and the user will be redirected to the main page.

## Persistence details:
All crud operations are persisted on the local and server databases.

## Offline application behaviour:
**Read** – The application will store in a local data storage all of the entries to be available for ‘read’, ‘update’ and ‘delete’ operations.</br>
**Update** – The user will be able to edit an entry and the modified entry will be stored in the local data storage and will be available for ‘read’, ‘update’ and ‘delete’ operations. The update conflicts will be handled by keeping the most recent edit as the final one.</br>
**Delete** – all ids of deleted elements will be stored locally. Once the application is back online, all entries of the deleted items will be deleted from the server database.</br>
**Create** - The application will store in a local data storage all entries which have been added through the ‘create’ operation. The entry will be available for ‘read’, ‘update’, ‘delete’ operations. Once the application is back online, it will synchronize with the server database and push the new entities to the server database. If there are entries with the same ‘Title’ text, the user will be notified that the operation has failed.</br.>

## Main Screen and Add Screen Mockups
![HomeScreen_AddScreen](https://github.com/user-attachments/assets/58bd7ef8-ad05-4377-8aae-69cf103494bb)
