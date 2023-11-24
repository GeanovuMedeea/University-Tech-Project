#pragma once
//DO NOT INCLUDE SMIterator

//DO NOT CHANGE THIS PART
typedef int TKey;
typedef int TValue;
#include <utility>
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -111111
#define NULL_TPAIR pair<TKey, TValue>(-111111, -111111);

typedef bool(*Relation)(TKey, TKey);

class SMIterator;

class SortedMap {
    friend class SMIterator;

private:
    struct treeNode {
        TKey key;
        TValue value;
        int priority;
        treeNode* left, * right;
        treeNode* father;

        treeNode(TKey _key, TValue _value, int _priority, treeNode* _left, treeNode* _right, treeNode* _father) {  //Theta(1)
            this->key = _key;
            this->value = _value;
            this->priority = _priority;
            this->left = _left;
            this->right = _right;
            this->father = _father;
        }
    };

    treeNode* root;
    Relation comp;
    int number_of_elements;

    static void rotleft(treeNode*& n); //Theta(1)

    static void rotright(treeNode*& n);    //Theta(1)

    void balance(treeNode*& n);    //Theta(1)

    TValue add(treeNode*& n, TKey e, TValue v, treeNode* father);    //total O(n) but Theta(logn) on average

    TValue remove(treeNode*& n, TKey e);    //total O(n) but Theta(logn) on average

    treeNode* get_node(treeNode* n, TKey e) const; //total O(n) but Theta(logn) on average

    static void destroy(treeNode*& n); //Theta(n) where n is the number of distinct elements

public:
    //constructor
    explicit SortedMap(Relation r);

    //adds an element to the sorted map
    TValue add(TKey e, TValue v);  //total O(n) but Theta(logn) on average

    //removes one occurrence of an element from a sorted map
    //returns true if an element was removed, false otherwise (if e was not part of the sorted map)
    TValue remove(TKey e);   //total O(n) but Theta(logn) on average

    //checks if an element appears is the sorted map
    [[nodiscard]] TValue search(TKey e) const;   //total O(n) but Theta(logn) on average

    //returns the number of elements from the sorted map
    [[nodiscard]] int size() const; //Theta(1)

    //returns an iterator for this sorted map
    [[nodiscard]] SMIterator iterator() const;   //total O(n) but Theta(logn) on average

    //checks if the sorted map is empty
    [[nodiscard]] bool isEmpty() const; //Theta(1)

    int getKeyRange() const;

    //destructor
    ~SortedMap();    //Theta(n) where n is the number of distinct elements
};


