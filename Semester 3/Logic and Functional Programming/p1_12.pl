% 12.

% a. Write a predicate to substitute in a list a value with all the elements of another list.
% b. Remove the n-th element of a list



% a

% insert(L:list, List:list, R:list)
% flow insert(i, i, o)
% CASE 1: Appending an empty list to a list - 2nd list, results the same 2nd list
% CASE 2: happens is the list that has to be replaced is not empty, it appends the elements of the 2nd input list to the end of the 1st list (the tail), it takes the head
% of the 1st list and moves it to the result list, when it runs out of of elements in the 1st list, all the elements from the 2nd list are moved to the end of the result


insert([], L, L). 
insert([H|T], L, [H|R]) :- insert(T, L, R).
% append is the same thing


% substitute_element(L:list, E:number, List:list, R:list) - 
% flow substitute_element(i, i, i, o) 
% CASE 1: subtituting an empty list with an empty list results an empty list, no matter what the element to be replaced is or the list to be inserted is
% CASE 2: the head of the list is equal with the searched element, so we call insert of the tail of the main list to the end of the list we want to insert in the main one
% this way, we replace the element we want to get rid of with the elements of the list to be inserted in the position of the element we wanted to replace in the main 
% list, then we keep searching the other appearances of the element E; here we we skip inserting the first element of the main list, which was the element that had to be 
% replaced, into the result list
% CASE 3: the head of the list is not equal with the searched element, so we keep searching in the rest of the list (the tail) the wanted element

substitute_element([], _, _, []).
substitute_element([H|T], E, List, R) :- H =:= E,
    insert(List, T, S),
    substitute_element(S, E, List, R).
substitute_element([H|T], E, List, [H|R]) :- H =\= E,
    substitute_element(T, E, List, R).

% substitute_element([], _, _, []).
% substitute_element([H|T], H, List, R) :-
%   insert(List, T, S),
%    substitute_element(S, H, List, R).
% substitute_element([H|T], E, List, [H|R]) :-
%    substitute_element(T, E, List, R).

% b
% remove_n(L:list, P:number, R:list)
% remove_n(i, i, o)
% CASE 1: the list is empty, so it does not matter what position we want to remove
% CASE 2: the element to remove is the first one in the list
% CASE 3: the element to remove is farther in the list, so we decrement the searched position, we want to remove the n-th element, but as we moved one element
% already to the output list then we need to look at a lower position, otherwise we will not find the n-th element at all, and put the head of the list in the output

remove_n([], _, []).
remove_n([_|T], 1, T).
remove_n([H|T], P, [H|R]) :-
    P1 is P - 1,
    remove_n(T, P1, R).