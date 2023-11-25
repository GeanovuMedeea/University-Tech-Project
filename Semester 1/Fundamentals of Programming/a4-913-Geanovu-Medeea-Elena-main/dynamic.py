'''Determine the longest common subsequence of two given sequences. Subsequence elements are not required to occupy
consecutive positions. For example, if X = "MNPNQMN" and Y = "NQPMNM", the longest common subsequence has length 4,
and can be one of "NQMN", "NPMN" or "NPNM". Determine and display both the length of the longest common subsequence
as well as at least one such subsequence.'''

'''def lcs1(X, Y, m, n):
    if m == 0 or n == 0:
        return 0
    else:
        if X[m - 1] == Y[n - 1]:
            return 1 + lcs1(X, Y, m - 1, n - 1)
        else:
            return max(lcs1(X, Y, m, n - 1), lcs1(X, Y, m - 1, n))'''


# Dynamic Programming implementation of LCS problem
#time complexity O(n*m)

def printtemp(L):
    import numpy as np
    print(np.matrix(L))

def lcs2(X, Y):
    # find the length of the strings
    m = len(X)
    n = len(Y)

    # declaring the array for storing the dp values
    L = [[0]*(n + 1) for i in range(m + 1)]

    """Following steps build L[m + 1][n + 1] in bottom up fashion
    Note: L[i][j] contains length of LCS of X[0..i-1]
    and Y[0..j-1]"""
    for i in range(m + 1):
        for j in range(n + 1):
            if i == 0 or j == 0:
                L[i][j] = 0
            else:
                if X[i - 1] == Y[j - 1]:
                    L[i][j] = L[i - 1][j - 1] + 1
                else:
                    L[i][j] = max(L[i - 1][j], L[i][j - 1])

    # Create a string variable to store the lcs string
    lcs = ""

    # Start from the right-most-bottom-most corner and
    # one by one store characters in lcs[]
    i = m
    j = n
    while i > 0 and j > 0:
        # If current character in X[] and Y[] are same, then
        # current character is part of LCS
        if X[i - 1] == Y[j - 1]:
            lcs += X[i - 1]
            i -= 1
            j -= 1
        # If not same, then find the larger of two and
        # go in the direction of larger value
        else:
            if L[i - 1][j] > L[i][j - 1]:
                i -= 1
            else:
                j -= 1

    print('The data structure (dynamic):')
    printtemp(L)
    # LCS needs to be reversed since we went through the table L from bottom up
    lcs = lcs[::-1]
    # L[m][n] contains the length of LCS of X[0..n-1] & Y[0..m-1]
    print('Length of LCS is (dynamic):', L[m][n])
    print("LCS of " + X + " and " + Y + " is (dynamic): " + lcs)

#naive implementation of lcs
#Time Complexity: O(2^(m+n))

def lcs(s1, s2, n, m):
    if n == 0 or m == 0:
        return ""
    if s1[n - 1] == s2[m - 1]:
        return lcs(s1, s2, n - 1, m - 1) + s1[n - 1]
    return max(lcs(s1, s2, n - 1, m), lcs(s1, s2, n, m - 1))

if __name__ == '__main__':
    X = input('The first sequence:')
    Y = input('The second sequence:')
    print('First sequence:', X, 'Second sequence:', Y)
    print('Length of LCS (naive):', len(lcs(X, Y, len(X), len(Y))))
    print("LCS of " + X + " and " + Y + " is: " + lcs(X,Y,len(X),len(Y)))
    lcs2(X,Y)