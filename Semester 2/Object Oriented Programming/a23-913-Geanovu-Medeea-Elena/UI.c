#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "UI.h"
#include "Service.h"

void printMenu()
{
	printf("\n");
	printf("1. List all the medicines\n");
	printf("2. Add a new medicine\n");
	printf("3. Delete a medicine\n");
	printf("4. Update the name of a medicine\n");
	printf("5. Update the concentration of a medicine\n");
	printf("6. Update the quantity of a medicine\n");
	printf("7. Update the price of a medicine\n");
	printf("8. Search for medicines\n");
	printf("9. Search for medicine in short supply\n");
	printf("10. Undo \n");
	printf("11. Redo \n");
	printf("0. Exit.\n");
}

void print_all_Medicines(Dynamic_array_of_medicines* medicines)
{
	for (int i = 0; i < medicines->size; i++)
	{
		printf("%d. Name: %s ", i + 1, get_Medicine_name(&medicines->elements[i]));
		printf("Concentration: %d ", get_Medicine_concentration(medicines->elements[i]));
		printf("Quantity: %d ", get_Medicine_quantity(medicines->elements[i]));
		printf("Price: %d\n", get_Medicine_price(medicines->elements[i]));
	}
	printf("%s", "\n");
}

void generate_iterable_list_to_print_Medicines(Dynamic_array_of_medicines* repository)
{
	Dynamic_array_of_medicines* all_stocks_of_Medicines = get_all_stocks_of_Medicines_Service(repository);

	printf("Stocks of all medicines: \n");
	print_all_Medicines(all_stocks_of_Medicines);

	destroy_Dynamic_array_of_Medicines(all_stocks_of_Medicines);
}

void add_new_Medicine(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	char new_name_of_Medicine[30];
	int new_concentration, new_quantity, new_price;

	printf("New name: ");
	scanf("%s", new_name_of_Medicine);

	printf("New concentration: ");
	scanf_s("%d", &new_concentration);

	printf("New quantity: ");
	scanf_s("%d", &new_quantity);

	printf("New price: ");
	scanf_s("%d", &new_price);

	add_new_Medicine_Service(repository, Undo_array, Redo_array, get_next_id_for_Medicine(), new_name_of_Medicine, new_concentration, new_quantity, new_price);
}

void delete_Medicine_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	char name_of_Medicine_to_Delete[30];
	int concentration_to_Delete;

	printf("Name of medicine to delete: ");
	scanf("%s", name_of_Medicine_to_Delete);

	printf("Concentration of medicine to delete: ");
	scanf_s("%d", &concentration_to_Delete);

	delete_Medicine_by_given_name_and_concentration_Service(repository, Undo_array, Redo_array, name_of_Medicine_to_Delete, concentration_to_Delete);
}

void update_Medicine_name_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	char name_of_Medicine[30], new_name_of_Medicine[30];
	int concentration;

	printf("Name of the medicine you want to update : ");
	scanf("%s", name_of_Medicine);

	printf("Concentration of the medicine you want to update: ");
	scanf_s("%d", &concentration);

	printf("New name of the medicine: ");
	scanf("%s", new_name_of_Medicine);

	update_Medicine_name_by_given_name_and_concentration_Service(repository, Undo_array, Redo_array, name_of_Medicine, concentration, new_name_of_Medicine);
}

void update_Medicine_concentration_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	char name_of_Medicine[30];
	int concentration, new_concentration;

	printf("Name of the medicine you want to update: ");
	scanf("%s", name_of_Medicine);

	printf("Concentration of the medicine you want to update: ");
	scanf_s("%d", &concentration);

	printf("New concentration of the medicine: ");
	scanf_s("%d", &new_concentration);

	update_Medicine_concentration_Service(repository, Undo_array, Redo_array, name_of_Medicine, concentration, new_concentration);
}

void update_Medicine_quantity_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	char name_of_Medicine[30];
	int concentration, new_quantity;

	printf("Name of the medicine you want to update: \n>");
	scanf("%s", name_of_Medicine);

	printf("Concentration of the medicine you want to update: \n>");
	scanf_s("%d", &concentration);

	printf("New quantity of the medicine: \n>");
	scanf_s("%d", &new_quantity);

	update_Medicine_quantity_by_given_name_and_concentration_Service(repository, Undo_array, Redo_array, name_of_Medicine, concentration, new_quantity);
}

