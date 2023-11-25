#we solve the 2nd exercise
#Point a)
p=input("Please input the probability (0.05 <= p <= 0.95) p= ");
for n=1:3:100 #it will simulate the steps that go to infinity, we can modify the start, end and step size however we wish
  x=0:n # we can have at most n successes in n trials
  y=binopdf(x,n,p);
  plot(x,y); #we'll see it gradually define a bell shape
  pause(0.5); #so the graphs won't cycle so fast that we won't be able to see them
endfor
