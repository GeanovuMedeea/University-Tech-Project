#include <string.h>
#include <stdlib.h>
#include "Repository.h"
#include "DynamicArray.h"
#include "Service.h"

void initialize_Service(Dynamic_array_of_medicines* repository)
{
	Dynamic_array_of_operations* Undo_array = create_Dynamic_array_of_operations(2);
	Dynamic_array_of_operations* Redo_array = create_Dynamic_array_of_operations(2);

	add_new_Medicine_Service(repository, Undo_array, Redo_array, 0, "Paracetamol", 500, 5, 30);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 1, "Algocalmin", 500, 10, 20);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 2, "Ketorol", 10, 2, 15);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 3, "Augumentin", 1000, 1, 40);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 4, "Euthyrox", 250, 15, 100);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 5, "Augumentin", 50, 10, 50);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 6, "Ibuprofen", 100, 18, 25);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 7, "Motillium", 25, 15, 25);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 8, "Decasept", 10, 10, 1);
	add_new_Medicine_Service(repository, Undo_array, Redo_array, 9, "Doreta", 100, 100, 23);

//	destroy_Dynamic_array_of_operations(Undo_array);
//	destroy_Dynamic_array_of_operations(Redo_array);
}

int next_id = 9;

int get_next_id_for_Medicine() {
	return ++next_id;
};

void sort_Medicines_ascending_by_name(Dynamic_array_of_medicines* Medicines)
{
	int Medicines_Array_Size = get_Dynamic_array_of_operations_size(Medicines);
	for (int i = 0; i < Medicines_Array_Size - 1; i++)
		for (int j = i + 1; j < Medicines_Array_Size; j++)
		{
			if (strcmp(get_Medicine_name(&Medicines->elements[i]), get_Medicine_name(&Medicines->elements[j])) > 0)
			{

				Medicine temporary_element_to_transfer_data = Medicines->elements[i];
				Medicines->elements[i] = Medicines->elements[j];
				Medicines->elements[j] = temporary_element_to_transfer_data;
			}
		}
}

void sort_Medicines_descending_name(Dynamic_array_of_medicines* Medicines)
{
	int Medicines_Array_Size = get_Dynamic_array_of_operations_size(Medicines);
	for (int i = 0; i < Medicines_Array_Size - 1; i++)
		for (int j = i + 1; j < Medicines_Array_Size; j++)
			if (strcmp(Medicines->elements[i].name_of_Medicine, Medicines->elements[j].name_of_Medicine) < 0)
			{
				Medicine temporary_element_to_transfer_data = Medicines->elements[i];
				Medicines->elements[i] = Medicines->elements[j];
				Medicines->elements[j] = temporary_element_to_transfer_data;
			}
}

Dynamic_array_of_medicines* get_all_stocks_of_Medicines_Service(Dynamic_array_of_medicines* repository)
{
	Dynamic_array_of_medicines* all_stocks_of_Medicines = create_Dynamic_array_of_medicines(2);
	int size_of_Medicines_array = get_Dynamic_Array_of_Medicines_size(repository);

	for (int i = 0; i < size_of_Medicines_array; i++)
	{
		add_new_Medicine_to_Dynamic_array_of_Medicines(all_stocks_of_Medicines, repository->elements[i]);
	}

	sort_Medicines_ascending_by_name(all_stocks_of_Medicines);

	return all_stocks_of_Medicines;
}

Dynamic_array_of_medicines* get_Medicines_by_name_Service(Dynamic_array_of_medicines* repository, char* name_of_Medicine_to_match)
{

	Dynamic_array_of_medicines* all_stocks_of_Medicines = create_Dynamic_array_of_medicines(2);
	int all_stocks_of_Medicines_size = get_Dynamic_Array_of_Medicines_size(repository);

	for (int i = 0; i < all_stocks_of_Medicines_size; i++)
		add_new_Medicine_to_Dynamic_array_of_Medicines(all_stocks_of_Medicines, repository->elements[i]);

	if (strcmp(name_of_Medicine_to_match, " ") == 0 || strcmp(name_of_Medicine_to_match, "") == 0 || strcmp(name_of_Medicine_to_match, "\n") == 0) //is empty space
	{
		sort_Medicines_ascending_by_name(all_stocks_of_Medicines);
		return all_stocks_of_Medicines;
	}

	Dynamic_array_of_medicines* Medicines_with_matched_names = create_Dynamic_array_of_medicines(2);
	all_stocks_of_Medicines_size = get_Dynamic_Array_of_Medicines_size(all_stocks_of_Medicines);

	for (int i = 0; i < all_stocks_of_Medicines_size; i++)
		if (strstr(repository->elements[i].name_of_Medicine, name_of_Medicine_to_match))
			add_new_Medicine_to_Dynamic_array_of_Medicines(Medicines_with_matched_names, repository->elements[i]);

	destroy_Dynamic_array_of_Medicines(all_stocks_of_Medicines);
	sort_Medicines_ascending_by_name(Medicines_with_matched_names);

	return Medicines_with_matched_names;
}