void update_Medicine_price_by_given_name_and_concentration(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	char name_of_Medicine[30];
	int concentration, new_price;

	printf("Name of the medicine you want to update: \n>");
	scanf("%s", name_of_Medicine);

	printf("Concentration of the medicine you want to update: \n>");
	scanf_s("%d", &concentration);

	printf("New price of the medicine: \n>");
	scanf_s("%d", &new_price);

	update_Medicine_price_by_given_name_and_concentration_Service(repository, Undo_array, Redo_array, name_of_Medicine, concentration, new_price);
}

void print_Medicines_filtered_by_name(Dynamic_array_of_medicines* repository)
{
	char Medicine_name_to_search[30] = "";
	printf("Text to search: ");
	fgets(Medicine_name_to_search, sizeof(Medicine_name_to_search), stdin);
	fgets(Medicine_name_to_search, sizeof(Medicine_name_to_search), stdin);

	Medicine_name_to_search[strlen(Medicine_name_to_search) - 1] = '\0';
	Dynamic_array_of_medicines* Medicines_according_to_filter_conditions = get_Medicines_by_name_Service(repository, Medicine_name_to_search);

	print_all_Medicines(Medicines_according_to_filter_conditions);

	destroy_Dynamic_array_of_Medicines(Medicines_according_to_filter_conditions);
}

void get_Medicines_with_quantity_less_than_given_number(Dynamic_array_of_medicines* repository)
{
	int quantity, sort_ascending_or_descending;

	printf("Quantity to find medicine stocks smaller than: \n>");
	scanf_s("%d", &quantity);

	Dynamic_array_of_medicines* filtered_Medicines = get_Medicines_with_quantity_less_than_given_number_Service(repository, quantity);

	print_all_Medicines(filtered_Medicines);

	destroy_Dynamic_array_of_Medicines(filtered_Medicines);
}

void undo(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	if (undoService(repository, Undo_array, Redo_array) == 1)
	{
		printf("No operations recorded.\n");
		return;
	}
	printf("Success.\n");
}

void redo(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{

	if (redoService(repository, Undo_array, Redo_array) == 1)
	{
		printf("No operations recorded.\n");
		return;
	}
	printf("Success.\n");
}

void start_UI(Dynamic_array_of_medicines* repository, Dynamic_array_of_operations* Undo_array, Dynamic_array_of_operations* Redo_array)
{
	printf("Welcome to medicine stock management app! \n\n");

	int choice = 100;

	while (choice)
	{
		printMenu();
		printf("\n>> ");
		scanf_s("%d", &choice);

		if (choice == 1)
		{
			generate_iterable_list_to_print_Medicines(repository);
			getchar();
			continue;
		}
		if (choice == 2)
		{
			add_new_Medicine(repository, Undo_array, Redo_array);
			continue;
		}
		if (choice == 3)
		{
			delete_Medicine_by_given_name_and_concentration(repository, Undo_array, Redo_array);
			continue;
		}
		if (choice == 4)
		{
			update_Medicine_name_by_given_name_and_concentration(repository, Undo_array, Redo_array);
			continue;
		}
		if (choice == 5)
		{
			update_Medicine_concentration_by_given_name_and_concentration(repository, Undo_array, Redo_array);
			continue;
		}
		if (choice == 6)
		{
			update_Medicine_quantity_by_given_name_and_concentration(repository, Undo_array, Redo_array);
			continue;
		}
		if (choice == 7)
		{
			update_Medicine_price_by_given_name_and_concentration(repository, Undo_array, Redo_array);
			continue;
		}
		if (choice == 8)
		{
			print_Medicines_filtered_by_name(repository);
			getchar();
			continue;
		}
		if (choice == 9)
		{
			get_Medicines_with_quantity_less_than_given_number(repository);
			getchar();
			continue;
		}
		if (choice == 10)
		{
			undo(repository, Undo_array, Redo_array);
			getchar();
			continue;
		}
		if (choice == 11)
		{

			redo(repository, Undo_array, Redo_array);
			getchar();
			continue;
		}
		if (choice == 0)
		{
			break;
		}
		printf("%s", "\nInvalid command.\n");
	}
}