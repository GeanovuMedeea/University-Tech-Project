#we are solving exercise nr 2 from Lab2
#we plot the pdf and the cdf of the binomial model
n = input("Give number of trials, n = "); #n is a natural number
p = input("Give the probability of success, p = "); #p is between 0 and 1
x = 0:1:n; #this is the number of successes that we can find in n trials
px = binopdf(x,n,p);
plot(x,px,'*r') #a pdf graph is a graph of points, it's not continuous
hold on #this will fuse both of the graphs, instead of just printing the cdf one (last one), while figure keeps them separate
xx = 0:0.01:n;
cx = binocdf(xx,n,p);
plot(xx,cx,'g');
#h1 = legend();
#str = get(h1, "string");
#str(1:5:end) = "Red is pdf";
#set(h1, "string", str);
legend('Red is pdf', 'Green is cdf');
