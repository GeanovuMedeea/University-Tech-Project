#pragma once
#include <utility>
//DO NOT INCLUDE MAPITERATOR


//DO NOT CHANGE THIS PART
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -111111
#define NULL_TELEM std::pair<TKey, TValue>(-111111, -111111) //here it was given with error/ std was necessary
class MapIterator;


class Map {
	//DO NOT CHANGE THIS PART
	friend class MapIterator;

	private:
		struct SLLANode {
			TElem info;
			int next;

			SLLANode() {
				this->info = NULL_TELEM;
				this->next = 0;
			}
			//Theta(1)
			SLLANode(TElem info, int next) {
				this->info = move(info);
				this->next = next;
			}
			//Theta(1)
		};

		//can adapt it without struct
		struct SLLA {
			SLLANode* list;
			int head, tail;
			int capacity, size;
			int first_empty;

			SLLA() {
				this->capacity = 1;
				this->size = 1;
				this->list = new SLLANode[this->capacity];
				this->list[0] = SLLANode(NULL_TELEM, -1);
				this->first_empty = 0;
				this->head = -1;
				this->tail = -1;
			}//Theta(1)

			SLLANode& operator[](int pos)
			{
				return this->list[pos];
			}
			//Theta(1)
		};

		SLLA elems;
		int length;

	public:

	// implicit constructor
	Map();

	// adds a pair (key,value) to the map
	//if the key already exists in the map, then the value associated to the key is replaced by the new value and the old value is returned
	//if the key does not exist, a new pair is added and the value null is returned
	TValue add(TKey c, TValue v);

	//searches for the key and returns the value associated with the key if the map contains the key or null: NULL_TVALUE otherwise
	TValue search(TKey c) const;

	//removes a key from the map and returns the value associated with the key if the key existed ot null: NULL_TVALUE otherwise
	TValue remove(TKey c);

	//returns the number of pairs (key,value) from the map
	int size() const;

	//checks whether the map is empty or not
	
	bool isEmpty() const;

	//returns an iterator for the map
	MapIterator iterator() const;

	// destructor
	~Map();

};



