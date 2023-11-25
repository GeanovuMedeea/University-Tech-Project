#include "ExtendedTest.h"
#include "ShortTest.h"

#include "Map.h"


#include <iostream>
using namespace std;

//23.ADT Map–using a hashtable with coalesced chaining.

int main() {
	testAll();
	testAllExtended();

	cout << "That's all!" << endl;
	system("pause");
	return 0;
}


