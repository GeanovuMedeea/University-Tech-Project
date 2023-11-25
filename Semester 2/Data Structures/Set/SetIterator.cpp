#include "SetIterator.h"
#include "Set.h"
#include <exception>
using namespace std;


SetIterator::SetIterator(const Set& m) : set(m) //initialize an object of type Set defined in Set.h
{
	//the index which will be used for parsing the set is initialized with the value 0 (the start)
	this->current = 0;
}
//Theta(1)

void SetIterator::first() {
	//the index is set to the start of the set, meaning position 0
	this->current = 0;
}
//Theta(1)

void SetIterator::next() {
	//checks if the current index surpasses the size of the set. if it doesn't, then function returns the next position for the index in the set
	if (this->current >= this->set.currentSize)
	{
		throw exception();
	}
	else
	{
		this->current++;
	}
}
//Theta(1)

void SetIterator::previous() {
	//checks if the current index surpasses teh size of the set or if it goes before the first existing element in the set. If it doesn't, then the function returns the
	//previous position for the index in the set
	if (this->current < 0)
	{
		throw exception();
	}
	else
	{
		this->current--;
	}
}
//Theta(1)

TElem SetIterator::getCurrent()
{
	//checks if the current index surpasses the size of the set. if it doesn't, then the function returns the elment in the set with the given index
	if (this->current >= this->set.currentSize)
	{
		throw exception();
	}
	return this->set.elements[this->current];
}
//Theta(1)

bool SetIterator::valid() const {
	//checks if the current index does not surpass the size of the set. if it doesn't, then the function returns true, meaning that the index is valid for the set
	if (this->current >= 0 && this->current < this->set.currentSize)
	{
		return true;
	}
	else
	{
		return false;
	}
}
//Theta(1)

//bool SetIterator::backwardsValid() const {
//	//checks if the current index does not decrease beyond the first position of the set. If it doesn't, then the function returns true, meaning that the index is valid for the set
//	if (this->current > 0)
//	{
//		return true;
//	}
//	else
//	{
//		return false;
//	}
//}
////Theta(1)