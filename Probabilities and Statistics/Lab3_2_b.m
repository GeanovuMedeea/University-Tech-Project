#we will solve the 2nd exercise
#subpoint b
n=input("Please input the n (n>=30) n= ");
p=input("Please input the probability p (p<=0.05) p= ");
lambda=n*p
x=0:n # we can have at most n successes in n trials
y=binopdf(x,n,p);
z=poisspdf(x,lambda);
plot(x,y, '*r;Binomial distribution;',x,z, 'xb;Poisson distribution;'); #plot the binomial graph
legend("Binomial","Poisson");
xlabel("X");
ylabel("Binomial of X");
#homework: plot the binopdf in the same graph as poisspdf, with 2 different colours and a legend
