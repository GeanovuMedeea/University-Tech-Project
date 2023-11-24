import timeit

def print_options():
    print('Press 1 to generate a list of n random natural numbers. Generated numbers must be between 0 and 100.')
    print('Press 2 to sort the list using Cocktail sort')
    print('Press 3 to sort the list using Shell sort')
    print('Press 4 to view the Complexity and runtime of the sorting algorithms for Best Case Scenario')
    print('Press 5 to view the Complexity and runtime of the sorting algorithms for Average Case Scenario')
    print('Press 6 to view the Complexity and runtime of the sorting algorithms for Worst Case Scenario')
    print('Press 0 to exit program')

import random

def create_list(l):
    l.clear()
    n = int(input('Number of elements for the list:'))
    for i in range(n):
        l.append(random.randint(0, 101))

    print(l)

def cocktail_sort_normal(list):
    l = list.copy()
    for i in range(len(l) // 2):
        swap = False
        for j in range(1 + i, len(l) - i): # test whether the two elements are in the wrong order
            if l[j] < l[j - 1]:
                l[j], l[j - 1] = l[j - 1], l[j] # let the two elements change places
                swap = True

        if not swap: # we can exit the outer loop here if no swaps occurred.
            break
        swap = False

        for j in range(len(l) - i - 1, i, -1): #sort the remaining half of the lsit
            if l[j] < l[j - 1]:
                l[j], l[j - 1] = l[j - 1], l[j] #swap elements to be in the correct ascending order
                swap = True

        if not swap:
            break
    print(l)

def shell_sort_normal(list):
    l = list.copy()
    # Start with a big gap, then reduce the gap
    h = len(l) // 2

    # Do a gapped insertion sort for this gap size.
    # The first gap elements a[0..gap-1] are already in gapped
    # order keep adding one more element until the entire array
    # is gap sorted

    while h > 0:
        for i in range(h, len(l)):
            t = l[i] # add l[i] to the elements that have been gap sorted and save it in a temporary variable
            j = i # location for l[i] is found

            while j >= h and l[j - h] > t: # shift earlier gap-sorted elements up until the correct location
                l[j] = l[j - h]
                j = j - h

            l[j] = t # put temporary variable (the original l[i]) in its correct location

        h = h // 2 #we halve the gap for searching

    print(l)

'''Best Case Complexity - It occurs when there is no sorting required, i.e., the array is already sorted.
The best-case time complexity of cocktail sort is O(n).

Average Case Complexity - It occurs when the array elements are in jumbled order that is not properly 
ascending and not properly descending. The average case time complexity of cocktail sort is O(n2).

Worst Case Complexity - It occurs when the array elements are required to be sorted in reverse order.
That means suppose you have to sort the array elements in ascending order, but its elements are in descending order.
The worst-case time complexity of cocktail sort is O(n2).'''

def cocktail_sort(list):
    l = list.copy()
    for i in range(len(l) // 2):
        swap = False
        for j in range(1 + i, len(l) - i): # test whether the two elements are in the wrong order
            if l[j] < l[j - 1]:
                l[j], l[j - 1] = l[j - 1], l[j] # let the two elements change places
                swap = True

        if not swap: # we can exit the outer loop here if no swaps occurred.
            break
        swap = False

        for j in range(len(l) - i - 1, i, -1): #sort the remaining half of the lsit
            if l[j] < l[j - 1]:
                l[j], l[j - 1] = l[j - 1], l[j] #swap elements to be in the correct ascending order
                swap = True

        if not swap:
            break

'''Time Complexity: Time complexity of this implementation of Shell sort is O(n*log(n)).
 There are many other ways to reduce gaps which leads to better time complexity, but for this problem we divide the gap by half each time.

Best Case Complexity
When the given array list is already sorted the total count of comparisons of each interval is equal to the size of the given array.
So best case complexity is Ω(n*log(n))

Average Case Complexity
The shell sort Average Case Complexity depends on the interval selected by the programmer. 
θ(n log(n)2). The Average Case Complexity in our case is: O(n*log n)

Worst Case Complexity
The worst-case complexity for shell sort is  O(n2). When all the elements on the even positions are greater than odd positions then it is worst case'''

def shell_sort(list):
    l = list.copy()
    # Start with a big gap, then reduce the gap
    h = len(l) // 2

    # Do a gapped insertion sort for this gap size.
    # The first gap elements a[0..gap-1] are already in gapped
    # order keep adding one more element until the entire array
    # is gap sorted

    while h > 0:
        for i in range(h, len(l)):
            t = l[i] # add l[i] to the elements that have been gap sorted and save it in a temporary variable
            j = i # location for l[i] is found

            while j >= h and l[j - h] > t: # shift earlier gap-sorted elements up until the correct location
                l[j] = l[j - h]
                j = j - h

            l[j] = t #put temporary variable (the original l[i]) in its correct location

        h = h // 2 #we halve the gap for searching

#create the lists for all cases

l1 = [] #10 unsorted
l2 = [] #20 unsorted
l3 = [] #40 unsorted
l4 = [] #80 unsorted
l5 = [] #160 unsorted


for i in range(10):
    l1.append(random.randint(0, 101))
for i in range(20):
    l2.append(random.randint(0, 101))
for i in range(40):
    l3.append(random.randint(0, 101))
for i in range(80):
    l4.append(random.randint(0, 101))
for i in range(160):
    l5.append(random.randint(0, 101))


def transmit_lists(n, m):
    l = []
    if m == 1: #10 elements
        l = l1.copy()
    if m == 2: #20 elements
        l = l2.copy()
    if m == 3: #40 elements
        l = l3.copy()
    if m == 4: #80 elements
        l = l4.copy()
    if m == 5: #160 elements
        l = l5.copy()

    if n == 1: #best case
        l.sort()
    if n == 3: #worst case
        l.sort(reverse=True)

    return l

# compute cocktail sorts time for best case

def cocktail_time_best():
    print('Execution time for best case for Cocktail sort:')
    #print('Best case list 10 elements:')
    #print(transmit_lists(1,1))
    cocktail_time_10b()
    #print('Best case list 20 elements:')
    #print(transmit_lists(1, 2))
    cocktail_time_20b()
    #print('Best case list 40 elements:')
    #print(transmit_lists(1, 3))
    cocktail_time_40b()
    #print('Best case list 80 elements:')
    #print(transmit_lists(1, 4))
    cocktail_time_80b()
    #print('Best case list 160 elements:')
    #print(transmit_lists(1, 5))
    cocktail_time_160b()

def cocktail_time_10b():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,1)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 10 elements: {}'.format(min(times)))

def cocktail_time_20b():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,2)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 20 elements: {}'.format(min(times)))

