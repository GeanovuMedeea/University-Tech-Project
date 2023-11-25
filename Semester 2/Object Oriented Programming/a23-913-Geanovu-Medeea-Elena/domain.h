#pragma once

typedef struct
{
	int id;
	char name_of_Medicine[30];
	int concentration, quantity, price;

} Medicine;

typedef Medicine TElem;

//Getters
int get_Medicine_id(TElem medicine);
char* get_Medicine_name(TElem* medicine);
int get_Medicine_concentration(TElem medicine);
int get_Medicine_quantity(TElem medicine);
int get_Medicine_price(TElem medicine);

//Setters
//void set_Medicine_name(TElem* medicine, char* new_name_of_medicine);
//void set_Medicine_concentration(TElem* medicine, int new_concentration);
//void set_Medicine_quantity(TElem* medicine, int new_quantity);
//void set_Medicine_price(TElem* medicine, int new_price);

//all below is for undo/redo

typedef struct
{
	// add OR delete OR update
	char operation[30];
	Medicine medicine;
} Operation;