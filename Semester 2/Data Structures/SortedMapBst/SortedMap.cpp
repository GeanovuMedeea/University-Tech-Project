#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>
#include <iostream>
using namespace std;

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
SortedMap::SortedMap(Relation r) {
    this->root = nullptr;
    this->comp = r;
    this->number_of_elements = 0;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void SortedMap::rotleft(SortedMap::treeNode*& n) {
    treeNode* t = n->left;

    if (t->right != nullptr)
        t->right->father = n;
    t->father = n->father;
    n->father = t;

    n->left = t->right;
    t->right = n;
    n->father = t;

    n->left = t->right;
    t->right = n;
    n = t;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void SortedMap::rotright(SortedMap::treeNode*& n) {
    treeNode* t = n->right;

    if (t->left != nullptr)
        t->left->father = n;
    t->father = n->father;
    n->father = t;

    n->right = t->left;
    t->left = n;
    n = t;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void SortedMap::balance(SortedMap::treeNode*& n) {
    if (n->left != nullptr && n->left->priority > n->priority)
        this->rotleft(n);
    else if (n->right != nullptr && n->right->priority > n->priority)
        this->rotright(n);
}

//Best case: Theta(1), Worst case: Theta(nrOfElements) => Total Complexity: O(log(numberOfElements))
TValue SortedMap::add(SortedMap::treeNode*& n, TKey e, TValue v, SortedMap::treeNode* father) {
    if (n == nullptr) {
        int priority = e;
        n = new treeNode(e, v, priority, nullptr, nullptr, father);
        return NULL_TVALUE;
    }
    if (e == n->key) {
        //std::cout << this->root->key << " " << this->root->value << '\n';

        TValue old = n->value;
        n->value = v;
        return old;
    }
    //std::cout << this->root->key << " " << this->root->value << '\n';
    if (this->comp(e, n->key))
        return this->add(n->left, e, v, n);
    else
        return this->add(n->right, e, v, n);
    this->balance(n);
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
TValue SortedMap::add(TKey e, TValue v) {
    TValue old;
    old = add(this->root, e, v, nullptr);
    if(old==NULL_TVALUE)
        ++this->number_of_elements;
    return old;
}

//Best case: Theta(1), Worst case: Theta(nrOfElements) => Total Complexity: O(log(numberOfElements))
TValue SortedMap::remove(SortedMap::treeNode*& n, TKey e) {
    if (n == nullptr)
        return NULL_TVALUE;
    if (this->comp(e, n->key) && e != n->key)
        return this->remove(n->left, e);
    if (this->comp(n->key, e) && e != n->key)
        return this->remove(n->right, e);

    TValue oldValue = n->value;

    if (n->left == nullptr && n->right == nullptr) {
        delete n;
        n = nullptr;
    }
    else if (n->left != nullptr && n->right == nullptr) {
        treeNode* temp = n;
        n = n->left;
        n->father = temp->father;
        delete temp;
    }
    else if (n->left == nullptr && n->right != nullptr) {
        treeNode* temp = n;
        n = n->right;
        n->father = temp->father;
        delete temp;
    }
    //else {
    //    int l = n->left->priority;
    //    int r = n->right->priority;
    //    if (l > r) {
    //        rotleft(n);
    //        return this->remove(n, e);
    //    }
    //    else {
    //        rotright(n);
    //        return this->remove(n, e);
    //    }
    //}
    return oldValue;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
TValue SortedMap::remove(TKey e) {
    TValue ret = this->remove(this->root, e);
    if (ret!= NULL_TVALUE)
        --this->number_of_elements;
    return ret;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
SortedMap::treeNode* SortedMap::get_node(SortedMap::treeNode* n, TKey e) const {
    if (n == nullptr)
        return nullptr;
    if (e == n->key)
        return n;
    if (this->comp(e, n->key))
        return this->get_node(n->left, e);
    return this->get_node(n->right, e);
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
TValue SortedMap::search(TKey elem) const {
    treeNode* temp;
    temp = this->get_node(this->root, elem);
    if (temp == nullptr)
        return NULL_TVALUE;
    return temp->value;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
int SortedMap::size() const {
    return this->number_of_elements;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
bool SortedMap::isEmpty() const {
    return this->number_of_elements == 0;
}

//return the difference between the maximum and the minimum key
//if the Map is empty the range is - 1
int SortedMap::getKeyRange() const {
    if (isEmpty())
        return -1;

    treeNode* current = this->root;

    while (current->left != nullptr)
    {
        current = current->left;
    }
    TKey minKey = current->key;

    current = this->root;

    while (current->right != nullptr)
    {
        current = current->right;
    }
    TKey maxKey = current->key;

    return maxKey - minKey;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)

SMIterator SortedMap::iterator() const {
    return SMIterator(*this);
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void SortedMap::destroy(SortedMap::treeNode*& n) {
    if (n == nullptr)
        return;
    destroy(n->left);
    destroy(n->right);
    delete n;
    n = nullptr;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
SortedMap::~SortedMap() {
    this->destroy(this->root);
}