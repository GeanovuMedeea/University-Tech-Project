#
# Write the implementation for A5 in this file
#

# set a 7, set b 12

# Length and elements of a longest subarray of numbers where their real part is in the form of a mountain (first the values increase, then they decrease).
# (e.g. 1-i, 2+6i, 4-67i, 90+3i, 80-7i, 76+i, 43-12i, 3)

# The length and elements of a longest alternating subsequence, when considering each number's real part (e.g., given sequence
# [1, 3, 2, 4, 10, 6, 1],[1, 3, 2, 10] is an alternating subsequence, because 1 < 3 > 2 < 10)

#
# Write below this comment
# Functions to deal with complex numbers -- list representation
# -> There should be no print or input statements in this section
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def getReal(x):
    return x[0]  # it returns a real part


def setReal(x, y):  # it sets a real part
    x[0] = y


def getImaginary(x):  # it
    return x[1]  # it returns the imaginary part


def setImaginary(x, y):
    x[1] = y  # it sets the imaginary part


def complexNumber(a=0, b=0):  # it forces a and b to be integers
    return [a, b]  # it makes these two elements a list


def readList(s, n, l1, l2):
    '''
        Input: list s of complex numbers
        n - number of elements
        l1 - the real part of the numbers
        l2 - the imagianry part of the numbers
        Effect: creates a list representing complex numbers
        '''
    t = []
    for i in range(n):
        real = int(l1[i])
        imag = int(l2[i])
        x = complexNumber()
        setReal(x, real)
        setImaginary(x, imag)
        t.append(x)
    s += t


#
# Write below this comment
# Functions to deal with complex numbers -- dict representation
# -> There should be no print or input statements in this section
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def create_dictionary(l1, l2, n):
    dict = []
    for i in range(0, n):
        real = int(l1[i])
        imaginary = int(l2[i])
        complex = {'real': real, 'imaginary': imaginary}
        dict.append(complex)
    return dict


#
# Write below this comment
# Functions that deal with subarray/subsequence properties
# -> There should be no print or input statements in this section
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#


def mountain(s):
    '''
    Input: list s of complex numbers
    and - list in which the longest subarray mountain is saved
    Effect: Prints on the console the longest subarray of "mountain" arranged numbers.
    '''
    maxx = 0
    ans = []
    for i in range(len(s) + 1):
        for j in range(i + 3, len(s) + 1):
            sub = s[i:j]
            if getReal(sub[0]) < getReal(sub[1]):
                ok = 0
                l = 2
                while l < len(sub) and ok <= 1:
                    if getReal(sub[l]) == getReal(sub[l - 1]):
                        ok = 2
                    if getReal(sub[l]) < getReal(sub[l - 1]):
                        if ok == 0:
                            ok = 1
                    if getReal(sub[l]) > getReal(sub[l - 1]):
                        if ok == 1:
                            ok = 2
                    l += 1
                if ok == 1:
                    if len(sub) > maxx:
                        maxx = len(sub)
                        ans = sub.copy()
    return ans


# Function to find the length of the longest subsequence with alternate low and high elements.
def find_longest_alternating_sequence(A):
    '''
        Input: list A of complex numbers
        L - 2 dimensional array to store temporary data
        Effect: computes the length of longest alteranting subsequence
    '''
    # base case
    if not A or len(A) <= 1:
        return len(A)

    if len(A) == 2:
        if getReal(A[0]) == getReal(A[1]):
            return 1

    # lookup table to store solutions to subproblems
    L = [[0] * 2 for r in range(len(A) + 1)]

    '''
        `L[i][0]` stores the longest alternating subsequence till `A[0…i]`
        where `A[i]` is greater than `A[i-1]`

        `L[i][1]` stores the longest alternating subsequence till `A[0…i]`
        where `A[i]` is smaller than `A[i-1]`
    '''

    # stores result
    result = 1

    # base case: the first element will always be part of LAS
    L[0][0] = L[0][1] = 1

    # fill the lookup table in a bottom-up manner
    for i in range(1, len(A)):

        # do for each element `A[j]` before `A[i]`
        for j in range(i):

            # If `A[i]` is greater than `A[j]`, update `L[i][0]`
            if getReal(A[i]) > getReal(A[j]):
                print(A[i], A[j])
                L[i][0] = max(L[i][0], L[j][1] + 1)

            # If `A[i]` is smaller than `A[j]`, update `L[i][1]`
            if getReal(A[i]) < getReal(A[j]):
                print(A[i], A[j])
                L[i][1] = max(L[i][1], L[j][0] + 1)

        # update result by taking a maximum of both values
        if result < max(L[i][0], L[i][1]):
            result = max(L[i][0], L[i][1])

    # return result
    # printtemp(L) #show the data structure used to memorise
    return result


def create_the_subsequence(s):
    '''
        Input: list s of complex numbers
        ans - list in which the longest alternating subsequence is saved, as integers
        sub - a list with which we iterate through the complex list number
        Effect: create the list of longest alternating subsequence
    '''
    if len(s) == 0:
        return []
    ans = []
    for sub in s:
        if ans == []:
            ans.append(getReal(sub))
            continue
        if ans[-1] == getReal(sub):
            continue
        if len(ans) == 1:
            ans.append(getReal(sub))
            continue
        if (getReal(sub) - ans[-1]) * (ans[-1] - ans[-2]) < 0:
            ans.append(getReal(sub))
            continue
        ans[-1] = getReal(sub)
    return ans


