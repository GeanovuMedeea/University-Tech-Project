#this program simulated 3 coin tosses
N=input("Input the number of simulations, N = ");
U=rand(3,N); #generates N simulations of 3 coin tosses, each trio being either head or tail (1 or 0)
Y=(U<0.5); #the matrix Y will have 0=tails where the values are smaller than 0.5, and 1=head where the values are bigger than 0.5
S=sum(Y); #the matrix S will have on each column the sum of the elements of each column in matrix Y, how many times we got heads in 3 tosses
clf
hist(S) #generates a histogram (bar chart) of the elements in the matrix S, the values will always be different in the graph

