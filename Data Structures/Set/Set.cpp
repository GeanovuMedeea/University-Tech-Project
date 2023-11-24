#include "Set.h"
#include "SetITerator.h"

Set::Set() {
	//declare the elements of the set class
	this->currentSize = 0;
	this->capacity = 1;
	this->elements = new TElem[capacity];
}


bool Set::add(TElem elem) {
	//adds a given element.function checks if it exists in the set. if the element exists, no add operation is performed. otherwise, the function checks if the size had reached
	//maximum capacity and enlarges the size of the array by 2. then adds the new element and increments the size of the set
	if (search(elem) == true)
		return false;
	if (this->currentSize == this->capacity) {
		this->capacity *= 2;
		TElem* temp = new TElem[this->capacity];
		for (int i = 0; i < this->currentSize; ++i)
			temp[i] = this->elements[i];
		delete[] this->elements;
		this->elements = temp;
	}
	this->elements[this->currentSize] = elem;
	this->currentSize = this->currentSize + 1;
	return true;
}
//Best case: Theta(1), Worst case: Theta(currentSize) => Total Complexity: O(currentSize)


bool Set::remove(TElem elem) {
	//removes a given element. function checks if it exists in the set. if the element doesn't exist, then no remove is permormed. otherwise, the element index is found and
	//it is removed by moving the other elements succeeding it one position to the left. then the size of the set is decreased by 1
	if (search(elem) == false)
		return false;
	bool found = false;
	for (int i = 0; i < this->currentSize - 1; ++i) {
		if (this->elements[i] == elem)
			found = true;
		if (found)
			this->elements[i] = this->elements[i + 1];
	}
	this->currentSize--;
	return true;
}
//Best case: Theta(1), Worst case: Theta(currentSize) => Total Complexity: O(CurrentSize)

bool Set::search(TElem elem) const {
	//checks if the given element is found in the set. if it exists, then the function returns true. otherwise, false

	int index = 0;
	while (index < this->currentSize)
	{
		if (this->elements[index] == elem)
		{
			return true;
		}
		index++;
	}
	return false;
}
//Best case: Theta(1), Worst case: Theta(currentSize) => Total Complexity: O(currentSize)

int Set::size() const {
	//returns the size of the set
	return this->currentSize;
}
//Theta(1)

bool Set::isEmpty() const {
	//checks if the size of the set is 0 (empty)
	if (this->currentSize == 0)
	{
		return true;
	}
	else
	{
		return false;
	}
}
//Theta(1)

Set::~Set() {
	//deletes the contents of the set
	delete[] this->elements;
}
//O(currentSize)

SetIterator Set::iterator() const {
	//generates an iterator for the set
	return SetIterator(*this);
}
//Theta(1)

