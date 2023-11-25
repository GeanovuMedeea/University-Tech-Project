#include <assert.h>
#include <exception>

#include "ShortTest.h"
#include "IndexedList.h"
#include "ListIterator.h"

using namespace std;
#include <iostream>


void testAll() {
    IndexedList list = IndexedList();
    assert(list.isEmpty());
    list.addToEnd(1);
    assert(list.size() == 1);
    list.addToPosition(0,2);
    assert(list.size() == 2);
    ListIterator it = list.iterator();
    assert(it.valid());
    it.next();
    assert(it.getCurrent() == 1);
    it.first();
    assert(it.getCurrent() == 2);
    assert(list.search(1) == 1);
    assert(list.setElement(1,3) == 1);
    assert(list.getElement(1) == 3);
    assert(list.remove(0) == 2);
    assert(list.size() == 1);
    list.setElement(0, 0);
    assert(list.size() == 1);
    for (int i = 1; i < 10; i++)
    {
        list.addToEnd(i);
    }
    assert(list.size() == 10);
    int start = 1;
    int end = 5;
    list.removeBetween(start, end);
    assert(list.size() == 5);
    list.removeBetween(0,0);
    list.removeBetween(list.size()-1, list.size()-1);
    assert(list.size() == 3);
}