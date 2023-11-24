% 12.
% a. Define a predicate to add after every element from a list, the divisors of that number.
% b. For a heterogeneous list, formed from integer numbers and list of numbers, 
% define a predicate to add in every sublist the divisors of every element.
% Eg.: [1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2] =>
% [1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]

% Mathematical model
% compute_and_add_divisors(N:number,Div:number,List:l1...ln) =
%   l1...ln, N=0,N=1,N=Div
%   Div + compute_and_add_divisors(N:number, Div+1:number, list:l2...ln), N % Div = 0
%   compute_and_add_divisors(N:number, Div+1:number, List:l2...ln), N % Div != 0

% compute_and_add_divisors(N:number, D:number, L:List, R:list)
% flow model(i,i,i,o)

% CASE 1/2/3/4 covers whether or not the number we need to compute the divisors of does not have any divisors of its own or it is equal with the number we want to check
% if it is a divisors of N
% CASE 5 if D is a divisor of N and we add it to the result list, then we check if D+1 is also a divisor in the rest of the list
% CASE 6 if D is not a divisor of N, then we check if D+1 is a divisor of N in the rest of the input list

compute_and_add_divisors(N,_,L,L):- N =:= 0.
compute_and_add_divisors(N,_,L,L):- N =:= 1.
compute_and_add_divisors(N,_,L,L):- N =:= 2.
compute_and_add_divisors(N,N,L,L).
compute_and_add_divisors(N,D,L,[D|R]):- N mod D =:=0,
    NewD is D + 1,
    compute_and_add_divisors(N,NewD,L,R).
compute_and_add_divisors(N,D,L,R):-
    NewD is D + 1,
    compute_and_add_divisors(N,NewD,L,R).

% Mathematical model
% divisors(l1...ln:list, Result:list)=
%   [], list is empty
%   compute_and_add_divisors(l1,2) + divisors(l2...ln), if list is not empty

% divisors(L:List, R:List)
% flow model(i,o)
% flow model(i,i)

% CASE 1 empty List
% CASE 2 we add the head of the list to the result, and we also check if the element in question has any divisors, which we then add to the result list
% the recursive call divisors(Tail of list, Divisors) will compute in the list Divisors the divisors for the tail of the input list, in this way we can take each
% element as the head of the recursively called tail and compute its divisors and add them in R; at the end of the process we obtain as a result the list of each
% number and its divisors appended to each of the numbers; it is similar to the append function

divisors([],[]).
divisors([H|T],[H|R]):-
    divisors(T,Divisors),
    compute_and_add_divisors(H,2,Divisors,R).

% b
% Mathematical model
% divisors_heterogenic_list(l1...ln:list)=
%   [], if list is empty
%   divisors(l1) + divisors_heterogenic_list(l2...ln), if l1 is a sublist
%   l1 + divisors_heterogenic_list(l2...ln), if l1 is not a sublist

% divisors_heterogenic_list(L:list, R:list)
% flow_model(i,o)
% flow_model(i,i)

% CASE 1 empty list
% CASE 2 if the element in the heterogeneous list is a list by itself, then we compute the divisors of each of its element, and add the divisors to the output list
% and check if there are more list elements in the list
% CASE 3 if the element is not a list, we look for other lists in the main list

divisors_heterogenic_list([],[]).
divisors_heterogenic_list([H|T],[Divisors|R]):- is_list(H),
    divisors(H,Divisors),
    divisors_heterogenic_list(T,R).
divisors_heterogenic_list([H|T],[H|R]):-
    divisors_heterogenic_list(T,R).

    