#X ~Bino(3,0.5)
#we solve point a)
x=0:1:3;
px=binopdf(x,3,0.5);
#plot(x,px,'*r');
#hold on
#we solve point b)
xx=0:0.01:3;
cx=binocdf(xx,3,0.5);
#plot(xx,cx,'g');

#we solve point c)
#P(X=0) is the value of binopdf(0,3,0.5), as we only need the first value of the vector
p1=binopdf(0,3,0.5);
printf('P(X=0)=%1.6f\n',p1);
#P(X!=1) is the value of 1-binopdf(1,3,0.5) as we can deduct only the probability of x=1 from the whole ones
p2=1-binopdf(1,3,0.5);
printf('P(X!=1)=%1.6f\n',p2);

#we solve point d)
#P(X<=2) is equal to the cdf in point 2
p3=binocdf(2,3,0.5);
printf('P(X<=2)=%1.6f\n',p3);

#P(X<2) is equal tot he cdf in point 2, deducted of pdf in point 2
#it is also equal with P(x<=1)
#p4=p3 - binopdf(2,3,0.5);
p4=binocdf(1,3,0.5);
printf('P(X<2)=%1.6f\n',p4);

#we solve point e)
#P(X>=1) is equal to 1- the pdf of 0
p5=1-binocdf(0,3,0.5);
#p5=1-binopdf(0,3,0.5) since we only need to deduct the case in which x = 0, if we had more values, it wouldn't work
printf('P(X>=1)=%1.6f\n',p5);

#P(X>1) is equal to 1-cdf of 1
p6 = 1 - binocdf(1,3,0.5);
printf('P(X>1)=%1.6f\n',p6);

