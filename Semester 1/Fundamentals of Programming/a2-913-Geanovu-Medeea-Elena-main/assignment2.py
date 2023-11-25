import random


def print_options():
    print('Press 1 to generate a list of n random natural numbers. Generated numbers must be between 0 and 100.')
    print('Press 2 to sort the list using Cocktail sort')
    print('Press 3 to sort the list using Shell sort')
    print('Press 0 to exit program')


def create_list(l, step):
    l.clear()
    k = 0
    n = int(input('Number of elements:'))
    for i in range(n):
        if(k == step):
            print(l)
            k = 0
        l.append(random.randint(0, 101))
        k = k + 1

    print(l)

def cocktail_sort(list, step):
    l = list.copy()
    k = 0
    for i in range(len(l) // 2):
        swap = False
        for j in range(1 + i, len(l) - i): # test whether the two elements are in the wrong order
            if l[j] < l[j - 1]:
                if(k == step): # print the list reaching k number of steps
                    print(l)
                    k = 0
                k = k + 1 #increment the number of steps
                l[j], l[j - 1] = l[j - 1], l[j] # let the two elements change places
                swap = True

        if not swap: # we can exit the outer loop here if no swaps occurred.
            break
        swap = False

        for j in range(len(l) - i - 1, i, -1): #sort the remaining half of the lsit
            if l[j] < l[j - 1]:
                if k == step: # print the list reaching k number of steps
                    print(l)
                    k = 0
                k = k + 1 #increment the number of steps
                l[j], l[j - 1] = l[j - 1], l[j] #swap elements to be in the correct ascending order
                swap = True

        if not swap:
            break

    print(l)

def shell_sort(list, step):
    l = list.copy()
    # Start with a big gap, then reduce the gap
    h = len(l) // 2
    k = 0

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
                k = k + 1 #incrementing the counter for step number

            l[j] = t # put temporary variable (the original l[i]) in its correct location

            if k == step:
                print(l)
                k = 0

        h = h // 2 #

    print(l)


def run_menu():

    commands = {1:create_list, 2:cocktail_sort, 3:shell_sort}
    list = []
    while True:
        print_options()
        option = input('Option:')
        if option == '0':
            break
        step = int(input('Number of steps:'))
        option = int(option)
        commands[option](list, step) #commands[options]() turns to function()


if __name__ == '__main__':
    run_menu()
