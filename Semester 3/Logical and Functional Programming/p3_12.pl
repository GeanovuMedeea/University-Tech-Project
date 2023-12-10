% 12. Generate all sub-strings of a length 2*n+1, formed from values of 0, 
% 1 or -1, so a1 = ..., a2n+1 = 0 and |a(i+1) - ai| = 1 or 2, for every 1 <= i <= 2n.


% mathematical model
% diff_1_or_2(X:integer, Y:integer) =
%   true, abs(X-Y) = 1 or abs(X-Y) = 2
%   false, otherwise

% schema diff_1_or_2(X:integer, Y:integer)
% flow model (i,i)

diff_1_or_2(X, Y) :- abs(X - Y) =:= 1 ; abs(X - Y) =:= 2.

% mathematical model
% valid_value(X:integer) = 
%   true, X in [-1,0,1]
%   false, otherwise

% schema valid_value(X:integer)
% flow model (i)

valid_value(X) :- member(X, [-1, 0, 1]).


% mathematical model
% constraint_substrings(L:List) = 
%   true, ln = 0, n = 1
%   constraint_substrings(l2...ln), diff_1_or_2(l1,l2) is true
%    false, otherwise

% schema constraint_susbtrings(L:list)
% flow model (i), (o)

constraint_substring([0]).
constraint_substring([A, B | R]) :-
    diff_1_or_2(A, B),
    constraint_substring([B | R]).

% mathematical model
% generate_substrings(N:integer, L:list) = 
% 

% schema generate_substrings(N:integer, L:list)
% flow model (i,i), (i,o)

generate_substrings(N, Substring) :-
    Len is 2*N + 1,
    length(Substring, Len),   % Create a list of variables of length Len
    maplist(valid_value, Substring),
    constraint_substring(Substring). % Values can be -1, 0, or 1

% mathematical model
% find_susbtrings(N:integer) =
%   generate_substrings(N,k1...kn), is true
%   find_substrings(_), otherwise

% schema find_substrings(N:integer)
% flow_model (i)

find_substrings(N) :-
    generate_substrings(N, Substring),
    write(Substring), nl,
    fail.
find_substrings(_).

:-write('Generate all sub-strings of a length 2*n+1, formed from values of 0 1 or -1, so a1 = ..., a2n+1 = 0 and |a(i+1) - ai| = 1 or 2, for every 1 <= i <= 2n.'), nl.
:-find_substrings(2).