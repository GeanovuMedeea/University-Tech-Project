A=[1001.7 975.0 978.3 988.3 978.7 988.9 ...
  1000.3 979.2 968.9 983.5 999.2 985.6];

# norm distrib, sigma unknown
# a) at the 5% siginiface level, does the data suggest that, on average, the muzzles
# are faster than 995 m/s?
oneminusalpha=input("Degree of confidence = ");
alpha=1-oneminusalpha;
n=length(A);
v=var(A);m=mean(A);
# H0 = mean(A) = 995 m/s
# H1 = mean(A) > 995 m/s this is a right tailed test
ttf=tinv(1-alpha,n-1);
[h,pval,ci,stat]=ttest(A,995,"alpha",alpha,"tail","right");
# [h,pval,ci,zval]=ztest(A,995,std(A),"alpha",alpha,"tail","right");
fprintf('H is %d\n',h);
if(h==1)
  fprintf('The hypothesis is rejected, muzzles are faster than 995m/s\n');
else
  fprintf('The hypothesis is not rejected, muzzles 995m/s fast\n');
endif

fprintf('Rejection region (%6.4f, %6.4f)\n',ttf,inf);
fprintf('Test value %6.4f\n',stat.tstat);
# fprintf('Test value %6.4f\n',zval);
fprintf('P-value is %6.4f\n',pval);

# b) Find a 99% confidence interval for the standard deviation of the velocity of
# shells of this type.
# sigma is unknown
# compute the pop variance conf int
v1=(n-1)*v/chi2inv(1-alpha/2,n-1);
v2=(n-1)*v/chi2inv(alpha/2,n-1);
s1=sqrt(v1);s2=sqrt(v2);
fprintf('The confidence interval for the standard deviation is (%6.4f, %6.4f)\n',s1,s2);


