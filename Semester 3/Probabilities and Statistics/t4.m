# A study is conducted to compare heat loss in glass pipes, versus steel
# pipes of the same size. Various liquids at identical starting temperaturs
# are run through segments of each type and the heat loss (in C) is measured
# These data result (normality of the two populations is assumed)
# a) At the 5% significance level, do the population variances seem to differ?
# b) At the same significance level, does it seem that on average, steel
# pipes lose more heat than glass pipes?

S=[4.6 0.7 4.2 1.9 4.8 ...
  6.1 4.7 5.5 5.4];

G=[2.5 1.3 2.0 1.8 2.7 ...
  3.2 3.9 3.5 3.4];

# we solve point a
# two tailed test, unknown sigma and different, we need a ratio test
# H0 = sigma1^2=sigma2^2
# H1 = sigma1^2!=sigma2^2
n1=length(S);n2=length(G);
alpha=0.05;
m1=mean(S);m2=mean(G);
v1=var(S);v2=var(G);
tt1=finv(alpha/2,n1-1,n2-1);tt2=finv(1-alpha/2,n1-1,n2-1);

[h,pval,ci,stats]=vartest2(S,G,"alpha",alpha,"tail","both");
fprintf('H is: %d\n',h);
if(h==1)
  fprintf('The null hypothesis is rejected! The pop. variances differ.\n');
else
  fprintf('The null hypohothesis is rejected! The pop. variances are equal.\n');
endif

fprintf('The rejection region is (%6.4f, %6.4f)U(%6.4g, %6.4f)\n',-inf,tt1,tt2,inf);
fprintf('The test value interval is %6.4f\n',stats.fstat);
fprintf('The p-value is %6.4f\n',pval);

# here we solve point b)
# first case, for different pop. variances
# it should be a right tailed test
# H0 = mean1 > mean2
# H1 = mean1 != mean2

c=(v1/n1)/(v1/n1+v2/n2);
n=c^2/(n1-1)+(1-c)^2/(n2-1);
n=1/n;

tt=tinv(1-alpha,n);
[h,pval,ci,stat]=ttest2(S,G,"alpha",alpha,"tail","right","vartype","unequal");
fprintf('\nH is %d\n',h);
if(h==1)
  fprintf('The null hyphothesis is rejected, steel pipes lose less or the same as glass pipes. \n')
else
  fprintf('The null hyphothesis is not rejected, the steel pipes lose more heat than glass pipes.\n');
endif

fprintf('The rejection region is (%6.4f, %6.4f)\n',tt,inf);
fprintf('The test value is %6.4f\n',stat.tstat);
fprintf('The pvalue is %6.4f\n\n', pval);

# case in which the pop variances are equal
sp=((n1-1)*v1+(n2-1)*v2)/(n1+n2-2);
tt=tinv(1-alpha,n1+n2-2);
[h,pval,ci,stat]=ttest2(S,G,"alpha",alpha,"tail","right","vartype","equal");
fprintf('\nH is %d\n',h);
if(h==1)
  fprintf('The null hyphothesis is rejected. Steel loses <= glass\n');
else
  fprintf('The null hyphothesis is not rejected! Steel loses >glass\n');
endif

fprintf('The rejection region is: (%6.4f, %6.4f)\n',tt,inf);
fprintf('The test value is %6.4f\n',stat.tstat);
fprintf('The pvalue is %6.4f\n',pval);



