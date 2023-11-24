#include "ListIterator.h"
#include "IndexedList.h"
#include <exception>

ListIterator::ListIterator(const IndexedList& list) : list(list){
    //initializez the iterator, implciitely it should be 0 as a new one
    this->current = 0;
}
//Best case = Worst case = 0

void ListIterator::first(){
    //changes the iterator's position to 0
    this->current = 0;
}
//Best case = Worst case = 0

void ListIterator::next(){
    //checks if the position is valid, and if it is, then increments the current position by 1
    if (valid() == false)
    {
        throw std::exception();
    }
    this->current++;
}
//Best case = Worst case = 0

bool ListIterator::valid() const{
    if(this->current < 0)
        return false;
    return this->current < this->list.size();
}
//Best case = Worst case = 0

TElem ListIterator::getCurrent() const{
    return this->list.getElement(current);
}
//Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)