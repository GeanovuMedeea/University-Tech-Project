#pragma once
#include "Map.h"
class MapIterator
{
	//DO NOT CHANGE THIS PART
	friend class Map;

private:
	const Map& map;  // Reference to the map
	int pos;     // Index of the current node

	MapIterator(const Map& m);  // Private constructor
public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;
	void jumpBackward(int k);
};


