#include "SMIterator.h"
#include "SortedMap.h"
#include <exception>

using namespace std;

//Best case: Theta(1), Worst case: Theta(numberOfElements) => Total Complexity: O(numberOfElements)
SMIterator::SMIterator(const SortedMap& b) : map(b) {
    this->first_node = this->map.root;
    if (this->first_node != nullptr)
        while (this->first_node->left != nullptr)
            this->first_node = this->first_node->left;
    this->node = this->first_node;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
TElem SMIterator::getCurrent() {
    if (!this->valid())
        throw std::exception();
    return std::make_pair(this->node->key, this->node->value);
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
bool SMIterator::valid() {
    return this->node != nullptr;
}

//Best case: Theta(1), Worst case: Theta(numberOfElements) => Total Complexity: O(numberOfElements)
void SMIterator::next() {
    if (!this->valid())
        throw std::exception();
    if (this->node->right != nullptr) {
        this->node = this->node->right;
        while (this->node->left != nullptr)
            this->node = this->node->left;
        return;
    }
    while (this->node->father != nullptr && this->node->father->right == this->node)
        this->node = this->node->father;
    this->node = this->node->father;
}

//Best case: Theta(1), Worst case: Theta(1) => Total Complexity: O(1)
void SMIterator::first() {
    this->node = this->first_node;
}
