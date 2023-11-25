# Solve the problem from the second set here
#problem nr 7
import math


def prime(n):
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    for i in range(3, int(math.sqrt(n)) + 1):
        if n % i == 0:
            return False
    return True


def twinprimes(n):
    a = n + 2
    ok = 0
    while ok != 1:
        b = a - 1
        a = b + 2
        if prime(a):
            if prime(b):
                print(b, ' ', a)
                ok = 1
    return


if __name__ == '__main__':
    n = int(input('What is the number?'))
    print('The smallest twin prime numbers larger than n are: ')
    twinprimes(n)