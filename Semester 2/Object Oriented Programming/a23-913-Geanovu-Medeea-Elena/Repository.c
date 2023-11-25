#include <stdlib.h>
#include <string.h>
#include "Repository.h"
#include "DynamicArray.h"
#include "Domain.h"

Dynamic_array_of_medicines* create_Repository()
{
	return create_Dynamic_array_of_medicines(2);
}

int get_index_of_Medicine_from_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration)
{
	int size_of_Dynamic_array_of_medicines = get_Dynamic_Array_of_Medicines_size(repository);
	for (int i = 0; i < size_of_Dynamic_array_of_medicines; i++)
		if (strcmp(get_Medicine_name(&repository->elements[i]), name_of_Medicine) == 0 && get_Medicine_concentration(repository->elements[i]) == concentration)
			return i;
	return -1; //medicine doesn't exist in stocks
}

//TElem* get_all_Medicines_from_Repository(Dynamic_array_of_medicines* repository)
//{
//	return repository->elements;
//}

Medicine add_new_Medicine_to_Repository(Dynamic_array_of_medicines* repository, int id, char* name_of_Medicine, int concentration, int quantity, int price)
{
	Medicine new_Medicine;

	strcpy(new_Medicine.name_of_Medicine, name_of_Medicine);
	new_Medicine.id = id;
	new_Medicine.concentration = concentration;
	new_Medicine.quantity = quantity;
	new_Medicine.price = price;

	add_new_Medicine_to_Dynamic_array_of_Medicines(repository, new_Medicine);

	return new_Medicine;
}

Medicine delete_Medicine_by_given_name_and_concentration_from_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration)
{
	int index_of_Medicine_to_delete = get_index_of_Medicine_from_Repository(repository, name_of_Medicine, concentration);
	Medicine Medicine_to_delete = repository->elements[index_of_Medicine_to_delete];

	if (index_of_Medicine_to_delete >= 0)
	{
		int Dynamic_array_of_Medicines_size = get_Dynamic_Array_of_Medicines_size(repository);
		for (int i = index_of_Medicine_to_delete; i < Dynamic_array_of_Medicines_size - 1; i++)
			repository->elements[i] = repository->elements[i + 1];
		repository->size--;
	}
	return Medicine_to_delete;
}

Medicine update_Medicine_name_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, char* new_Medicine_name)
{
	int index_of_Medicine_to_search = get_index_of_Medicine_from_Repository(repository, name_of_Medicine, concentration);
	Medicine Medicine_to_update = repository->elements[index_of_Medicine_to_search];

	if (index_of_Medicine_to_search >= 0)
	{
		strcpy(repository->elements[index_of_Medicine_to_search].name_of_Medicine, new_Medicine_name);
	}

	return Medicine_to_update;
}

Medicine update_Medicine_concentration_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, int new_concentration)
{
	int index_of_Medicine_to_search = get_index_of_Medicine_from_Repository(repository, name_of_Medicine, concentration);
	Medicine Medicine_to_update = repository->elements[index_of_Medicine_to_search];

	if (index_of_Medicine_to_search >= 0)
		repository->elements[index_of_Medicine_to_search].concentration = new_concentration;

	return Medicine_to_update;
}

Medicine update_Medicine_quantity_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, int new_quantity)
{
	int index_of_Medicine_to_search = get_index_of_Medicine_from_Repository(repository, name_of_Medicine, concentration);
	Medicine Medicine_to_update = repository->elements[index_of_Medicine_to_search];

	if (index_of_Medicine_to_search >= 0)
		repository->elements[index_of_Medicine_to_search].quantity = new_quantity;

	return Medicine_to_update;
}

Medicine update_Medicine_price_by_given_name_and_concentration_in_Repository(Dynamic_array_of_medicines* repository, char* name_of_Medicine, int concentration, int new_price)
{
	int index_of_Medicine_to_search = get_index_of_Medicine_from_Repository(repository, name_of_Medicine, concentration);
	Medicine Medicine_to_update = repository->elements[index_of_Medicine_to_search];

	if (index_of_Medicine_to_search >= 0)
		repository->elements[index_of_Medicine_to_search].price = new_price;

	return Medicine_to_update;
}