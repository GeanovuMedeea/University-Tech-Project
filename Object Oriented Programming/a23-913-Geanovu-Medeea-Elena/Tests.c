#include <assert.h>
#include "Domain.h"
#include "Repository.h"
#include "Service.h"
#include "DynamicArray.h"

void testDomain()
{
    Medicine medicine = { 1, "Ketorol", 250, 100, 300 };
    assert(get_Medicine_id(medicine) == 1);
    assert(get_Medicine_concentration(medicine) == 250);
    assert(get_Medicine_quantity(medicine) == 100);
    assert(get_Medicine_price(medicine) == 300);
    assert(strcmp(get_Medicine_name(&medicine), "Ketorol") == 0);
}

void testRepository() {
    Dynamic_array_of_medicines* repository = create_Repository();
    Medicine medicine = { 1, "Ketorol", 250, 100, 300 };
    add_new_Medicine_to_Repository(repository, 1, "Ketorol", 250, 100, 300);
    assert(get_Dynamic_Array_of_Medicines_size(repository) == 1);
    assert(get_Medicine_id(repository->elements[0]) == 1);
    assert(get_Medicine_concentration(repository->elements[0]) == 250);
    assert(get_Medicine_quantity(repository->elements[0]) == 100);
    assert(get_Medicine_price(repository->elements[0]) == 300);
    assert(strcmp(get_Medicine_name(&repository->elements[0]), "Ketorol") == 0);
    delete_Medicine_by_given_name_and_concentration_from_Repository(repository, "Ketorol", 500);
    assert(get_Dynamic_Array_of_Medicines_size(repository) == 1);
    add_new_Medicine_to_Repository(repository, 3, "Nurofen", 1000, 50, 100);
    assert(get_Dynamic_Array_of_Medicines_size(repository) == 2);
    assert(get_Medicine_id(repository->elements[1]) == 3);
    assert(get_Medicine_concentration(repository->elements[1]) == 1000);
    assert(get_Medicine_quantity(repository->elements[1]) == 50);
    assert(get_Medicine_price(repository->elements[1]) == 100);
    assert(strcmp(get_Medicine_name(&repository->elements[1]), "Nurofen") == 0);
    delete_Medicine_by_given_name_and_concentration_from_Repository(repository, "Nurofen", 1000);
    assert(get_Dynamic_Array_of_Medicines_size(repository) == 1);
}

void testService()
{
    Dynamic_array_of_medicines* repository = create_Repository();
    Dynamic_array_of_operations* Undo_array = create_Dynamic_array_of_operations(2);
    Dynamic_array_of_operations* Redo_array = create_Dynamic_array_of_operations(2);
    initialize_Service(repository);
    add_new_Medicine_Service(repository, Undo_array, Redo_array, 1, "Ketorol", 500, 10, 10);
    assert(get_Dynamic_Array_of_Medicines_size(repository) == 11);
    add_new_Medicine_Service(repository, Undo_array, Redo_array, 2, "Panadol", 150, 10, 20);
    assert(get_Dynamic_Array_of_Medicines_size(repository) == 12);
    update_Medicine_concentration_Service(repository, Undo_array, Redo_array, "Ketorol", 500, 1000);
    assert(get_Medicine_concentration(repository->elements[10]) == 1000);
    update_Medicine_quantity_by_given_name_and_concentration_Service(repository, Undo_array, Redo_array, "Ketorol", 1000, 20);
    assert(get_Medicine_quantity(repository->elements[10]) == 20);
    update_Medicine_price_by_given_name_and_concentration_Service(repository, Undo_array, Redo_array, "Ketorol", 1000, 30);
    assert(get_Medicine_price(repository->elements[10]) == 30);
    assert(get_Dynamic_Array_of_Medicines_size(get_Medicines_by_name_Service(repository, "Paracet")) == 1);

}

int TestAll()
{
    testDomain();
    testRepository();
    testService();
}