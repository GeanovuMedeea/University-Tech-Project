#include "Map.h"
#include "MapIterator.h"
#include <exception>
using namespace std;

//Best case: Theta(1), Worst case: Theta(nrOfElements) => Total Complexity: O(nrOfElements)
void MapIterator::displacement() {
	while ((current < map.capacity) && map.elements[current] == NULL_TELEM)
		current++;
}

//Best case: Theta(1), Worst case: Theta(nrOfElements) => Total Complexity: O(nrOfElements)
MapIterator::MapIterator(const Map& d) : map(d)
{
	current = 0;
	displacement();
}

//Best case: Theta(1), Worst case: Theta(nrOfElements) => Total Complexity: O(nrOfElements)
void MapIterator::first() {
	current = 0;
	displacement();
}

//Best case: Theta(1), Worst case: Theta(nrOfElements) => Total Complexity: O(nrOfElements)
void MapIterator::next() {
	if (!valid())
		throw std::exception();
	current++;
	displacement();
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
TElem MapIterator::getCurrent(){
	return map.elements[current];
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
bool MapIterator::valid() const {
	return (current < map.capacity && current >= 0);
}



