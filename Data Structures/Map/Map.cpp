#include "Map.h"
#include "MapIterator.h"
#include <exception>
#include <iostream>

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
Map::Map() {
	this->length = 0;
}

//Best case: Theta(n), Worst case: Theta(n) => Total Complexity: O(n)
Map::~Map() {
	delete[] elems.list;
}


//Best case: Theta(1), Worst case: Theta(n) => Total Complexity: O(n)
TValue Map::add(TKey c, TValue v) {
    SLLA& elements = this->elems;

    if (elements.size == elements.capacity && elements.first_empty == -1) {
        elements.capacity *= 2;
        auto* aux = new SLLANode[elements.capacity];
        for (int i = 0; i < elements.size; ++i)
            aux[i] = elements[i];
        delete[] elements.list;
        elements.list = aux;
    }

    if (elements.first_empty == -1) {
        //std::cout << "r " << elements[elements.tail].info.first << " " << elements[elements.tail].info.second << std::endl;
        elements.first_empty = elements.size;
        elements[elements.first_empty] = SLLANode(NULL_TELEM, -1);
        ++elements.size;
    }

    if (this->length == 0) {
        int copy_empty = elements.first_empty;
        elements.first_empty = elements[elements.first_empty].next;
        elements[copy_empty] = SLLANode(TElem(c, v), -1);
        elements.tail = elements.head = copy_empty;
        ++this->length;
        //std::cout << "empty " << elements[elements.tail].info.first << " " << elements[elements.tail].info.second << " " << elements.tail << " " << elements.first_empty << std::endl;
        return NULL_TVALUE;
    }

    if (this->search(c) != NULL_TVALUE)
    {

        int copy_empty = elements.first_empty;
        if (c == elements[elements.head].info.first) {  //place it in first position
            int previousValue = elements[elements.head].info.second;
            elements[elements.head].info.second = v;
            //std::cout << "first: " << elements[elements.tail].info.first << " " << elements[elements.tail].info.second << " " << elements.tail << " " << elements.first_empty << std::endl;
            //std::cout << elements.tail << " " << previousValue << std::endl;
            return previousValue;
        }


        if (elements[elements.tail].info.first == c) {  //place it in last position
            int previousValue = elements[elements.tail].info.second;
            elements[elements.tail].info.second = v;
            //std::cout << "last: " << elements[elements.tail].info.first << " " << elements[elements.tail].info.second << " " << elements.tail << " " << elements.first_empty << std::endl;
            //std::cout << elements.tail << " " << previousValue << std::endl;
            return previousValue;
        }

        int after = elements.head;
        for (int node = elements.head; true; node = elements[node].next) {
            if (elements[node].info.first != c)
                after = node;
            else break;
        }
        after = elements[after].next;


        int previousValue = elements[after].info.second;
        elements[after].info.second = v;
        //std::cout <<"double end " <<  elements[elements.tail].info.first << " " << elements[elements.tail].info.second << " " << elements.tail << " " << elements.first_empty << std::endl;
        //std::cout << elements.tail << " " << previousValue<< std::endl;
        return previousValue;
    }
    else
    {
        int newNode = elements.first_empty;
        elements.first_empty = elements[newNode].next;
        elements[newNode] = SLLANode(TElem(c, v), -1);

        elements[elements.tail].next = newNode;
        elements.tail = newNode;
        //std::cout << "normal: " << elements[elements.tail].info.first << " " << elements[elements.tail].info.second << " " << elements.tail << " " << elements.first_empty << std::endl;
        ++this->length;
    }
    return NULL_TVALUE;
}


//Best case: Theta(1), Worst case: Theta(n) => Total Complexity: O(n)
//
TValue Map::search(TKey c) const {
    int count = 0;
    for (int node = this->elems.head; node != -1 && this->size() > count; node = this->elems.list[node].next)
    {
        //std::cout << "info: " << this->elems.list[node].info.first << " next: " << this->elems.list[node].next << " ";
        if (this->elems.list[node].info.first == c)
        {
            //std::cout << "c " << elems.list[node].info.first << " " << elems.list[node].info.second << std::endl;
            return this->elems.list[node].info.second;
        }
        count++;
    }
    return NULL_TVALUE;
}


//Best case: Theta(1), Worst case: Theta(n) => Total Complexity: O(n)
TValue Map::remove(TKey c) {
    SLLA& elements = this->elems;

    if (this->length == 0 || this->search(c) == NULL_TVALUE)
        return NULL_TVALUE;

    if (this->length == 1) {
        if (elements[elements.head].info.first != c)
            return NULL_TVALUE;

        int previousValue = elements[elements.head].info.second;
        elements[elements.head].next = elements.first_empty;
        elements.first_empty = elements.head;
        elements.head = elements.tail = -1;
        --this->length;
        //std::cout << "len 1: " << previousValue;
        return previousValue;
    }

    if (elements[elements.head].info.first == c) {
        int previousValue = elements[elements.head].info.second;
        int copy_new_head = elements[elements.head].next;
        elements[elements.head].next = elements.first_empty;
        elements.first_empty = elements.head;
        elements.head = copy_new_head;
        --this->length;
        //std::cout << "first: " << previousValue;
        return previousValue;
    }

    int pos = -1, prev = -1;
    for (int node = elements.head; node != -1; node = elements[node].next) {
        if (c == elements[node].info.first) {
            pos = node;
            break;
        }
        prev = node;
    }

    //if (pos == -1)
    //    return NULL_TVALUE;

    if (elements[elements.tail].info.first == c) {
        int previousValue = elements[elements.tail].info.second;
        elements[prev].next = elements.first_empty;
        elements.first_empty = elements.tail;
        elements.tail = prev;
        elements[elements.tail].next = -1;
        --this->length;
        //std::cout << "tail: " << previousValue;
        return previousValue;
    }

    // If the key is in the middle of the list
    //std::cout << elements[prev].info.first << " " << elements[prev].info.second << " ";
    //std::cout << pos << " " << prev;
    int previousValue = elements[pos].info.second;
    elements[prev].next = elements[pos].next;
    elements[pos].next = elements.first_empty;
    elements.first_empty = pos;
    --this->length;
   // std::cout << "middle: " << previousValue << " ";
    return previousValue;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
int Map::size() const {
	return this->length;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
bool Map::isEmpty() const {
	return this->length == 0;
}

MapIterator Map::iterator() const {
	return MapIterator(*this);
}

//Map::SLLA::SLLA() {
//	this->capacity = 1;
//	this->size = 1;
//	this->list = new SLLANode[this->capacity];
//	this->list[0] = SLLANode(NULL_TELEM, -1);
//	this->first_empty = 0;
//	this->head = -1;
//	this->tail = -1;
//}
//
//Map::SLLANode& Map::SLLA::operator[](int pos) {
//	return this->list[pos];
//}
//
//Map::SLLANode::SLLANode(TElem info, int next) : info{ std::move(info) }, next{ next } {}
//
//Map::SLLANode::SLLANode() : info{ NULL_TELEM }, next{ 0 } {} //NEXT 0?

