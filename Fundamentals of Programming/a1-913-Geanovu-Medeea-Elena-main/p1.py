# Solve the problem from the first set here
#problem nr 4
def extractnumbers(n):
    l1 = []
    while n > 0:
        x = n % 10
        l1.append(x)
        n = n//10
    return l1

def sort(l2):
    for i in range(len(l2) - 1):
        for j in range(i, len(l2)):
            if l2[i] < l2[j]:
                l2[i], l2[j] = l2[j], l2[i]
    return l2

def largnumber(l3):
    x = 0
    for i in range(len(l3)):
        x = x * 10 + l3[i]
    return x

if __name__ == '__main__':
    n = int(input('What is the number?'))
    l = extractnumbers(n)
    l = sort(l)
    x = largnumber(l)
    print('The largest number with the digits of the input value is: ', x)