Dynamic_array_of_medicines* get_Medicines_with_quantity_less_than_given_number_Service(Dynamic_array_of_medicines* repository, int quantity_to_filter_Medicines)
{
	Dynamic_array_of_medicines* all_stocks_of_Medicines = create_Dynamic_array_of_medicines(2);
	int all_stocks_of_Medicines_size = get_Dynamic_Array_of_Medicines_size(repository);

	for (int i = 0; i < all_stocks_of_Medicines_size; i++)
		add_new_Medicine_to_Dynamic_array_of_Medicines(all_stocks_of_Medicines, repository->elements[i]);

	Dynamic_array_of_medicines* Medicines_with_quantity_smaller_than_the_given_quantity = create_Dynamic_array_of_medicines(2);
	int filtered_Medicines_list_size = get_Dynamic_Array_of_Medicines_size(all_stocks_of_Medicines);

	for (int i = 0; i < filtered_Medicines_list_size; i++)
		if (get_Medicine_quantity(all_stocks_of_Medicines->elements[i]) < quantity_to_filter_Medicines)
			add_new_Medicine_to_Dynamic_array_of_Medicines(Medicines_with_quantity_smaller_than_the_given_quantity, all_stocks_of_Medicines->elements[i]);

	destroy_Dynamic_array_of_Medicines(all_stocks_of_Medicines);
	sort_Medicines_ascending_by_name(Medicines_with_quantity_smaller_than_the_given_quantity);

	return Medicines_with_quantity_smaller_than_the_given_quantity;
}

void add_new_Medicine_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, int id, char* name_of_Medicine, int concentration, int quantity, int price)
{
	Redo_array->size = 0;
	Dynamic_array_of_medicines* all_stocks_of_Medicines = create_Dynamic_array_of_medicines(2);
	int size_of_Medicines_array = get_Dynamic_Array_of_Medicines_size(repository);

	for (int i = 0; i < size_of_Medicines_array; i++)
	{
		add_new_Medicine_to_Dynamic_array_of_Medicines(all_stocks_of_Medicines, repository->elements[i]);
	}

	//updates the quantity of the medicine record if it is already in the list, and skips adding it again
	int check_Medicine_already_exists = 0;

	for (int i = 0; i < size_of_Medicines_array; i++)
	{
		if (strcmp(get_Medicine_name(&all_stocks_of_Medicines->elements[i]), name_of_Medicine) == 0 && get_Medicine_concentration(all_stocks_of_Medicines->elements[i]) == concentration)
		{
			repository->elements[i].quantity += quantity;
			check_Medicine_already_exists = 1;
		}
	}

	if (check_Medicine_already_exists == 0)
	{
		Medicine Medicine_to_be_added = add_new_Medicine_to_Repository(repository, id, name_of_Medicine, concentration, quantity, price);
		Operation Undo_operation_record;
		strcpy(Undo_operation_record.operation, "add");
		Undo_operation_record.medicine = Medicine_to_be_added;

		add_Medicine_to_Dynamic_array_of_operations(Undo_array, Undo_operation_record);
	}

	destroy_Dynamic_array_of_Medicines(all_stocks_of_Medicines);
}

void delete_Medicine_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name_of_Medicine, int concentration)
{
	Redo_array->size = 0;
	Medicine Medicine_to_delete = delete_Medicine_by_given_name_and_concentration_from_Repository(repository, name_of_Medicine, concentration);
	Operation Undo_operation_record;

	strcpy(Undo_operation_record.operation, "delete");
	Undo_operation_record.medicine = Medicine_to_delete;

	add_Medicine_to_Dynamic_array_of_operations(Undo_array, Undo_operation_record);
}

void update_Medicine_name_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name_of_Medicine, int concentration, char* new_name_of_Medicine)
{
	Redo_array->size = 0;
	Medicine Medicine_to_Update = update_Medicine_name_by_given_name_and_concentration_in_Repository(repository, name_of_Medicine, concentration, new_name_of_Medicine);
	Operation Undo_operation_record;

	strcpy(Undo_operation_record.operation, "update");
	Undo_operation_record.medicine = Medicine_to_Update;

	add_Medicine_to_Dynamic_array_of_operations(Undo_array, Undo_operation_record);
}

