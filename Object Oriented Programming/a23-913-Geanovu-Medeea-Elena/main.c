#include <stdio.h>
#include "Repository.h"
#include "DynamicArray.h"
#include "Service.h"
#include "UI.h"
#include "Tests.h"
#define _CRTDBG_MAP_ALLOC

#include <crtdbg.h>


int main(int argc, char** argv)
{
	Dynamic_array_of_medicines* repository = create_Repository();

	Dynamic_array_of_operations* Undo_Array = create_Dynamic_array_of_operations(2);

	Dynamic_array_of_operations* Redo_Array = create_Dynamic_array_of_operations(2);

	TestAll();

	initialize_Service(repository);

	start_UI(repository, Undo_Array, Redo_Array);

	destroy_Dynamic_array_of_Medicines(repository);

	destroy_Dynamic_array_of_operations(Undo_Array);

	destroy_Dynamic_array_of_operations(Redo_Array);

	_CrtDumpMemoryLeaks();
	return 0;
}