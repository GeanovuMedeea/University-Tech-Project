#pragma once
#include "SortedMap.h"

//DO NOT CHANGE THIS PART

class SortedMap;

class SMIterator
{
    friend class SortedMap;

private:
    const SortedMap& map;
    explicit SMIterator(const SortedMap& b);

    SortedMap::treeNode* node, * first_node;

public:
    TElem getCurrent(); //Theta(1)
    bool valid();   //Theta(1)
    void next();    //total O(n) but average Theta(1)
    void first();   //Theta(1)
};
