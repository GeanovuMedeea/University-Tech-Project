#include "ExtendedTest.h"
#include "ShortTest.h"

#include "FixedCapBiMap.h"
#include "FixedCapBiMapIterator.h"


#include <iostream>
using namespace std;


int main() {
	//testAll();
	//testAllExtended();

	FixedCapBiMap m(10);
	m.add(5, 5);
	m.add(2, 3);
	m.add(1, 9);
	m.add(21, 5);
	m.add(3, 6);

	TElem e;
	FixedCapBiMapIterator id = m.iterator();
	id.first();
	int s1 = 0, s2 = 0;
	while (id.valid()) {
		e = id.getCurrent();
		printf("%d", e.first);
		printf(" ");
		printf("%d", e.second);
		printf("\n");
		id.next();
	}

	cout << "That's all!" << endl;
	system("pause");
	return 0;
}

