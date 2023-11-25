#pragma once
//DO NOT INCLUDE LISTITERATOR

//DO NOT CHANGE THIS PART
typedef int TElem;
#define NULL_TELEM -11111
class ListIterator;

class Node {
public:
    int data;
    Node* next;
    Node* prev;
};


class IndexedList {
private:
    int length;
    Node* head;
    Node* tail;
    friend class ListIterator;
public:
    // constructor
    IndexedList();
    //Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)

    // returns the number of elements from the list
    int size() const;
    //Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)

    // checks if the list is empty
    bool isEmpty() const;
    //Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)

    // returns an element from a position
    //throws exception if the position is not valid
    TElem getElement(int pos) const;
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

    // modifies an element from a given position
    //returns the old value from the position
    //throws an exception if the position is not valid
    TElem setElement(int pos, TElem e);
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

    // adds an element to the end of the list
    void addToEnd(TElem e);
    //Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)

    // adds an element to a given position
    //throws an exception if the position is not valid
    void addToPosition(int pos, TElem e);
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

    // removes an element from a given position
    //returns the removed element
    //throws an exception if the position is not valid
    TElem remove(int pos);
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

    // searches for an element and returns the first position where the element appears or -1 if the element is not in the list
    int search(TElem e)  const;
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

    // returns an iterator set to the first element of the list or invalid if the list is empty
    ListIterator iterator() const;
    //Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)

    //removes all elements between two positions, start and end
    //throws exception if start or end is not valid
    void removeBetween(int start, int end);
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

    //destructor
    ~IndexedList();
    //Best case: Theta(1), Worst case: Theta(length) => Total Complexity: O(length)

};