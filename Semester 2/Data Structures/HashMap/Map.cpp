#include "Map.h"
#include "MapIterator.h"
#include <utility>

#include <iostream>

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
int Map::hash(TValue key) const {
	return abs(key) % capacity;
}

//Best case: Theta(1), Worst case: Theta(capacity) => Total Complexity: O(capacity)
Map::Map() {
	capacity = 100;
	elements = new TElem[capacity];
	next = new int[capacity];
	for (int i = 0; i < capacity; i++) {
		elements[i] = NULL_TELEM;
		next[i] = -1;
	}
	firstFree = 0;
	nrOfElements = 0;
}

//Best case: Theta(1), Worst case: Theta(capacity) => Total Complexity: O(capacity)
void Map::resize() {
    auto* old = new TElem[nrOfElements];
    for (int i = 0; i < nrOfElements; ++i) {
        old[i] = NULL_TELEM;
    }
    int current = 0;
    for (int i = 0; i < capacity; ++i) {
        if (elements[i] != NULL_TELEM) {
            old[current].first = elements[i].first;
            old[current].second = elements[i].second;
            current++;
        }
    }

    capacity *= 2;
    auto* nou = new TElem[capacity];
    int* nextNew = new int[capacity];
    for (int i = 0; i < capacity; ++i) {
        nou[i] = NULL_TELEM;
        nextNew[i] = -1;
    }
    delete[] elements;
    delete[] next;
    elements = nou;
    next = nextNew;

    firstFree = 0;
    int oldNrOfElements = nrOfElements;
    nrOfElements = 0;
    for (int i = 0; i < oldNrOfElements; ++i) {
        add(old[i].first, old[i].second);
    }
    delete[] old;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
Map::~Map() {
    delete[] elements;
    delete[] next;
}

//Best case: Theta(1), Worst case: Theta(numberOfElements) => Total Complexity: O(numberOfElements)
TValue Map::add(TKey c, TValue v){
    if ((nrOfElements / capacity) * 100 > 70)
        resize();

    int i = hash(c);
    // If the element's hash value is max,
    // it means that the element is not in the set.
    // The element is added to the elements array of the first free position.
    //if (c == 98) {
    //    std::cout << '\n' << c << '\n';
    //    for (int k = 0; k <= nrOfElements; k++)
    //        std::cout << k << " " << elements[k].first << " " << elements[k].second << " " << next[k] << '\n';
    //}
    if (elements[i] == NULL_TELEM)
    {
        elements[i].first = c;
        elements[i].second = v;
        if (c < minKey)
            minKey = c;
        if (c > maxKey)
            maxKey = c;
        if (i == firstFree) {
            firstFree++;
            while ((firstFree < capacity) && elements[firstFree] != NULL_TELEM)
                firstFree++;
        }
        nrOfElements++;
        return NULL_TVALUE;
    }

    // If the element is already in the set, the method returns false.
    int j = -1;
    while (i != -1)
    {
        if (elements[i].first == c)
        {
            if (c < minKey)
                minKey = c;
            if (c > maxKey)
                maxKey = c;
            int old = elements[i].second;
            elements[i].second = v;
            return old;
        }
        j = i;
        i = next[i];
    }

    // If the element is not in the set, it is added to the elements array
    // of the first free position. The next array is updated.
    if (c < minKey)
        minKey = c;
    if (c > maxKey)
        maxKey = c;
    elements[firstFree].first = c;
    elements[firstFree].second = v;
    next[j] = firstFree;
    firstFree++;
    while ((firstFree < capacity) && elements[firstFree] != NULL_TELEM)
        firstFree++;
    nrOfElements++;
    return NULL_TVALUE;
}

//Best case: Theta(1), Worst case: Theta(numberOfElements) => Total Complexity: O(numberOfElements)
TValue Map::search(TKey c) const{
    int i = hash(c);
    if (elements[i].first == c)
    {
        return elements[i].second;
    }

    while (i != -1)
    {
        if (elements[i].first == c)
            return elements[i].second;
        i = next[i];
    }

    return NULL_TVALUE;
}

//TValue Map::remove(TKey c) {
//
//    int i = hash(c);
//    int j = -1;
//
//    while (i != -1 && elements[i] !=NULL_TELEM) {
//        j = i;
//        i = next[i];
//    }
//    int old = elements[i].second;
//    std::cout << "1: " <<  c << " " << i << " " << j << " " << elements[i].first << " " << elements[i].second << " " << next[i] << '\n';
//
//    if (i == -1 || j == -1 || elements[i] == NULL_TELEM) {
//        std::cout << "2: " << c << " " << i << " " << j << " " << elements[i].first << " " << elements[i].second << " " << next[i] << '\n';
//        return NULL_TVALUE;
//    }
//    else {
//        bool check = false;
//        do {
//            int current = next[i];
//            int previous = i;
//
//            while (current != -1 && hash(elements[current].first) != i) {
//                previous = current;
//                current = next[current];
//            }
//
//            if (current == -1) {
//                check = true;
//            }
//
//            else {
//                old = elements[i].second;
//                elements[i] = elements[current];
//                i = current;
//                j = previous;
//            }
//        } while (!check);
//        //While the element to be removed has a next element,
//        // we replace it with the next element.
//        //If the element to be removed doesn't have a next element,
//        // we replace it with the last element.
//
//        if (j != -1) {
//            next[j] = next[i];
//        }
//        old = elements[i].second;
//        elements[i] = NULL_TELEM;
//        next[i] = -1;
//        if (i < firstFree) {
//            firstFree = i;
//        }
//    }
//    --nrOfElements;
//    std::cout << "3: " << c << " " << i << " " << j << " " << elements[i].first << " " << elements[i].second << " " << next[i] << " " << old << '\n';
//    return old;
//}

//Best case: Theta(1), Worst case: Theta(numberOfElements) => Total Complexity: O(numberOfElements)
TValue Map::remove(TKey c) {
    int i = hash(c);
    int j = -1;

    // Search for the element with key 'c'
    while (i != -1 && elements[i].first != c) {
        j = i;
        i = next[i];
    }

    if (i == -1 || elements[i] == NULL_TELEM) {
        // Element not found
        return NULL_TVALUE;
    }
    else {
        if (elements[i].first == minKey || elements[i].first == maxKey) {
            // If the removed key was the minimum or maximum key, recompute the minKey and maxKey values
            minKey = 111111;
            maxKey = -111111;
            for (int i = 0; i < capacity; i++) {
                if (elements[i] != NULL_TELEM) {
                    TKey key = elements[i].first;
                    if (key < minKey)
                        minKey = key;
                    if (key > maxKey)
                        maxKey = key;
                }
            }
        }
        TValue oldValue = elements[i].second; // Store the original value

        bool check = false;
        do {
            int current = next[i];
            int previous = i;

            while (current != -1 && hash(elements[current].first) != i) {
                previous = current;
                current = next[current];
            }

            if (current == -1) {
                check = true;
            }
            else {
                elements[i] = elements[current];
                i = current;
                j = previous;
            }
        } while (!check);

        // Update the next array and remove the element
        if (j != -1) {
            next[j] = next[i];
        }
        elements[i] = NULL_TELEM;
        next[i] = -1;

        if (i < firstFree) {
            firstFree = i;
        }

        --nrOfElements;
        return oldValue; // Return the original value associated with key 'c'
    }
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
int Map::size() const {
    return nrOfElements;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
bool Map::isEmpty() const{
    return nrOfElements == 0;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
MapIterator Map::iterator() const {
	return MapIterator(*this);
}

//return the difference between the maximum and the minimum key
//if the Map is empty the range is - 1
int Map::getKeyRange() const
{
    if (isEmpty())
        return -1; // empty map, return -1
    return maxKey - minKey;
}


