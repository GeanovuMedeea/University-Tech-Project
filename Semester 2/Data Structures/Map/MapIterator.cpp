#include "Map.h"
#include "MapIterator.h"
#include <exception>
#include <iostream>
using namespace std;

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
MapIterator::MapIterator(const Map& d) : map(d)
{
	pos = map.elems.head;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void MapIterator::first() {
	pos = map.elems.head;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void MapIterator::next() {
	if (this->valid() == false)
		throw std::exception();
	pos = map.elems.list[pos].next;
	//std::cout << pos << endl;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
TElem MapIterator::getCurrent(){
	if (this->valid() == false)
		throw std::exception();
	return map.elems.list[pos].info;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
bool MapIterator::valid() const {
	return pos != -1;
}

//throws an exception if the iterator is invalid or k is negative or zero
//moves the current element from the iterator ksteps backward, or makes the 
//iterator invalid if there are less than kelements left until the beginning of the Set.


//Best case: Theta(1), Worst case: Theta(current position in array) => Total Complexity: O(current position in array) O(n)
void MapIterator::jumpBackward(int k)
{
	//it doesn't work well since the list is not linked well (check for firstEmpty)
	if (k <= 0 || this->valid() == false)
		throw std::exception();
	if (map.size() <= k)
	{
		pos = -1;
		return;
	}
	if (pos < k)
	{
		pos = -1;
		return;
	}
	int initialPos = pos;
	first();
	int firstCount = 0;
	int count = 0;
	//std::cout << "init: " << initialPos << endl;

	if (firstCount + k - 1 == 0)
	{
		//std::cout << "1: " << map.elems.list[pos].info.first << " 2: " << map.elems.list[pos].info.second << endl;
		return;
	}

	while (firstCount + k - 1 < initialPos)
	{
		firstCount++;
		pos = map.elems.list[pos].next;
	}


	//std::cout << "1: " << map.elems.list[pos].info.first << " 2: " << map.elems.list[pos].info.second << endl;
	//int firstCount = map.size() - k;
	//int count = 0;
	//first();
	//while (count != firstCount)
	//{
	//	std::cout << map.elems.list[pos].info.first << " " << map.elems.list[pos].info.second << endl;
	//	pos = map.elems.list[pos].next;
	//	count++;
	//}

}
