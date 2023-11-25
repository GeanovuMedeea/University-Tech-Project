#include <iostream>

#include "ShortTest.h"
#include "ExtendedTest.h"

int main() {
    testAll();
    testAllExtended();
    getchar();
    std::cout << "Finished SMM Tests!" << std::endl;
}