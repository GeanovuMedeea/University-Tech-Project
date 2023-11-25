#pragma once
#include "Domain.h"

typedef struct
{
	TElem* elements;
	int size, capacity;
} Dynamic_array_of_medicines;

typedef Medicine TElem;

Dynamic_array_of_medicines*  create_Dynamic_array_of_medicines(int capacity);
int get_Dynamic_Array_of_Medicines_size(Dynamic_array_of_medicines* dynamic_Array);
int get_Dynamic_array_of_Medicines_capacity(Dynamic_array_of_medicines* dynamic_Array);
void add_new_Medicine_to_Dynamic_array_of_Medicines(Dynamic_array_of_medicines* dynamic_Array, TElem medicine);
void resize_Dynamic_array_of_Medicines(Dynamic_array_of_medicines* dynamic_Array);
void destroy_Dynamic_array_of_Medicines(Dynamic_array_of_medicines* dynamic_Array);

typedef struct
{
	Operation* elements;
	int size, capacity;
} Dynamic_array_of_operations;

Dynamic_array_of_operations* create_Dynamic_array_of_operations(int capacity);
int get_Dynamic_array_of_operations_size(Dynamic_array_of_operations* dynamic_Array);
int get_Dynamic_array_of_operations_capacity(Dynamic_array_of_operations* dynamic_Array);
void add_Medicine_to_Dynamic_array_of_operations(Dynamic_array_of_operations* dynamic_Array, Operation dynamic_Array_of_Operations);
void resize_Dynamic_array_of_operations(Dynamic_array_of_operations* dynamic_Array);
void destroy_Dynamic_array_of_operations(Dynamic_array_of_operations* dynamic_Array);