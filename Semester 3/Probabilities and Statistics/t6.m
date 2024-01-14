# In a study of the size of various computer systems, the random variable
% $X$, the number of files stored, is considered. Past experience
% indicates that sigma = 5. These data are obtained:
%  7  7  4  5  9  9
%  4 12  8  1  8  7
%  3 13  2  1 17  7
% 12  5  6  2  1 13
% 14 10  2  4  9 11
%  3  5 12  6 10  7
% Find a 100(1-alpha)% confidence interval for the average number of
% files stored.

A=[7  7  4  5  9  9 ...
  4 12  8  1  8  7 ...
  3 13  2  1 17  7 ...
 12  5  6  2  1 13 ...
 14 10  2  4  9 11 ...
  3  5 12  6 10  7];

n =length(A);
sigma=5;
alpha=input('Input alpha (0<alpha<1) = ');
# average is needed, sigma known
m1=mean(A)-(sigma/sqrt(n))*norminv(1-alpha/2,0,1);
m2=mean(A)-(sigma/sqrt(n))*norminv(alpha/2,0,1);
fprintf('The confidence interval fo rthe average number of files stored is (%6.4f, %6.4f)\n',m1,m2);

% The weights of chocolate bars of a certain brand are studied. A sample
% of 20 yields the following data (in grams):
% X = 99.8  99.9  98.0  100.1  100.5  100.0  100.2
%      2     5      3     4      2      2      2
% Assuming the weights of the chocolate bars are approximately normally
% distributed, find a 100(1-alpha) confidence interval for the average
% weight of the chocolate bars

B=[99.8*ones(1,2)  99.9*ones(1,5)  98.0*ones(1,3)  100.1*ones(1,4)  100.5*ones(1,2)  100.0*ones(1,2)  100.2*ones(1,2)];

#sigma unknown
n=length(B);
m1=mean(B)-std(B)/sqrt(n)*tinv(1-alpha/2,n-1);
m2=mean(B)-std(B)/sqrt(n)*tinv(alpha/2,n-1);
fprintf('The confidence interval for the average weight, sigma unknown, is (%6.4f, %6.4f)',m1,m2);

% Problem 3.
% When programming from a terminal, one random variable of concern is the
% response time (in seconds). For one particular installation, a
% (repeated) random sample yields the following data:
% 1.48  1.26  1.52	 1.56	1.48  1.46
% 1.30  1.28  1.43	 1.43	1.55  1.57
% 1.51  1.53  1.68	 1.37   1.47  1.61
% 1.49  1.43  1.64	 1.51   1.60  1.65
% 1.60  1.64  1.51	 1.51   1.53  1.74
% Assuming the response times of the terminals are (approximately) normally
% distributed, find 100(1-alpha)% confidence intervals for the
% variance and for the standard deviation (0< alpha < 1).

C=[% 1.48  1.26  1.52	 1.56	1.48  1.46 ...
 1.30  1.28  1.43	 1.43	1.55  1.57 ...
 1.51  1.53  1.68	 1.37   1.47  1.61 ...
 1.49  1.43  1.64	 1.51   1.60  1.65 ...
 1.60  1.64  1.51	 1.51   1.53  1.74];

n=length(C);

# sigma unknown, variance and standard deviation
v1=((n-1)*var(C))/chi2inv(1-alpha/2,n-1);
v2=((n-1)*var(C))/chi2inv(alpha/2,n-1);
std1=sqrt(v1);std2=sqrt(v2);
fprintf('The conf int for pop var sigma unknown is %6.4f, %6.4f\n',v1,v2);
fprintf('The conf int for std dev sigma unknown is %6.4f, %6.4f\n',std1,std2);


