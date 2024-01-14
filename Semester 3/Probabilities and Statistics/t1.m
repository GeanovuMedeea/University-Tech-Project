# Nickel powders are used in coatings used to shield electronic equipment
# A random sample is selected, and the sizes of nickel particles in each coating
# are recorded (they are assumed to be approximately normally distributed)
# a) Find a 95% confidence interval for the average size of nickel particles
# b) At the 1% significance level, on average, do these nickel particles seem
# to be smaller than 3?

A=[3.26 1.89 2.42 2.03 ...
  2.95 1.39 3.06 2.46 ...
  3.35 1.56 1.79 1.76 ...
  3.82 2.42 2.96];


# solving a)

n=length(A);

oneminusalpha=input('Input the confidence level (>0 and <1):');
alpha=1-oneminusalpha;

m1=mean(A)-std(A)/sqrt(n)*tinv(1-alpha/2,n-1);
m2=mean(A)-std(A)/sqrt(n)*tinv(alpha/2,n-1);
#m11=(n-1)*std(A)*std(A)/chi2inv(1-alpha/2,n-1);
#m22=(n-1)*std(A)*std(A)/chi2inv(alpha/2,n-1);
#m1=sqrt(m11);
#m2=sqrt(m22);
printf('The confidence interval for the theoretical mean, sigma unknown: ');
printf('%4.3f,%4.3f\n',m1,m2);

# solving b

n=length(A);
oneminusalpha=input('Input the confidence level (>0 and <1): ');
alpha=1-oneminusalpha;
# it should be a left tailed test <3
m0=3;
[h,p,ci,stats]=ttest(A,m0,"alpha",alpha,"tail","left");
ttalpha = tinv(alpha,n-1);
RR=[-inf ttalpha];

printf("The value of h is: %d\n",h);
if h == 1
  printf('The null hypothesys is rejected!\n');
  printf('The data suggests that the nickel particles are greater than 3!\n');
else
  printf('The null hypothesys is not rejected!\n');
  printf('The data suggests that the nickel particles are smaller than 3!\n');
endif

printf('The rejection region is (%4.3f, %4.3f)\n',RR);
printf('The value of the test statistic is: %6.4f\n',stats.tstat);
printf('The pvalue of the test is: %6.4f\n',p);
