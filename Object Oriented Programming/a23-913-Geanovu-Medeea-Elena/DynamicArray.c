#include <stdlib.h>
#include <string.h>
#include "DynamicArray.h"


Dynamic_array_of_medicines* create_Dynamic_array_of_medicines(int capacity)
{

	Dynamic_array_of_medicines* Dynamic_array = malloc(sizeof(Dynamic_array_of_medicines));

	if (Dynamic_array == NULL)
		return NULL;

	Dynamic_array->capacity = capacity;
	Dynamic_array->size = 0;
	Dynamic_array->elements = malloc(capacity * sizeof(TElem));

	if (Dynamic_array->elements == NULL)
		return NULL;

	return Dynamic_array;
}

int get_Dynamic_Array_of_Medicines_size(Dynamic_array_of_medicines* Dynamic_array)
{
	return Dynamic_array->size;
}

int get_Dynamic_array_of_Medicines_capacity(Dynamic_array_of_medicines* Dynamic_array)
{
	return Dynamic_array->capacity;
}

void add_new_Medicine_to_Dynamic_array_of_Medicines(Dynamic_array_of_medicines* Dynamic_array, TElem element)
{
	if (Dynamic_array->size == Dynamic_array->capacity)
		resize_Dynamic_array_of_Medicines(Dynamic_array);

	Dynamic_array->elements[Dynamic_array->size] = element;
	Dynamic_array->size++;
}

void resize_Dynamic_array_of_Medicines(Dynamic_array_of_medicines* Dynamic_array)
{
	if (Dynamic_array == NULL)
		return;

	int new_capacity = Dynamic_array->capacity * 2;

	Dynamic_array->capacity *= 2;
	void* temporary_Dynamic_Array_to_transfer_data = realloc(Dynamic_array->elements, new_capacity * sizeof(TElem));

	if (temporary_Dynamic_Array_to_transfer_data == NULL)
		return;

	Dynamic_array->capacity = new_capacity;
	Dynamic_array->elements = temporary_Dynamic_Array_to_transfer_data;
}

void destroy_Dynamic_array_of_Medicines(Dynamic_array_of_medicines* Dynamic_array)
{
	if (Dynamic_array == NULL)
		return;

	free(Dynamic_array->elements);
	free(Dynamic_array);
}

Dynamic_array_of_operations* create_Dynamic_array_of_operations(int capacity)
{
	Dynamic_array_of_operations* Dynamic_array = malloc(sizeof(Dynamic_array_of_operations));

	if (Dynamic_array == NULL)
		return NULL;

	Dynamic_array->capacity = capacity;
	Dynamic_array->size = 0;
	Dynamic_array->elements = malloc(capacity * sizeof(Operation));

	if (Dynamic_array->elements == NULL)
		return NULL;

	return Dynamic_array;
}

int get_Dynamic_array_of_operations_size(Dynamic_array_of_operations* Dynamic_array)
{
	return Dynamic_array->size;
}

int get_Dynamic_array_of_operations_capacity(Dynamic_array_of_operations* Dynamic_array)
{
	return Dynamic_array->capacity;
}

void add_Medicine_to_Dynamic_array_of_operations(Dynamic_array_of_operations* Dynamic_array, Operation element)
{
	if (Dynamic_array->size == Dynamic_array->capacity)
		resize_Dynamic_array_of_operations(Dynamic_array);

	Dynamic_array->elements[Dynamic_array->size] = element;
	Dynamic_array->size++;
}

void resize_Dynamic_array_of_operations(Dynamic_array_of_operations* Dynamic_array)
{
	if (Dynamic_array == NULL)
		return;

	int new_capacity = Dynamic_array->capacity * 2;

	Dynamic_array->capacity *= 2;
	void* temporary_Dynamic_Array_to_transfer_data = realloc(Dynamic_array->elements, new_capacity * sizeof(Operation));

	if (temporary_Dynamic_Array_to_transfer_data == NULL)
		return;

	Dynamic_array->capacity = new_capacity;
	Dynamic_array->elements = temporary_Dynamic_Array_to_transfer_data;
}

void destroy_Dynamic_array_of_operations(Dynamic_array_of_operations* Dynamic_array)
{
	if (Dynamic_array == NULL)
		return;

	free(Dynamic_array->elements);
	free(Dynamic_array);
}