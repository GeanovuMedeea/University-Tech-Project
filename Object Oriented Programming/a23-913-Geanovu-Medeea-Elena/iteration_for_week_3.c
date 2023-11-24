///*John is the administrator of the “Smiles” Pharmacy. He needs a software application to help him manage his pharmacy's medicine stocks.
//Each Medicine has the following attributes: name, concentration, quantity and price. John wants the application to help him in the following ways:
//(a) Add, delete or update a medicine. A medicine is uniquely identified by its name and concentration. If a product that already exists is added,
//its quantity will be updated (the new quantity is added to the existing one).
//(b) See all available medicines containing a given string (if the string is empty, all the available medicines will be considered),
//sorted ascending by medicine name.
//(c) See only those medicines that are in short supply (quantity less than X items, where the value of X is user-provided).
//(d) Provide multiple undo and redo functionality. Each step will undo/redo the previous operation performed by the user.
//*/
//
//#include <stdio.h>
//#include <stdlib.h>
//#include <string.h>
//
//typedef struct {
//	char name[50];
//	float concentration;
//	int quantity, price;
//}medicine;
//
//void add_starting_entries_of_medicine(medicine medicine_list[])
//{
//	char list_of_medicine_names[10][50] = { "Paracetamol", "Algocalmin", "Vitamina C", "Augumentin", "Augumentin", "Euthyrox", "Euthyrox", "Ibuprofen", "Motillium", "Decasept" };
//	float list_of_medicine_concentrations[10] = { 500, 500, 10, 1000, 250.5, 50, 100, 25.5, 10, 100};
//	int list_of_medicine_quantities[10] = { 5, 10, 2, 1, 15, 10, 20, 15, 8, 2 };
//	int list_of_medicine_prices[10] = { 30, 20, 15, 40, 100, 50, 25, 18, 25, 1 };
//	int index;
//	for (index = 0; index < 10; index++)
//	{
//		strcpy(medicine_list[index].name, list_of_medicine_names[index]);
//		medicine_list[index].concentration = list_of_medicine_concentrations[index];
//		medicine_list[index].quantity = list_of_medicine_quantities[index];
//		medicine_list[index].price = list_of_medicine_prices[index];
//	}
//}
//
//
//int find_by_name_and_concentration(int number_of_medicines, char name_to_check[], float concentration_to_check, medicine medicine_list[])
//{
//	if (number_of_medicines > 0)
//	{
//		int index;
//		for (index = 0; index < number_of_medicines; index++)
//			if (strcmp(medicine_list[index].name, name_to_check) == 0 && medicine_list[index].concentration == concentration_to_check)
//			{
//				return index;
//			}
//	}
//	return -1;
//}
//
//
//void increment_number_of_medicine_value(int* number_to_increment)
//{
//	*number_to_increment += 1;
//}
//
//
//void decrement_number_of_medicine_value(int* number_to_decrement)
//{
//	*number_to_decrement -= 1;
//}
//
//
//void add_new_medicine(int *n, char new_name[], float new_concentration, int new_quantity, int new_price, medicine medicine_list[])
//{
//	char name_to_check[50];
//	float concentration_to_check;
//	strcpy(medicine_list[*n].name, new_name);
//	medicine_list[*n].concentration = new_concentration;
//	medicine_list[*n].quantity = new_quantity;
//	medicine_list[*n].price = new_price;
//	*n += 1;
//}
//
//
//void update_medicine_quantity_when_it_already_exists(int position_of_medicine, int new_quantity, medicine medicine_list[])
//{
//	medicine_list[position_of_medicine].quantity = medicine_list[position_of_medicine].quantity + new_quantity;
//}
//
//
//void update_medicine_quantity(int index_of_medicine_to_update, int new_quantity, medicine medicine_list[])
//{
//	medicine_list[index_of_medicine_to_update].quantity = new_quantity;
//}
//
//
//void update_medicine_price(int index_of_medicine_to_update, int new_price, medicine medicine_list[])
//{
//	medicine_list[index_of_medicine_to_update].price = new_price;
//}
//
//
//void delete_medicine_by_its_index(int index_to_delete, int *number_of_medicine, medicine medicine_list[])
//{
//	int index;
//	for (index = index_to_delete; index < *number_of_medicine - 1; index++)
//		medicine_list[index] = medicine_list[index + 1];
//	*number_of_medicine -= 1;
//}
//
//
//int search_if_word_exists_in_medicine_name(char word_to_search[], char medicine_name_to_compare[])
//{
//	if (strstr(medicine_name_to_compare, word_to_search))
//		return 1;
//	return 0;
//}
//
//
////the following 3 functions are for sorting the list in ascending order by the medicine names
//void swap(medicine* first_medicine, medicine* second_medicine)
//{
//	medicine temp = *first_medicine;
//	*first_medicine = *second_medicine;
//	*second_medicine = temp;
//}
//
//
//int partition(medicine medicine_list[], int low, int high)
//{
//	char pivot[50];
//	strcpy(pivot, medicine_list[high].name);
//	int swap_position_index = (low - 1);
//	int iterating_index;
//	for (iterating_index = low; iterating_index <= high - 1; iterating_index++) {
//		if (strcmp(medicine_list[iterating_index].name, pivot) < 0) {
//			swap_position_index++;
//			swap(&medicine_list[swap_position_index], &medicine_list[iterating_index]);
//		}
//	}
//	swap(&medicine_list[swap_position_index + 1], &medicine_list[high]);
//	return (swap_position_index + 1);
//}
//
//
//void sort_medicine_list_in_ascending_order_by_name(medicine medicine_list[], int low, int high)
//{
//	if (low < high) {
//		// pi = Partition index
//		int partition_index_of_medicine_list_to_sort = partition(medicine_list, low, high);
//		sort_medicine_list_in_ascending_order_by_name(medicine_list, low, partition_index_of_medicine_list_to_sort - 1);
//		sort_medicine_list_in_ascending_order_by_name(medicine_list, partition_index_of_medicine_list_to_sort + 1, high);
//	}
//}
//
//
//void list_all_medicines(int n, medicine medicine_list[])
//{
//	printf("\n");
//	if (n == 0)
//	{
//		printf("No elements in the list.\n ");
//		return;
//	}
//	for (int i = 0; i < n; i++)
//	{
//		printf("Name: %s", medicine_list[i].name);
//		printf(" Concentration: %.2f", medicine_list[i].concentration);
//		printf(" Quantity: %d", medicine_list[i].quantity);
//		printf(" Price %d\n", medicine_list[i].price);
//	}
//}
//
//
//void print_menu()
//{
//	printf("\n1. Add a medicine\n");
//	printf("2. Delete a medicine\n");
//	printf("3. Update a medicine (quantity or price)\n");
//	printf("4. Search\n");
//	printf("5. Show medicine with quantity less than a given number\n");
//	printf("6. Undo\n");
//	printf("7. Redo\n");
//	printf("0. Exit\n");
//}
//
//
//int main()
//{
//	medicine medicine_list[50];
//	int choice = -1, number_of_medicines = 10;
//
//	add_starting_entries_of_medicine(medicine_list);
//	printf("The initial stock of medicines is: \n");
//	list_all_medicines(number_of_medicines, medicine_list);
//
//	while (choice != 0)
//	{
//		print_menu();
//		printf(">> ");
//		scanf_s("%d", &choice);
////		gets(buffer_clear);
//		getchar();
//
//		if (choice == 1)
//		{
//			char name_to_check[50];
//			float concentration_to_check;
//			int new_quantity, new_price;
//
//			printf("New medicine name: ");
//			gets(name_to_check);
//
//			printf("New medicine concentration: ");
//			if (scanf_s("%f", &concentration_to_check) != 1)
//			{
//				printf("Invalid medicine information.\n");
//				continue;
//			}
//
//			printf("New medicine quantity: ");
//			if (scanf_s("%d", &new_quantity) != 1)
//			{
//				printf("Invalid medicine information.\n");
//				continue;
//			}
//
//			printf("New medicine price: ");
//			if (scanf_s("%d", &new_price) != 1)
//			{
//				printf("Invalid medicine information.\n");
//				continue;
//			}
//
//			if (new_quantity < 1 || new_price < 1 || concentration_to_check < 0.1)
//			{
//				printf("Invalid medicine information.\n");
//				continue;
//			}
//
//			int medicine_position_if_it_exists = find_by_name_and_concentration(number_of_medicines, name_to_check, concentration_to_check, medicine_list);
//
//			if (medicine_position_if_it_exists != -1)
//			{
//				update_medicine_quantity_when_it_already_exists(medicine_position_if_it_exists, new_quantity, medicine_list);
//				printf("Medicine already exists! Updated its quantity.\n");
//				list_all_medicines(number_of_medicines, medicine_list);
//			}
//			else
//			{
//				char new_name[50];
//				strcpy(new_name, name_to_check);
//				float new_concentration = concentration_to_check;
//				//increment number of medicines in add functions
//				add_new_medicine(&number_of_medicines, new_name, new_concentration, new_quantity, new_price, medicine_list);
////				increment_number_of_medicine_value(&number_of_medicines);
//				list_all_medicines(number_of_medicines, medicine_list);
//			}
//
//		}
//
//		if (choice == 2)
//		{
//			printf("Please input the name and concentration of the medicine to delete: \n");
//
//			char name_to_delete[50];
//			float concentration_to_delete = 0;
//
//			printf("Name: ");
//			scanf("%s", &name_to_delete);
//			printf("Concentration: ");
//			scanf_s("%f", &concentration_to_delete);
//
//			int index_to_delete = find_by_name_and_concentration(number_of_medicines, name_to_delete, concentration_to_delete, medicine_list);
//
//			if (index_to_delete != -1)
//			{
//				//decrement in function
//				delete_medicine_by_its_index(index_to_delete, &number_of_medicines, medicine_list);
////				decrement_number_of_medicine_value(&number_of_medicines);
//				list_all_medicines(number_of_medicines, medicine_list);
//			}
//			else
//			{
//				printf("Item doesn't exist, can't be deleted.\n");
//				list_all_medicines(number_of_medicines, medicine_list);
//				continue;
//			}
//
//		}
//
//		if (choice == 3)
//		{
//			char name_of_medicine_to_update[50];
//			float concentration_of_medicine_to_update;
//
//			printf("Input the name of the medicine to update: ");
//			scanf("%s", &name_of_medicine_to_update);
//			printf("Input the concentration of the medicine to update: ");
//			scanf_s("%f", &concentration_of_medicine_to_update);
//
//			int index_of_medicine_to_update = find_by_name_and_concentration(number_of_medicines, name_of_medicine_to_update, concentration_of_medicine_to_update, medicine_list);
//
//			if (index_of_medicine_to_update != -1)
//			{
//				int update_choice = 0;
//
//				printf("Update quantity or price (1-quantity/2-price)? ");
//				scanf_s("%d", &update_choice);
//
//				if (update_choice == 1)
//				{
//					int new_quantity;
//					printf("Input new quantity value: ");
//
//					if (scanf_s("%d", &new_quantity) != 1)
//					{
//						printf("Invalid quantity value.");
//						continue;
//					}
//
//					if (new_quantity < 1)
//					{
//						printf("Invalid quantity value.");
//						continue;
//					}
//
//					update_medicine_quantity(index_of_medicine_to_update, new_quantity, medicine_list);
//					list_all_medicines(number_of_medicines, medicine_list);
//				}
//				else
//				{
//					int new_price;
//					printf("Input new price value: ");
//
//					if (scanf_s("%d", &new_price) != 1)
//					{
//						printf("Invalid price value.");
//						continue;
//					}
//
//					if (new_price < 1)
//					{
//						printf("Invalid price value.");
//						continue;
//					}
//
//					update_medicine_price(index_of_medicine_to_update, new_price, medicine_list);
//					list_all_medicines(number_of_medicines, medicine_list);
//				}
//			}
//			else
//			{
//				printf("Medicine doesn't exist, so it can't be updated!\n");
//				list_all_medicines(number_of_medicines, medicine_list);
//				continue;
//			}
//
//		}
//
//		if (choice == 4)
//		{
//			sort_medicine_list_in_ascending_order_by_name(medicine_list, 0, number_of_medicines - 1);
//
//			char word_to_search[50];
//			int index, read_letter_position = 0;
//
//			printf("Please input the word to search medicine by: ");
//			scanf("%[^\n]%*c", word_to_search);
//
//			if(word_to_search[0] == ' ' || word_to_search[0] == '\n')
//			{
//				printf("\nThe medicine names corresponding with the search element are: \n");
//				list_all_medicines(number_of_medicines, medicine_list);
//			}
//			else
//			{
//				printf("\nThe medicine names corresponding with the search element are: \n");
//				for (index = 0; index < number_of_medicines; index++)
//					if (search_if_word_exists_in_medicine_name(word_to_search, medicine_list[index].name))
//					{
//						printf("Name: %s", medicine_list[index].name);
//						printf(" Concentration: %.2f", medicine_list[index].concentration);
//						printf(" Quantity: %d", medicine_list[index].quantity);
//						printf(" Price: %d\n", medicine_list[index].price);
//					}
//			}
//		}
//
//		if (choice == 5)
//		{
//			int value_to_list_medicine_with_quantity_smaller_than_it;
//
//			printf("Input the value: ");
//			scanf_s("%d", &value_to_list_medicine_with_quantity_smaller_than_it);
//
//			if (value_to_list_medicine_with_quantity_smaller_than_it < 1)
//			{
//				printf("Invalid filter criteria.\n");
//			}
//			else
//			{
//				int index;
//
//				printf("Medicine with quantity value smaller than %d : \n", value_to_list_medicine_with_quantity_smaller_than_it);
//
//				for (index = 0; index < number_of_medicines; index++)
//				{
//					if(medicine_list[index].quantity < value_to_list_medicine_with_quantity_smaller_than_it)
//					{
//						printf("Name: %s", medicine_list[index].name);
//						printf(" Concentration: %.2f", medicine_list[index].concentration);
//						printf(" Quantity: %d", medicine_list[index].quantity);
//						printf(" Price: %d\n", medicine_list[index].price);
//					}
//				}
//			}
//		}
//	}
//	return 0;
//}