def cocktail_time_40b():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,3)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 40 elements: {}'.format(min(times)))

def cocktail_time_80b():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,4)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 80 elements: {}'.format(min(times)))

def cocktail_time_160b():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,5)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 160 elements: {}'.format(min(times)))

# compute cocktail sorts time for average case

def cocktail_time_average():
    print('Execution time for average case for Cocktail sort:')
    #print('Average case list 10 elements:')
    #print(transmit_lists(2, 1))
    cocktail_time_10a()
    #print('Average case list 20 elements:')
    #print(transmit_lists(2, 2))
    cocktail_time_20a()
    #print('Average case list 40 elements:')
    #print(transmit_lists(2, 3))
    cocktail_time_40a()
    #print('Average case list 80 elements:')
    #print(transmit_lists(2, 4))
    cocktail_time_80a()
    #print('Average case list 160 elements:')
    #print(transmit_lists(2, 5))
    cocktail_time_160a()

def cocktail_time_10a():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,1)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 10 elements: {}'.format(min(times)))

def cocktail_time_20a():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,2)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 20 elements: {}'.format(min(times)))

def cocktail_time_40a():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,3)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 40 elements: {}'.format(min(times)))

def cocktail_time_80a():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,4)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 80 elements: {}'.format(min(times)))

def cocktail_time_160a():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,5)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 160 elements: {}'.format(min(times)))

#compute cocktail sorts time for worst case

