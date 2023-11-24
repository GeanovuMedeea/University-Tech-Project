#pragma once
#include "DynamicArray.h"

void printMenu();

void generate_iterable_list_to_print_Medicines(Dynamic_array_of_medicines* repository);

void add_new_Medicine(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void delete_Medicine_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void update_Medicine_name_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void update_Medicine_concentration_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void update_Medicine_quantity_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void update_Medicine_price_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void print_Medicines_filtered_by_name(Dynamic_array_of_medicines* repository);

void undo(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void redo(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);

void start_UI(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array);