A=[1021 980 1017 988 1005 998 ...
  1014 985 995 1004 1030 ...
  1015 995 1023];

B=[1070 970 993 1013 1006 ...
  1002 1014 997 1002 1010 975];

n1=length(A);n2=length(B);
v1=var(A);v2=var(B);
m1=mean(A);m2=mean(B);


# a) 5% significance level, do the population variances seem to differ?
# b) a 95% confidence interval for the difference of the average weights

# H0= sigma1^2=sigma2^2
# H1 = sigma1^2!=sigma2^2, so this should be a two tailed test, for the ratio of population variances

# sigma is unknown, normally distributed
alpha=input('Input the significance level (0<alpha<1) (for point a is 0.05):');

# we compute the rejection region
tt1=finv(alpha/2,n1-1,n2-1);
tt2=finv(1-alpha/2,n1-1,n2-1);

[h,pval,ci,stat]=vartest2(A,B,"alpha",alpha,"tail","both");
fprintf('H is %d\n',h);
if(h==1)
  fprintf('The null hypothesis is rejected, the population variances differ.\n');
  fprintf('The rejection region is (%6.4f/%6.4f)U(%6.4f,%6.4f)\n',-inf,tt1,tt2,inf);
  fprintf('The test value is %6.4f\n',stat.fstat);
  fprintf('The p-value is %6.4f\n',pval);
  fprintf('\nThen sigma1 != sigma2\n');

  # point b
  oneminusalpha=input('Confidence interval (0<oneminusalpha<1) (for point b is 0.95):');
  alpha=1-oneminusalpha;

  c=(v1/n1)/(v1/n1+v2/n2);
  n=c*c/(n1-1)+(1-c)*(1-c)/(n2-1);
  n=1/n;
  d1=m1-m2-tinv(1-alpha/2,n)*sqrt(v1/n1+v2/n2);
  d2=m1-m2+tinv(1-alpha/2,n)*sqrt(v1/n1+v2/n2);
  fprintf('The confidence interval for the difference of average weights, sigma unknown,but different, is (%6.4f, %6.4f)\n',d1,d2);

else
  fprintf('The null hypothesis is not rejected, the population variances are equal.\n');
  fprintf('The rejection region is (%6.4f/%6.4f)U(%6.4f,%6.4f)\n',-inf,tt1,tt2,inf);
  fprintf('The test value is %6.4f\n',stat.fstat);
  fprintf('The p-value is %6.4f\n',pval);
  fprintf('\nThen sigma1 == sigma2\n');

  # point b
  oneminusalpha=input('Confidence interval (0<oneminusalpha<1) (for point b is 0.95):');
  alpha=1-oneminusalpha;

  sp=((n1-1)*v1+(n2-1)*v2)/(n1+n2-2);
  d1=m1-m2-tinv(1-alpha/2,n1+n2-2)*sp*sqrt(1/n1+1/n2);
  d2=m1-m2+tinv(1-alpha/2,n1+n2-2)*sp*sqrt(1/n1+1/n2);
  fprintf('The confidence interval for the difference of average weights, sigma unknown, but equal, is (%6.4f, %6.4f)\n',d1,d2);

endif
