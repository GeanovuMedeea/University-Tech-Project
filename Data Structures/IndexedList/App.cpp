#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"

using namespace std;

//ADT list (interface with TPozition = Integer - IndexedList) using DLL

int main(){
    testAll();
    testAllExtended();
    cout<<"\nFinished LI Tests!"<<endl;
    getchar();
}