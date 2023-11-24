# Solve the problem from the third set here
#problem nr 13
def primefactors(m, n, k):

    ok = 0
    if n % 2 == 0:
        print(2)
    while n % 2 == 0 and k < m:
        ok = 1
        n = n//2

    k = k + ok
    if(k == m):
        print('The n-th factor is: 2')
        return k

    f = 3

    while n > 1 and k < m:
        ok = 0
        if n % f == 0:
            print(f)
        while n % f == 0 and k < m:
            n = n // f
            ok = 1

        k = k + ok

        f = f + 2

    if (k == m):
        print('The n-th factor is: ', f - 2)

    return k

def list(n):
    l = 2
    k = 1
    while k < n:
        k = primefactors(n, l, k)
        l = l + 1


if __name__ == '__main__':
    n = int(input('The number is:'))
    if n > 0:
        print('The list of n elements of the sequence of the numbers written as their prime factors is: ')
        print(1)
        list(n)
    else:print('0 cannot be written as a product of prime divisors')