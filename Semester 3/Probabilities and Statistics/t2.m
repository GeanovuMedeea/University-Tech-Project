# A food store owner receives 1-liter water bottles from two suppliers.
# After some complaints from custormers, he wants to check the accuracy
# of the weights of 1-liter water bottles. He finds the following weights
# (the two populations are approximately normally distributed)
# a) At the 5% significance level, do the population variances seem
# to differ?
# b) At the same significance level, on average, does Supplier A seem
# to be more reliable?

A=[1021 980 1017 988 1005 ...
  998 1014 985 995 1004 ...
  1030 1015 995 1023];

B=[1070 970 993 1013 1006 ...
  1002 1014 997 1002 1010 ...
  975];

# we solve point a)
# this should be a ratio of two population variances
# a two tailed test, H0 being 1 (Ratio)

n1=length(A);
n2=length(B);
alpha=input('Input the significance level 0<alpha<1:');

ttvariance1 = finv(alpha/2,n1-1,n2-1);
ttvariance2 = finv(1-alpha/2,n1-1,n2-1);
RR1=[-inf  ttvariance1];
RR2=[ttvariance2 inf];

h = ((var(A)/var(B))<ttvariance1)||((var(A)/var(B))>ttvariance2);
pval = 2 * min(fcdf(var(A)/var(B), n1-1,n2-1), 1 - fcdf(var(A)/var(B), n1-1,n2-1));


printf("The rejection area is: (%4.3f, %4.3f) U (%4.3f, %4.3f)\n",RR1,RR2);
printf('The value of the test statistic is %4.3f\n', var(A)/var(B));
printf('The pvalue of the test is %4.3f\n',pval);

printf("The value of h is: %d\n",h);
if h == 1
  printf('The null hypothesys is rejected!\n');
  printf('The data suggests that the population variances differ!\n');
else
  printf('The null hypothesys is not rejected!\n');
  printf('The data suggests that the population variances do not differ!\n\n');
endif


# we solve point b)
# this should be a right tailed test > 0

% Perform two-sample t-test
[h, pval, ci, stats] = ttest2(A, B, "alpha", alpha,"tail","right","vartype","unequal");

% Display results
fprintf("The value of h is: %d\n", h);

if h == 1
    fprintf('The null hypothesis is rejected!\n');
    fprintf('The data suggests that Supplier A is less reliable!\n');
else
    fprintf('The null hypothesis is not rejected!\n');
    fprintf('The data suggests that Supplier A is as reliable as Supplier B!\n');
end

v1=var(A);
v2=var(B);
c=(v1/n1)/(v1/n1+v2/n2);
n=c^2/(n1-1)+(1-c)^2/(n2-1);
n=1/n;
talpha=tinv(1-alpha,n);
RR=[talpha inf];

fprintf("The rejection area is: (%4.3f, inf)\n", talpha,inf);
fprintf('The value of the test statistic is %4.3f\n', stats.tstat);
fprintf('The p-value of the test is %4.3f\n', pval);


