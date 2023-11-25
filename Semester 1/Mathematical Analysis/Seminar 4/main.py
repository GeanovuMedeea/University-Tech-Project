l1 = []
l2 = []
l3 = []

#Geanovu Medeea Elena, Group 913

#I had tried to import a plot module (matplotlib), but i had to solve a lot of errors that appeared and in the end it messed up
#python itself, so i could not implement the visual representation of the alternative harmonic sum and its different order
#of elements

from fractions import Fraction

def normal_sum(n):
    s = 0
    for i in range(1,n+1):
        if i % 2 == 0:
            s = s - 1/i
            l1.append(s)
        else:
            s = s +1/i
            l1.append(s)
    return s

def modified_sum(n):
    s = 0
    for i in range(1,n+1,2):
        s = s + 1/i
        l2.append(s)
        if 2 * i <= n:
            s = s - 1/(2*i)
            l2.append(s)
        if 4 * i <=n:
            s = s - 1/(4*i)
            l2.append(s)
        if 4 * i + 4 <= n:
            s = s - 1/(4*(i+1))
            l2.append(s)
    return s

def normal():
    for i in range(1,12,2):
        print('+',Fraction(1,i),Fraction(-1,i+1))

def modified():
    for i in range(1,6,2):
        print('+',Fraction(1,i),Fraction(-1,2*i),Fraction(-1,4*i),Fraction(-1,4*(i+1)))

if __name__ == '__main__':
    n = int(input('The number of elements for the sum is:',))
    for i in range(n):
        l3.append(i+1)
    print('The sum with the elements in their natural order is (example):',normal_sum(n))
    print(normal())
    print('The sum with changed order of elements is (example):',modified_sum(n))
    print(modified())
#sum(1 to  inf) (-1)^(n+1)/n = ln2