def cocktail_time_worst():
    print('Execution time for worst case for Cocktail sort:')
    #print('Average case list 10 elements:')
    #print(transmit_lists(3, 1))
    cocktail_time_10w()
    #print('Average case list 20 elements:')
    #print(transmit_lists(3, 2))
    cocktail_time_20w()
    #print('Average case list 40 elements:')
    #print(transmit_lists(3, 3))
    cocktail_time_40w()
    #print('Average case list 80 elements:')
    #print(transmit_lists(3, 4))
    cocktail_time_80w()
    #print('Average case list 160 elements:')
    #print(transmit_lists(3, 5))
    cocktail_time_160w()

def cocktail_time_10w():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,1)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 10 elements: {}'.format(min(times)))

def cocktail_time_20w():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,2)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 20 elements: {}'.format(min(times)))

def cocktail_time_40w():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,3)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 40 elements: {}'.format(min(times)))

def cocktail_time_80w():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,4)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 80 elements: {}'.format(min(times)))

def cocktail_time_160w():
    SETUP_CODE = '''
import random
from __main__ import cocktail_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,5)
list = l.copy()
'''

    TEST_CODE = '''
cocktail_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Cocktail sort time for 160 elements: {}'.format(min(times)))

#compute shell sorts time for best case

def shell_time_best():
    print('Execution time for best case Shell sort:')
    shell_time_10b()
    shell_time_20b()
    shell_time_40b()
    shell_time_80b()
    shell_time_160b()

def shell_time_10b():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,1)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 10 elements: {}'.format(min(times)))

def shell_time_20b():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,2)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 20 elements: {}'.format(min(times)))

def shell_time_40b():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,3)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 40 elements: {}'.format(min(times)))

def shell_time_80b():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,4)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 80 elements: {}'.format(min(times)))

def shell_time_160b():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(1,5)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 160 elements: {}'.format(min(times)))

#compute shell sorts time for average case

def shell_time_average():
    print('Execution time for shell sort worst case:')
    shell_time_10a()
    shell_time_20a()
    shell_time_40a()
    shell_time_80a()
    shell_time_160a()

def shell_time_10a():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,1)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 10 elements: {}'.format(min(times)))

def shell_time_20a():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,2)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 20 elements: {}'.format(min(times)))

def shell_time_40a():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,3)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 40 elements: {}'.format(min(times)))

def shell_time_80a():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,4)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 80 elements: {}'.format(min(times)))

def shell_time_160a():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(2,5)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 160 elements: {}'.format(min(times)))

#compute shell sorts time for worst case

def shell_time_worst():
    print('Execution time for worst case for Shell sort:')
    shell_time_10w()
    shell_time_20w()
    shell_time_40w()
    shell_time_80w()
    shell_time_160w()

def shell_time_10w():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,1)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 10 elements: {}'.format(min(times)))

def shell_time_20w():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,2)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 20 elements: {}'.format(min(times)))

def shell_time_40w():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,3)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 40 elements: {}'.format(min(times)))

def shell_time_80w():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,4)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 80 elements: {}'.format(min(times)))

def shell_time_160w():
    SETUP_CODE = '''
import random
from __main__ import shell_sort
from __main__ import transmit_lists
list = []
l = []
l = transmit_lists(3,5)
list = l.copy()
'''

    TEST_CODE = '''
shell_sort(list)'''

    # timeit.repeat statement
    times = timeit.repeat(setup=SETUP_CODE,
                          stmt=TEST_CODE,
                          repeat=3,
                          number=100)

    # printing minimum exec. time
    print('Shell sort time for 160 elements: {}'.format(min(times)))

def best_case(list):
    print('Time complexity for best case:')
    cocktail_time_best()
    shell_time_best()

def average_case(list):
    print('Time complexity for average case:')
    cocktail_time_average()
    shell_time_average()

def worst_case(list):
    print('Time complexity for worst case:')
    cocktail_time_worst()
    shell_time_worst()

def run_menu():

    commands = {1:create_list, 2:cocktail_sort_normal, 3:shell_sort_normal, 4:best_case, 5:average_case, 6:worst_case}
    list = []
    while True:
        print_options()
        option = input('Option:')
        if option == '0':
            break
        option = int(option)
        commands[option](list) #commands[options]() turns to function()


if __name__ == '__main__':
    run_menu()