#include "Domain.h"
#include "DynamicArray.h"

Dynamic_array_of_medicines* create_Repository();

int get_index_of_Medicine_from_Repository(Dynamic_array_of_medicines* repository, char* name_od_Medicine, int concentration);

//TElem* get_all_Medicines_from_Repository(Dynamic_array_of_medicines* repository);

Medicine add_new_Medicine_to_Repository(Dynamic_array_of_medicines* repository, int id, char* name_of_Medicine, int concentration, int quantity, int price);

Medicine delete_Medicine_by_given_name_and_concentration_from_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration);

Medicine update_Medicine_name_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, char* new_name_of_medicine);

Medicine update_Medicine_concentration_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, int new_concentration);

Medicine update_Medicine_quantity_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, int new_quantity);

Medicine update_Medicine_price_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, int new_price);
