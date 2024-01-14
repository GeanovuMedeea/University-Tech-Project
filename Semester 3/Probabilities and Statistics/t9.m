% It is thought that the gas mileage obtained by a particular model of
% automobile will be higher if unleaded premium gasoline is used in the
% vehicle rather than regular unleaded gasoline. To gather evidence in
% this matter, 10 cars are randomly selected from the assembly line and
% tested using a specified brand of premium gasoline; 10 others are
% randomly selected and tested using the brand's regular gasoline. Tests
% are conducted under identical controlled conditions and gas mileages
% for both types of gas are assumed independent and (approximately)
% normally distributed. These data result:
%   Premium            Regular
% 22.4  21.7    !    17.7  14.8
% 24.5  23.4    !    19.6  19.6
% 21.6  23.3    !    12.1  14.8
% 22.4  21.6    !    15.4  12.6
% 24.8  20.0    !    14.0  12.2
% Let 0 < alpha < 1.
% a. At the alpha significance level, is there evidence that the variances
% of the two populations are equal?
% b. Based on the result in part  a., at the same significance level,
% does gas mileage seem to be higher, on average, when premium gasoline
% is used?

# sigma is unknown, norm dis
P=[22.4  21.7 ...
 24.5  23.4 ...
 21.6  23.3 ...
 22.4  21.6 ...
 24.8  20.0];

R=[17.7  14.8 ...
19.6  19.6 ...
12.1  14.8 ...
15.4  12.6 ...
14.0  12.2];
alpha=input('Alpha (0 < alpha < 1) = ');
n1=length(P);n2=length(R);
v1=var(P);v2=var(R);

# we solve a
# equal pop variance
# H0 = sigma1^2=sigma2^2
# H1 = sigma1^2 != sigma2^2, this is a two tailed test

tt1=finv(alpha/2,n1-1,n2-1);
tt2=finv(1-alpha/2,n1-1,n2-1);

[h,pval,ci,stat]=vartest2(P,R,"alpha",alpha,"tail","both");
fprintf('H is %d\n',h);
if(h==1)
  fprintf('The hypothesis is rejected. The pop variances differ.\n');
else
  fprintf('The hypothesis is not rejected. The pop variances are the same \n');
endif

fprintf('The rejection region is (%6.4f, %6.4f)U(%6.4f, %6.4f)\n',-inf,tt1,tt2,inf);
fprintf('The test value is %6.4f\n',stat.fstat);
fprintf('The pvalue is %6.4f\n',pval);

# we solve b
# mean P larger than mean R
# H0 = mean(P)=mean(R)
# H1 = mean(P)>mean(R) right tailed test
# case 1, sigma is unequal

c=(v1/n1)/(v1/n1+v2/n2);
n=c^2/(n1-1)+(1-c)^2/(n2-1);
n=1/n;
tt3=tinv(1-alpha,n);

[h,pval,ci,stat]=ttest2(P,R,"alpha",alpha,"tail","right","vartype","unequal");

fprintf('H is %d\n',h);
if(h==1)
  fprintf('Hypothesys is rejected, premium better than regular\n');
else
  fprintf('Hypothesis is not rejected, premium the same as regular\n');
endif

fprintf('Rejection region (%6.4f, %6.4f)\n',tt3,inf);
fprintf('Test value %6.4f\n',stat.tstat);
fprintf('P-value is %6.4f\n',pval);

# sigma equal
tt4=tinv(1-alpha,n1+n2-2);
[h,pval,ci,stat]=ttest2(P,R,"alpha",alpha,"tail","right","vartype","equal");

fprintf('H is %d\n',h);
if(h==1)
  fprintf('Hypothesis rejected, premium better than regular\n');
else
  fprintf('Hypothesis rejected, premium the same as regular\n');
endif

fprintf('Rejection region (%6.4f, %6.4f)\n',tt4,inf);
fprintf('Test value is %6.4f\n',stat.tstat);
fprintf('P-value is %6.4f\n',pval);




