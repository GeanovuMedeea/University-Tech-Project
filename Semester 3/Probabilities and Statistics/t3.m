# To reach the maximum efficiency in performing an assembling operation
# in a manufacturing plant, new employees are required to take a 1-month
# training course. A new method was suggested, and the manager wants to
# compare the new method with the standard one, by looking at the lengths
# of time required for employees to assemble a certain device. They are
# given below (and assumed approximately normally distributed).
# a) At the 5% significance level, do the population variances seem to
# differ?
# b) Find a 95% confidence interval for the difference of the average
# assembling times.

S=[46 37 39 48 47 ...
  44 35 31 44 37];

N=[35 33 31 35 34 ...
  30 27 32 31 31];

# we solve point a

alpha=0.05;
# we need population variances, which we do not know, and we also need to test
# if the variances are different through a ratio test
# this should be a two tailed test
# H0: sigma1^2 = sigma2^2
# H1: sigma1^2 != sigma2^2

n1=length(S);n2=length(N);
m1=mean(S);m2=mean(N);
v1=var(S);v2=var(N);
tt1=finv(alpha/2,n1-1,n2-1);
tt2=finv(1-alpha/2,n1-1,n2-1);
[h,pval,ci,stats]=vartest2(S,N,"alpha",alpha,"tail","both");

fprintf('Part a. Comparing variances\n');
fprintf('Two-tailed test for comparing variances\n');
fprintf('H is: %d\n',h);
if(h==1)
  fprintf('The null hypothesis is rejected. The varriances are different.\n');
else
  fprintf('The null hypothesis is not rejected. The variances are the same.\n');
endif

fprintf('The rejection region is (%6.4f,%6.4f)U(%6.4f, %6.4f)\n',-inf,tt1,tt2,inf);
fprintf('The value of the test statistic is %6.4f\n',stats.fstat);
fprintf('The P-value for the variances test is %6.4f\n',pval);

# we solve point b
# difference of average, so mean; 95% conf interval, alpha = 0.05
# sigma is unknown, so case 3, part 3
# case in which the pop. variances are different
alpha=0.05;
c=(v1/n1)/(v1/n1+v2/n2);
n=c*c/(n1-1)+(1-c)*(1-c)/(n2-1);
n=1/n;
d1=m1-m2-tinv(1-alpha/2,n)*sqrt(v1/n1+v2/n2);
d2=m1-m2+tinv(1-alpha/2,n)*sqrt(v1/n1+v2/n2);
fprintf('The confidence interval for the difference of average assembling times, sigma unknown and different is (%6.4f, %6.4f)\n',d1,d2);

# case in which the population variances are the same
sp=((n1-1)*v1+(n2-1)*v2)/(n1+n2-2);
d1=m1-m2-tinv(1-alpha/2,n1+n2-2)*sp*sqrt(1/n1+1/n2);
d2=m1-m2+tinv(1-alpha/2,n1+n2-2)*sp*sqrt(1/n1+1/n2);
fprintf('The confidence interval for the difference of average assembling times, sigma unknown, but equal is (%6.4f, %6.4f)\n',d1,d2);