#
# Write below this comment
# UI section
# Write all functions that have input or print statements here
# Ideally, this section should not contain any calculations relevant to program functionalities
#


def displayList(s):
    if (len(s) == 0):
        print('The list is empty')
        return

    print('The list has ' + str(len(s)) + ' complex numbers')

    l = []

    for i in s:
        t = ''

        if getReal(i) != 0:
            t += str(getReal(i))

        if getImaginary(i) != 0:
            if t == '':
                if getImaginary(i) == 1:
                    t += 'i'
                elif getImaginary(i) == -1:
                    t += '-i'
                else:
                    t += str(getImaginary(i)) + 'i'
            elif getImaginary(i) > 0:
                if getImaginary(i) == 1:
                    t += '+i'
                else:
                    t += '+' + str(getImaginary(i)) + 'i'
            else:
                if getImaginary(i) == -1:
                    t += '-i'
                else:
                    t += str(getImaginary(i)) + 'i'

        if t == '':
            t = 0

        l.append(t)

    print(l)


def print_dictionary(dict):
    for i in range(len(dict)):
        if dict[i]['imaginary'] > 0:
            print(dict[i]['real'], '+', dict[i]['imaginary'], 'i')

        elif dict[i]['imaginary'] < 0:
            print(dict[i]['real'], '-', abs(dict[i]['imaginary']), 'i')

        elif dict[i]['imaginary'] == 0:
            print(dict[i]['real'])


def printtemp(D):
    import numpy as np
    print(np.matrix(D))


def printMenu():
    print("Menu: ")
    print("1. Print the implicit 10 value list")
    print("2. Set A, problem 7")
    print("3. Set B, problem 12")
    print("4. Exit")


def main():
    s = []
    t = [complexNumber(23, 4), complexNumber(7, 89), complexNumber(0, 7), complexNumber(2, 5), complexNumber(624, 0),
         complexNumber(10, 10), complexNumber(4, 90), complexNumber(0, 25), complexNumber(0, 12), complexNumber(12, 4)]
    while True:
        printMenu()
        choice = input('Choose an operation:')
        if choice == '1':
            displayList(t)
        if choice == '2':
            w = int(input("Do you wish to use a list(1), the program's list(2), or a dictionary(3):"))
            if w == 1:
                s.clear()
                n = input('How many complex numbers do you wish to insert: ')
                if not n.isdigit():
                    print('Not a valid number')
                    continue
                n = int(n)
                l1 = []
                l1.clear()
                l2 = []
                l2.clear()
                for i in range(n):
                    x = input('real part:')
                    l1.append(x)
                    x = input('imaginary part:')
                    l2.append(x)
                readList(s, n, l1, l2)
                displayList(s)
                ans = mountain(s)
                print('The longest mountain subarray is:')
                displayList(ans)
            if w == 2:
                displayList(t)
                ans = mountain(t)
                print('The longest mountain subarray is:')
                displayList(ans)
            if w == 3:
                s.clear()
                n = input('How many complex numbers do you wish to insert: ')
                if not n.isdigit():
                    print('Not a valid number')
                    continue
                n = int(n)
                l1 = []
                l1.clear()
                l2 = []
                l2.clear()
                for i in range(n):
                    x = input('real part:')
                    l1.append(x)
                    x = input('imaginary part:')
                    l2.append(x)
                l = create_dictionary(l1, l2, n)
                print_dictionary(l)
                readList(s, n, l1, l2)
                ans = mountain(s)
                print('The longest mountain subarray is:')
                displayList(ans)
        elif choice == '3':
            s.clear()
            w = int(input("Do you wish to use a generated list (1), the program's list(2) or a dictionary(3): "))
            if w == 1:
                n = input('How many complex numbers do you wish to insert: ')
                if not n.isdigit():
                    print('Not a valid number')
                    continue
                n = int(n)
                l1 = []
                l1.clear()
                l2 = []
                l2.clear()
                for i in range(n):
                    x = input('real part:')
                    l1.append(x)
                    x = input('imaginary part:')
                    l2.append(x)
                readList(s, n, l1, l2)
                ans = create_the_subsequence(s)
                print('The length of the longest alternating subsequence is', find_longest_alternating_sequence(s))
                print('Longest alternating subsequence is:', ans)
            if w == 2:
                ans = create_the_subsequence(t)
                print('The length of the longest alternating subsequence is', find_longest_alternating_sequence(s))
                print('Longest alternating subsequence is:', ans)
            if w == 3:
                s.clear()
                n = input('How many complex numbers do you wish to insert: ')
                if not n.isdigit():
                    print('Not a valid number')
                    continue
                n = int(n)
                l1 = []
                l1.clear()
                l2 = []
                l2.clear()
                for i in range(n):
                    x = input('real part:')
                    l1.append(x)
                    x = input('imaginary part:')
                    l2.append(x)
                l = create_dictionary(l1, l2, n)
                print_dictionary(l)
                readList(s, n, l1, l2)
                ans = create_the_subsequence(s)
                print('The length of the longest alternating subsequence is', find_longest_alternating_sequence(s))
                print('Longest alternating subsequence is:', ans)
        elif choice == '4':
            print('Exit program')
            return
        else:
            print('Option does not exist, enter another one')


if __name__ == '__main__':
    main()