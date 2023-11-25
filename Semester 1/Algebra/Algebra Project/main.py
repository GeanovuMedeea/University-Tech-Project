import math
import copy

def ToReducedRowEchelonForm(M):
    if not M: return
    lead = 0
    rowCount = len(M)
    columnCount = len(M[0])
    for r in range(rowCount):
        if lead >= columnCount:
            return
        i = r
        while M[i][lead] == 0:
            i += 1
            if i == rowCount:
                i = r
                lead += 1
                if columnCount == lead:
                    return
        M[i],M[r] = M[r],M[i]
        lv = M[r][lead]
        M[r] = [ mrx / float(lv) for mrx in M[r]]
        for i in range(rowCount):
            if i != r:
                lv = M[i][lead]
                M[i] = [ iv - lv*rv for rv,iv in zip(M[r],M[i])]
        lead += 1

def create_binary(x):
    num=x**2
    n = int(math.log(num,2))
    x = [0,1]

    for i in range(1,int(n)+1):
        for j in range(0,2**i):
            x.append(1 + x[j])
            x[j]= 0 + x[j]

    return(x[0:num])


def matrix_compare(mtx1, mtx2):
    for i in mtx1:
        index = mtx2.index(i)
        if i != mtx2[index]:
            return False
    return True


def GenerateMatrix(x, y):
    binary = create_binary(y)
    matrix = []
    if x == 2:
        for i in binary:
            matrix.append(i)
            for j in binary:
                if i == j:
                    continue
                matrix.append(j)
                matrix_com = copy.deepcopy(matrix)
                ToReducedRowEchelonForm(matrix_com)
                if matrix_compare(matrix, matrix_com) == True:
                    print('RREF matrix: ')
                    for rw in matrix:
                        print(', '.join((str(rv) for rv in rw)))


GenerateMatrix(2,3)





# mtx = [
#    [ 1, 2, -1, -4],
#    [ 2, 3, -1, -11],
#    [-2, 0, -3, 22],]
#
# print('Initial matrix: ')
# for rw in mtx:
#     print(', '.join((str(rv) for rv in rw)))
#
# ToReducedRowEchelonForm( mtx )
#
# print('RREF matrix: ')
# for rw in mtx:
#     print(', '.join((str(rv) for rv in rw)))
