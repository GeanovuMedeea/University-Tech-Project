import numpy as np
import matplotlib.pyplot as plt

theta = np.arange(26)
#cost function
J = (theta)**2
print(theta,J)

# Finding the value of x that minimizes J
plt.style.use("seaborn")
plt.plot(theta,J)
# adding labels to the curve
plt.ylabel('J (θ) ')
plt.xlabel("θ")
# printing the plot
plt.show()

#Case a) f is convex, f(x) = x^2, and learning rate is 0.1
error=[]
# initializing beta to initial value
beta = 12
# learning rate
learn_rate = 0.1

print('a) F is convex, learning rate is 0.1:')
for i in range(50):
    gradient = 2*beta
    beta = beta - learn_rate*gradient
    J = beta**2 #computing the mean square error
    error.append(J)
    print(beta)

plt.plot(error)
plt.show()

#Case b) f is convex, f(x) = x^2, and learning rate is 1
error.clear()
# initializing beta to initial value
beta = 12
# learning rate
learn_rate = 0.4

print('b) F is convergent, learning rate is 0.5:')
for i in range(50):
    gradient = 2*beta
    beta = beta - learn_rate*gradient
    J = beta**2 #computing the mean square error
    error.append(J)
    print(beta)

plt.plot(error)
plt.show()

#Case c) f is convex, f(x) = x^2, and learning rate is 2, which leads to divergence
error.clear()
# initializing beta to initial value
beta = 12
# learning rate
learn_rate = 2

print('c) F is convex, learning rate is 2:')
for i in range(50):
    gradient = 2*beta
    beta = beta - learn_rate*gradient
    J = beta**2 #computing the mean square error
    error.append(J)
    print(beta)

plt.plot(error)
plt.show()

#Case d) f is nonconvex, f(x) = x^2-3x^3, and learning rate is 0.1
theta = np.arange(-4, 3, 0.5, dtype=int)
#cost function
J = (theta)**2 - 3*(theta**3)
print(theta,J)

# Finding the value of x that minimizes J
plt.style.use("seaborn")
plt.plot(theta,J)
# adding labels to the curve
plt.ylabel('J (θ) ')
plt.xlabel("θ")
# printing the plot
plt.show()

error=[]
# initializing beta to initial value
beta = 3
# learning rate
learn_rate = 0.1

print('d) F is nonconvex, learning rate is 0.1, and the gradient descent gets stuck at the local minima 0:')
for i in range(50):
    gradient = 2*beta
    beta = beta - learn_rate*gradient
    J = beta**2 #computing the mean square error
    error.append(J)
    print(beta)

plt.plot(error)
plt.show()