void update_Medicine_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name_of_Medicine, int concentration, int newConcentration)
{
	Redo_array->size = 0;
	Medicine Medicine_to_Update = update_Medicine_concentration_by_given_name_and_concentration_in_Repository(repository, name_of_Medicine, concentration, newConcentration);
	Operation Undo_operation_record;

	strcpy(Undo_operation_record.operation, "update");
	Undo_operation_record.medicine = Medicine_to_Update;

	add_Medicine_to_Dynamic_array_of_operations(Undo_array, Undo_operation_record);
}

void update_Medicine_quantity_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name_of_Medicine, int concentration, int new_quantity)
{
	Redo_array->size = 0;
	Medicine Medicine_to_Update = update_Medicine_quantity_by_given_name_and_concentration_in_Repository(repository, name_of_Medicine, concentration, new_quantity);

	Operation Undo_operation_record;
	strcpy(Undo_operation_record.operation, "update");
	Undo_operation_record.medicine = Medicine_to_Update;

	add_Medicine_to_Dynamic_array_of_operations(Undo_array, Undo_operation_record);
}

void update_Medicine_price_by_given_name_and_concentration_Service(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array, char* name_of_Medicine, int concentration, int new_price)
{
	Redo_array->size = 0;
	Medicine Medicine_to_Update = update_Medicine_price_by_given_name_and_concentration_in_Repository(repository, name_of_Medicine, concentration, new_price);
	Operation Undo_operation_record;

	strcpy(Undo_operation_record.operation, "update");
	Undo_operation_record.medicine = Medicine_to_Update;

	add_Medicine_to_Dynamic_array_of_operations(Undo_array, Undo_operation_record);
}

int undoService(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	// 0 return -> success
	// 1 return -> nothing to undo (Undo_array has size 0)
	if (get_Dynamic_array_of_operations_size(Undo_array) == 0)
		return 1;

	Operation Undo_operation_record = Undo_array->elements[get_Dynamic_array_of_operations_size(Undo_array) - 1]; //take the last recorded operation

	if (strcmp(Undo_operation_record.operation, "add") == 0)
	{
		add_Medicine_to_Dynamic_array_of_operations(Redo_array, Undo_operation_record);
		Dynamic_array_of_operations* temporary_operation_array = create_Dynamic_array_of_operations(2); //only to run the function since it requires undo/redo array as parameters

		delete_Medicine_by_given_name_and_concentration_Service(repository, temporary_operation_array, temporary_operation_array, Undo_operation_record.medicine.name_of_Medicine, Undo_operation_record.medicine.concentration);
		Undo_array->size--;

		destroy_Dynamic_array_of_operations(temporary_operation_array);

		return 0;
	}

	if (strcmp(Undo_operation_record.operation, "delete") == 0)
	{
		add_Medicine_to_Dynamic_array_of_operations(Redo_array, Undo_operation_record);
		Dynamic_array_of_operations* temporary_operation_array = create_Dynamic_array_of_operations(2);//only to run the function since it requires undo/redo array as parameters

		add_new_Medicine_Service(repository, temporary_operation_array, temporary_operation_array, Undo_operation_record.medicine.id, Undo_operation_record.medicine.name_of_Medicine, Undo_operation_record.medicine.concentration, Undo_operation_record.medicine.quantity, Undo_operation_record.medicine.price);
		Undo_array->size--;

		destroy_Dynamic_array_of_operations(temporary_operation_array);

		return 0;
	}

	if (strcmp(Undo_operation_record.operation, "update") == 0)
	{
		Dynamic_array_of_operations* temporary_operation_array = create_Dynamic_array_of_operations(2);//only to run the function since it requires undo/redo array as parameters
		Medicine medicineToDelete;
		int stocks_of_all_Medicines_size = get_Dynamic_Array_of_Medicines_size(repository);

		for (int i = 0; i < stocks_of_all_Medicines_size; i++)
		{
			if (get_Medicine_id(repository->elements[i]) == get_Medicine_id(Undo_operation_record.medicine))
			{
				Medicine redoMedicine;

				redoMedicine.id = repository->elements[i].id;
				strcpy(redoMedicine.name_of_Medicine, repository->elements[i].name_of_Medicine);
				redoMedicine.concentration = repository->elements[i].concentration;
				redoMedicine.quantity = repository->elements[i].quantity;
				redoMedicine.price = repository->elements[i].price;

				Operation newRedoOperation;
				strcpy(newRedoOperation.operation, "update");
				newRedoOperation.medicine = redoMedicine;

				add_Medicine_to_Dynamic_array_of_operations(Redo_array, newRedoOperation);

				medicineToDelete.id = repository->elements[i].id;
				strcpy(medicineToDelete.name_of_Medicine, repository->elements[i].name_of_Medicine);
				medicineToDelete.concentration = repository->elements[i].concentration;

				break;
			}
		}

		delete_Medicine_by_given_name_and_concentration_Service(repository, temporary_operation_array, temporary_operation_array, medicineToDelete.name_of_Medicine, medicineToDelete.concentration);

		add_new_Medicine_Service(repository, temporary_operation_array, temporary_operation_array, Undo_operation_record.medicine.id, Undo_operation_record.medicine.name_of_Medicine, Undo_operation_record.medicine.concentration, Undo_operation_record.medicine.quantity, Undo_operation_record.medicine.price);
		Undo_array->size--;

		destroy_Dynamic_array_of_operations(temporary_operation_array);

		return 0;
	}
}

