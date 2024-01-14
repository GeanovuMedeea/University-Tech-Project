% In a study of the size of various computer systems, the random variable
% $X$, the number of files stored, is considered. If they cannot store at
% least 9 files, on average, they don't meet the efficiency standard
% and they have to be replaced. Past experience
% indicates that sigma = 5. These data are obtained:
%  7  7  4  5  9  9
%  4 12  8  1  8  7
%  3 13  2  1 17  7
% 12  5  6  2  1 13
% 14 10  2  4  9 11
%  3  5 12  6 10  7
% a. At the 5% significance level, does the data suggest that the standard
% is met? What about at 1%? (Find the rejection region and the value of
% the test statistic)
% b. What is the P-value of this test?

# we solve point a
# sigma 5, mean, left tail
# H0 = mean >= 9
# H1 = mean < 9
A=[%  7  7  4  5  9  9 ...
  4 12  8  1  8  7 ...
  3 13  2  1 17  7 ...
 12  5  6  2  1 13 ...
 14 10  2  4  9 11 ...
  3  5 12  6 10  7];
alpha=input('Input alpha (0<alpha<1): ');
n=length(A);
tt=norminv(alpha,0,1);
m=mean(A);v=var(A);
[h,pval,ci,stat]=ztest(A,9,5,"alpha",alpha,"tail","left");
fprintf('H is %d\n',h);
if(h==1)
  fprintf('The hyphothesis is rejected! The average is not met!\n');
else
  fprintf('The hyphothesis is not rejected! The average is met!\n')
endif

fprintf('The rejection are is (%6.4f, %6.4f)\n',-inf,tt);
fprintf('The test value is %6.4f\n',stat);
fprintf('The pvalue is %6.4f\n', pval);

# this is for the unknown sigma case
tt2=tinv(alpha,n-1);
[h2,pval2,ci2,stat2]=ttest(A,9,"alpha",alpha,"tail","left");
fprintf('H is %d\n',h2);
if(h2==1)
  fprintf('The hyphothesis is rejected! The average is not met!\n');
else
  fprintf('The hyphothesis is not rejected! The average is met!\n')
endif

fprintf('The rejection are is (%6.4f, %6.4f)\n',-inf,tt2);
fprintf('The test value is %6.4f\n',stat2.tstat);
fprintf('The pvalue is %6.4f\n', pval2);

# we solve b
v0=input('test value =');
# H0 = V = v0;
# H1 = V != v0;
# this should be a two tailed test
tt3=chi2inv(alpha/2,n-1);
tt4=chi2inv(1-alpha/2,n-1);
[h,p,ci,stats]=vartest(A,v0,"alpha",alpha,"tail","both");

fprintf('H is %d\n',h);
if(h==1)
  fprintf('The null hyphothesys is rejected, the variance is not equal to %6.4f\n',v0);
else
  fprintf('The null hyphothesys is not rejected, the variance is equal to %6.4f\n',v0);
endif
fprintf('Rejection region is (%6.4f, %6.4f)U(%6.4f, %6.4f)',-inf,tt3,tt4,inf);
fprintf('The value of the test chi2 is %6.4f\n', stats.chisqstat);
fprintf('The Pvalue of the test is %6.4f\n',p);

% A fitness center purchases energy bars, but only if their
% average weight does not exceed 99.4 grams.
% A random sample
% of 20 yields the following data (in grams):
% X = 99.8  99.9  98.0  100.1  100.5  100.0  100.2
%      2     5      3     4      2      2      2
% Assume the weights of the energy bars are approximately normally
% distributed.
% At the 5% significance level, does the data suggest that the center
% will accept these energy bars?
% What about at 1%?
% The null hypothesis H0: mu = 99.4
% the alt. hypothesis H1: mu > 99.4. This is a right-tailed test for mu.
x= [ 99.8*ones(1,2), 99.9*ones(1,5), 98.0*ones(1,3), 100.1*ones(1,4),...
    100.5*ones(1,2), 100.0*ones(1,2), 100.2*ones(1,2)];

n=length(x);
m=mean(x);v=var(x);
alpha=input('Alpha is: \n');
# sigma unknown, mean test
[h,pval,c,stat]=ttest(x,99.4,"alpha",alpha,"tail","right")
fprintf('H is %d\n',h);
if(h==1)
  fprintf('The hyphothesys is rejected, the bars are too heavy\n');
else
  fprintf('The hypothesis is not rejected, the bars are accepted\n');
endif
tt5=tinv(1-alpha,n-1);
fprintf('Rejection region (%6.4f, %6.4f)\n',tt5,inf);
fprintf('Test value %6.4f\n',stat.tstat);
fprintf('Pvalue is %6.4f\n',pval);

