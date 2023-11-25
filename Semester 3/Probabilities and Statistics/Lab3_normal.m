#each of the 1 st exercise points will be solved in each of the probability distributions as homework (all 4 options)
#we'll solve the first exercise, using the normal distribution, which is continuous
p1=normcdf(0);
printf('P(X<=0)=%1.6f\n',p1);
printf('P(X>=0)=%1.6f\n',1-p1); #in continuous distributions, the x<0 can be substituted to x<=0 and x>0 with x>=0

#solve subpoint b
#P(a<X<=b) = P(a<=X<=b)=P(a<X<b) = F(b) - F(a) because they are continuous distributions
p2=normcdf(1)-normcdf(-1);
printf('P(-1<X<=1)=%1.6f\n',p2);
printf('P(X<0.2=-1 or x>=1)=%1.6f\n',1-p2); #we compute the opposite of the first probability, as point b wants exactly the interval that is ignored
#by point a

#solve subpoint c and d
alpha=input('Please input alpha (0 < alpha < 1)= ');
beta=input('Please input beta (0 < beta < 1)= ');
p3=norminv(alpha);
printf('P(X<(xalpha))= alpha is %1.6f\n',p3); #quantile of order alpha
p4=norminv(1-beta);
printf('P(X>(xbeta)) = 1-beta is %1.6f\n',p4); #quantile of order of 1-beta