int redoService(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	// 0 return -> success
	// 1 return -> nothing to undo (Undo_array has size 0)
	if (get_Dynamic_array_of_operations_size(Redo_array) == 0)
		return 1;

	Operation redoOperation = Redo_array->elements[get_Dynamic_array_of_operations_size(Redo_array) - 1];

	if (strcmp(redoOperation.operation, "add") == 0)
	{
		Dynamic_array_of_operations* temporary_operation_array = create_Dynamic_array_of_operations(2);

		add_new_Medicine_Service(repository, Undo_array, temporary_operation_array, redoOperation.medicine.id, redoOperation.medicine.name_of_Medicine,
			redoOperation.medicine.concentration, redoOperation.medicine.quantity, redoOperation.medicine.price);
		Redo_array->size--;

		destroy_Dynamic_array_of_operations(temporary_operation_array);

		return 0;
	}

	if (strcmp(redoOperation.operation, "delete") == 0)
	{
		Dynamic_array_of_operations* temporary_operation_array = create_Dynamic_array_of_operations(2);

		delete_Medicine_by_given_name_and_concentration_Service(repository, Undo_array, temporary_operation_array, redoOperation.medicine.name_of_Medicine, redoOperation.medicine.concentration);
		Redo_array->size--;

		destroy_Dynamic_array_of_operations(temporary_operation_array);

		return 0;

	}

	if (strcmp(redoOperation.operation, "update") == 0)
	{
		Dynamic_array_of_operations* temporary_operation_array = create_Dynamic_array_of_operations(2);
		Medicine medicineToDelete;
		int stocks_of_all_Medicines_size = get_Dynamic_Array_of_Medicines_size(repository);

		for (int i = 0; i < stocks_of_all_Medicines_size; i++)
		{
			if (get_Medicine_id(repository->elements[i]) == get_Medicine_id(redoOperation.medicine))
			{
				Medicine undoMedicine;

				undoMedicine.id = repository->elements[i].id;
				strcpy(undoMedicine.name_of_Medicine, repository->elements[i].name_of_Medicine);
				undoMedicine.concentration = repository->elements[i].concentration;
				undoMedicine.quantity = repository->elements[i].quantity;
				undoMedicine.price = repository->elements[i].price;

				Operation newUndoOperation;
				strcpy(newUndoOperation.operation, "update");
				newUndoOperation.medicine = undoMedicine;

				add_Medicine_to_Dynamic_array_of_operations(Undo_array, newUndoOperation);

				medicineToDelete.id = repository->elements[i].id;
				strcpy(medicineToDelete.name_of_Medicine, repository->elements[i].name_of_Medicine);
				medicineToDelete.concentration = repository->elements[i].concentration;

				break;
			}
		}

		delete_Medicine_by_given_name_and_concentration_Service(repository, temporary_operation_array, temporary_operation_array, medicineToDelete.name_of_Medicine, medicineToDelete.concentration);

		add_new_Medicine_Service(repository, temporary_operation_array, temporary_operation_array, redoOperation.medicine.id, redoOperation.medicine.name_of_Medicine,
			redoOperation.medicine.concentration, redoOperation.medicine.quantity, redoOperation.medicine.price);

		Redo_array->size--;

		destroy_Dynamic_array_of_operations(temporary_operation_array);
	}
}