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
% a. Assuming sigma_1 = sigma_2, find a 100(1-alpha)% confidence interval
% for the difference of the true means.
% b. Assuming sigma_1 not= sigma_2, find a 100(1-alpha)%$ confidence
% interval for the difference of the true means.
% c. Find a 100(1-alpha)%$ confidence interval for the ratio of the
% variances.

P=[22.4 21.7 24.5 23.4 21.6 23.3 22.4 23.3 22.4 21.6 24.8 20];
R=[17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];
n1=length(P);n2=length(R);
v1=var(P);v2=var(R);
m1=mean(P);m2=mean(R);
alpha=input('Input alpha (0<alpha<1) = ');

# we solve point a
# sigma unknown, sigma1=sisgma2, norm distrib, diff of means
sp=((n1-1)*v1+(n2-1)*v2)/(n1+n2-2);
m1a=m1-m2-tinv(1-alpha/2,n1+n2-2)*sqrt(sp)*sqrt(1/n1+1/n2);
m2a=m1-m2-tinv(1-alpha/2,n1+n2-2)*sqrt(sp)*sqrt(1/n1+1/n2);
fprintf('The conf int for sigma eq mean diff %6.4f, %6.4f\n', m1a, m2a);

# we solve point b
# sigma unknown, sigma1=sigma2, norm distrib, difference of means
c=(v1/n1)/(v1/n1+v2/n2);
n=c^2/(n1-1)+(1-c)^2/(n2-1);
n=1/n;
m1b=m1-m2-tinv(1-alpha/2,n)*sqrt(v1/n1+v2/n2);
m2b=m1-m2-tinv(1-alpha/2,n)*sqrt(v1/n1+v2/n2);
fprintf('Conf int for diff of true mean, sigma diff: %6.4f, %6.4f\n',m1b,m2b);

# we solve point c
# conf int for ratio of variances
r1=(1/finv(1-alpha/2,n1-1,n2-1))*(v1/v2);
r2=(1/finv(alpha/2,n1-1,n2-2))*(v1/v2);
fprintf('Ratio of two pop var: %6.4f',r1/r2);

