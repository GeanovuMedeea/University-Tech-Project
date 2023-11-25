'''1. A number of n coins are given, with values of a1, ..., an and a value s.
Display all payment modalities for the sum s. If no payment modality exists print a message.

1. Iterative
2. Recursive

'''
# Returns the nr of ways we can sum the available coins to get the sum

vis = [] #to avoid going back to same element in backtracking if it is taken already in current stack considered for solution
c = False #if there are solutions in backtracking
c2 = False #if there are solutions in iterative
sol = [] #for no repetition in backtracking

#Version with limited nr of coins

def valid(x,k,n,s,st,sol):
    if x <= s and k < n:
        ss = sum(st)
        if ss + x <= s:
            st2 = st.copy()  # for no repetition
            st2.append(x)
            st2.sort()
            if st2 in sol:
                return False  # for no repetition
            return True
    return False


def solution(s,st): #O(k)
    ss = sum(st)
    if ss == s:
        return True
    return False


def show():
    print(st) #O(1)


def back(coin_list, n, s, k):
    for i in range(k,n):
        if vis[i] == 0:
            x = coin_list[i]
            if valid(x,k,n,s,st,sol):
                st.append(x)
                vis[i] = 1
                if solution(s,st):
                    show()
                    ss = st.copy() #for no repetition
                    sol.append(ss) #for no repetition
                    global c
                    c = True
                else:
                    back(coin_list, n, s, k + 1)
            vis[i] = 0
            if len(st) > 0:
                st.pop()

'''
#EXAMPLE ITERATIVE BACKTRACKING  
def back_iter(n, l):
    x = [initial_value()]
    while' len(x) > 0:
        el = next_elem(l, n)
        while el is 'not None:
            x[-1] = el
            if consistent(x, n):
                if is_solution(x, n):
                    output_solution(x)
                else:
                    x.append(initial_value())
                    break
            el = next_elem(x, n)
        if el is None: x = x[:-1]
'''

solutions = []

def is_consistent(cur_sol,s):
    if sum(cur_sol) <= s:
        return True
    return False

def is_solution(cur_sol,s):
    if sum(cur_sol) == s:
        return True
    return False

def bkt_iterative(n: int, values: list, s: int) -> None:
    """
    Solves the given problem via iterative backtracking, displaying the solution whenever any is found.

    Input: n - int, the number of given coins;
           values - list, the values of each coin;
           s - int, the sum we want to achieve.
    Output: None.
    """
    k = 0
    cur_sol = [-1] * n #we initialize the array in which we try to find the solutions with the initial values of -1 for all length n
    while k > -1:
        if k == n:
            if is_solution(cur_sol, s):
                global solutions
                global c2
                c2 = True
                new_cur_sol = []
                for nr in cur_sol:
                    if nr != 0:
                        new_cur_sol.append(nr)
                if new_cur_sol not in solutions:
                    print(new_cur_sol)
                    solutions.append(new_cur_sol[:])
            k -= 1
            continue

        if cur_sol[k] == -1:
            cur_sol[k] = 0
        elif cur_sol[k] == 0:
            cur_sol[k] = values[k]
            if not is_consistent(cur_sol, s):
                cur_sol[k] = -1
                k -= 1
                continue
        else:
            cur_sol[k] = -1
            k -= 1
            continue
        k += 1



if __name__ == '__main__':
    l = []
    n = int(input('The number of available coins is: '))
    for i in range(n):
        x = int(input())
        l.append(x)
    s = int(input('The sum required for payment is:'))
    l.sort()
    print('The available coins are:', l)
    print('Iterative: the sum,', s, 'can be paid as:')
    bkt_iterative(n,l,s)
    if c2 == False:
        print('not possible with available coins.')
    st = []
    vis = [0]*n
    print('Backtracking: the sum', s, 'can be paid as:')
    back(l, n, s, 0)
    if c == False:
        print('not possible with available coins.')

