A=[1 2 3;4 5 6;7 8 9]; #the semicolon makes it invisible to the command window, but it still runs
x=1:2:10; #it starts at 1 and ends at 10, incrementing by 2
A(1:2,2:3);
A(1,1);

x=1:2:10
#x+2; #adds 2 to each of x's elements
#x*3; #multiplies by 3 each element, 3*x works
x.^2; #raises to the power of 2 each elements
B=[3 6 1;5 2 1;6 9 2];
A*B; #multiplies A by B
A.*B; #multiplies a11 with b11, a12 with b12 and keeps their positions
help input;
help printf; #give information on these functions in the command window
help plot;
clear; #clears the workspace
clc; #the command window is cleared
