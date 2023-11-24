#pragma once
#include "Map.h"
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;

class MapIterator
{
	//DO NOT CHANGE THIS PART
	friend class Map;
private:
	const Map& map;
	explicit MapIterator(const Map& map);
	int current;
	void displacement();
public:
	void first();
	void next();
	TElem getCurrent();
	bool valid() const;
};


