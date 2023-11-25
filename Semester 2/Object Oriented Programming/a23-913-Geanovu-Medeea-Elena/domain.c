#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "Domain.h"

//getters

int get_Medicine_id(TElem medicine)
{
	return medicine.id;
}

char* get_Medicine_name(TElem* medicine)
{
	return medicine->name_of_Medicine;
}

int get_Medicine_concentration(TElem medicine)
{
	return medicine.concentration;
}

int get_Medicine_quantity(TElem medicine)
{
	return medicine.quantity;
}

int get_Medicine_price(TElem medicine)
{
	return medicine.price;
}

//setters

//void set_Medicine_name(TElem* medicine, char* new_name_of_Medicine)
//{
//	strcpy(medicine->name_of_Medicine, new_name_of_Medicine);
//}
//
//void set_Medicine_concentration(TElem* medicine, int new_concentration)
//{
//	medicine->concentration = new_concentration;
//}
//
//void set_Medicine_quantity(TElem* medicine, int new_quantity)
//{
//	medicine->quantity = new_quantity;
//}
//
//void set_Medicine_price(TElem* medicine, int new_price)
//{
//	medicine->price = new_price;
//}