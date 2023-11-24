#include "Repository.h"
#include "DynamicArray.h"

void initialize_Service(Dynamic_array_of_medicines* repository);

int get_next_id_for_Medicine();

void sort_Medicines_descending_name(Dynamic_array_of_medicines* Medicines);

//Dynamic_array_of_medicines* sort_Medicines_by_concentration_Service(Dynamic_array_of_medicines* repository);

Dynamic_array_of_medicines* get_all_stocks_of_Medicines_Service(Dynamic_array_of_medicines* repository);

Dynamic_array_of_medicines* get_Medicines_by_name_Service(Dynamic_array_of_medicines* repository, char* givenName);

Dynamic_array_of_medicines* get_Medicines_with_quantity_less_than_given_number_Service(Dynamic_array_of_medicines* repository, int quantity);

void add_new_Medicine_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, int id, char* name, int concentration, int quantity, int price);

void delete_Medicine_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name, int concentration);

void update_Medicine_name_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name, int concentration, char* newName);

void update_Medicine_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name, int concentration, int newConcentration);

void update_Medicine_quantity_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name, int concentration, int newQuantity);

void update_Medicine_price_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name, int concentration, int newPrice);

int undoService(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

int redoService(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);
