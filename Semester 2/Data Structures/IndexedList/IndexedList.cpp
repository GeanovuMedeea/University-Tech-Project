#include <exception>
#include <iostream>

#include "IndexedList.h"
#include "ListIterator.h"

using namespace std;

IndexedList::IndexedList() {
    //calls the initialing settings for DLL
    this->head = nullptr;
    this->tail = nullptr;
    this->length = 0;
}

int IndexedList::size() const {
    //returns the length fiels in the DLL
    return this->length;
}

bool IndexedList::isEmpty() const {
    //checks if the size (length) of the DLL is 0
    return size() == 0;
}

TElem IndexedList::getElement(int pos) const {
    //returns the data of the node in the DLL which can be found on the given position, and if not found, -1
    //it creates a node which corresponds with the head of the DLL and iterates with "next" until the element is found
    if (pos <= -1 || pos >= size()) {
        throw std::exception();
    }

    Node* current = this->head;

    int position = 0;
    while (position < pos && current != nullptr) {
        current = current->next;
        position++;
    }

    if (current == nullptr)
    {
        return -1;
    }
    return current->data;
}

TElem IndexedList::setElement(int pos, TElem e) {
    //changes the data of the node found on the given position to the given TElem data "e",and returns the old data of the modified node
    //it creates a node which corresponds with the head of the DLL and iterated with "next" until it is found
    if (pos <= -1 || pos >= size()) {
        throw std::exception();
    }

    Node* current = this->head;
    //    Node* current = this->indexed_list.getHead();

    int position = 0;
    while (position < pos && current != nullptr) {
        current = current->next;
        position++;
    }

    TElem previous;
    previous = current->data;
    current->data = e;

    return previous;
}

void IndexedList::addToEnd(TElem e) {
    //adds at the end of the DLL a new node with data "e"
    //uses the tail of the DLL as reference to where the new node should be attached to

    Node* current = this->tail;
    Node* newNode = new Node();

    if (current == nullptr)
    {
        newNode->data = e;
        newNode->prev = nullptr;
        newNode->next = nullptr;
        this->head = newNode;
        this->tail = newNode;
        this->length = this->length + 1;
    }
    else
    {
        newNode->data = e;
        newNode->prev = current;
        newNode->next = nullptr;
        current->next = newNode;
        this->tail = newNode;
        this->length = this->length + 1;
    }
}

void IndexedList::addToPosition(int pos, TElem e) {
    //adds to a given position a new node with data "e" in the DLL at the given position

    if (pos <= -1 || pos >= size()) {
        throw std::exception();
    }

    //the dll is empty
    if (this->head == nullptr)
    {
        Node* newNode = new Node();
        newNode->data = e;
        newNode->prev = nullptr;
        newNode->next = nullptr;
        this->head = newNode;
        this->length = this->length + 1;
        return;
    }

    //the elements is to be added as the new head of the DLL
    if (pos == 0)
    {
        Node* newNode = new Node();
        newNode->data = e;
        newNode->prev = nullptr;
        newNode->next = this->head;
        this->head->prev = newNode;
        this->head = newNode;
        this->length = this->length + 1;
        return;
    }

    Node* current = this->head;
    int currentPosition = 0;

    //iterates until the position will correspond to the given index
    while (currentPosition < pos - 1 && current->next != nullptr)
    {
        current = current->next;
        currentPosition++;
    }

    //if the current node is the end and the position hasn't been reached, then quit
    if (current->next == nullptr && currentPosition < pos - 1)
    {
        return;
    }

    //the position is somewhere in the valid limits of the DLL, and not the head nor the tail
    Node* newNode = new Node();
    newNode->data = e;
    newNode->next = current->next;
    newNode->prev = current;
    if (current->next != nullptr)
    {
        current->next->prev = newNode;
    }
    current->next = newNode;
    this->length = this->length + 1;
}

TElem IndexedList::remove(int pos) {
    //removes a node which can be found at the given position from the DLL

    if (pos <= -1 || pos >= size()) {
        throw std::exception();
    }

    if (this->head == nullptr) {
        // List is empty
        return -1;
    }

    if (pos == 0) {
        // Removing the head node
        Node* temp = this->head;
        this->head = this->head->next;
        if (this->head != nullptr)
            this->head->prev = nullptr;
        this->length = this->length - 1;
        int data = temp->data;
        delete temp;
        return data;
    }

    Node* current = this->head;
    int currentPosition = 0;
    while (current != nullptr && currentPosition < pos) {
        current = current->next;
        currentPosition++;
    }

    if (current == nullptr) {
        // Position is out of range
        return -1;
    }

    Node* prevNode = current->prev;
    Node* nextNode = current->next;
    if (prevNode != nullptr) {
        prevNode->next = nextNode;
    }
    if (nextNode != nullptr) {
        nextNode->prev = prevNode;
    }

    this->length = this->length - 1;
    int data = current->data;
    delete current;
    return data;
}

int IndexedList::search(TElem e) const {
    //returns the position in the DLL at which the node with data equal to "e" is found, and -1 otherwise
    Node* current = this->head;
    int position = 0;
    while (current != nullptr) {
        if (current->data == e) {
            return position; // Return position if found
        }
        current = current->next;
        position++;
    }
    return -1;
}

ListIterator IndexedList::iterator() const {
    //instantiates a new iterator for the DLL
    return ListIterator(*this);
}

void IndexedList::removeBetween(int start, int end) {

    //removes all the elements from the DLL between the positions of start and end
    if (start < 0 || end < 0 || start >= size() || end >= size() || start-end > 0)
        throw std::exception();

    Node* startNode = this->head;
    Node* endNode = this->head;
    int position = 0;

    while (position < start && startNode != nullptr)
    {
        startNode = startNode->next;
        position++;
    }

    if (startNode == nullptr)
        return;

    position = 0;
    while (position < end && endNode != nullptr)
    {
        endNode = endNode->next;
        position++;
    }

    if (endNode == nullptr)
        return;

    if (startNode->prev == nullptr && endNode->next == nullptr)
    {
        this->head = nullptr;
        this->tail = nullptr;
        this->length = 0;
        return;
    }

    if (startNode->prev == nullptr)
    {
        this->head = endNode->next;
        this->length = this->length - 1;
        return;
    }

    if (endNode->next == nullptr)
    {
        this->tail = startNode->prev;
        this->length = this->length - 1;
        return;
    }

    startNode->prev->next = endNode->next;
    endNode->next->prev = startNode->prev;

    this->length = this->length - end - start + 1;
}

IndexedList::~IndexedList() {
    //destroys the DLL
    Node* current = this->head;
    while (current != nullptr) {
        Node* next = current->next;
        delete current;
        current = next;
    }
}