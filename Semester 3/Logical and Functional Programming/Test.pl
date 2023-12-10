% Define a predicate that merges two sorted lists of integer values and removes
% all duplicate values. For example:
% [2,3,5,5,7,8,8,9] and [2,2,2,3,4,5,6,8,9] -> [2,3,4,5,6,7,8,9]

% Mathematical model:
%     f(l1l2...ln,k1k2...km) : { [], n = m = 0 }
%                             { l1 U f(g(l1, l2...ln),k1k2...km), l1 < k1 }
%                             { k1 U f(l1l2...ln,g(k1,k2...km)), l1 > k1 }
%                             { l1 U f(g(l1,l2...ln),g(k1,k2...km)), l1 = k1 }

%     g(l1,l2...ln) : { [], n = 0 }
%                     { g(l2...ln), l1 = l2 }
%                     { l1 U g(l2...ln), l1 != l2 }

% Basically, g removes duplicates from a list and f merges the two lists, after removing duplicates.

% Signature : f(L:list, K:list, R:list)
% Flow model : (i i o), (i i i)

f([], [], []).
f(L1, [], L1).
f([], K1, K1).

f([H1|T1], [H2|T2], [H1|R]) :-
    H1 < H2, 
    removeOccurence(T1,H1,NewT1),
    f(NewT1, [H2|T2], R).

f([H1|T1], [H2|T2], [H2|R]) :-
    H1 > H2, 
    removeOccurence(T2,H2,NewT2),
    f([H1|T1], NewT2, R).

f([H1|T1], [H2|T2], [H1|R]) :-
    H1 =:= H2, 
    removeOccurence(T1,H1,NewT1), 
    removeOccurence(T2,H2,NewT2),
    f(NewT1, NewT2, R).

% Remove duplicates from a list
% Signature : remove_duplicates(L:list, R:list)
% Flow model : (i o), (i i)

removeOccurence([],_,[]).
removeOccurence([H|T],E,R):-
    H=:=E,
    removeOccurence(T,E,R).
removeOccurence([H|T],E,[H|R]):-
    H=\=E,
    removeOccurence(T,E,R).

:- write('Both lists are empty, f([], [], R), provides: '), nl.
:- f([], [], R), write(R), nl, nl.

:- write('First list is empty, f([1,2,3], [], R) provides: '), nl.
:- f([1,2,3], [], R), write(R), nl, nl.

:- write('Second list is empty, f([], [1,4,7], R) provides: '), nl.
:- f([], [1,4,7], R),  write(R), nl, nl.

:- write('Lists are non-empty and contain the same elements: f([1,2,3],[1,2,3],R) provides: '), nl.
:- f([1,2,3], [1,2,3], R), write(R), nl, nl.

:- write('Lists are non-empty and contain unique elements: f([2,3,4,5,6],[2,3,5,6,7,8], R) provides: '), nl.
:- f([2,3,4,5,6],[2,3,5,6,7,8], R), write(R), nl, nl.

:- write('Lists are non-empty and contain unique elements: f([2,4,4,5,6],[2,3,5,5,6,7,8], R) provides: '), nl.
:- f([2,4,4,5,6],[2,3,5,5,6,7,8], R), write(R). 