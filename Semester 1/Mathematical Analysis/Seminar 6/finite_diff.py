import numpy as np
import matplotlib.pyplot as plt
plt.style.use('seaborn-poster')

# step size
h = 0.1
print('h:', h)
# define x axis
x = np.arange(0, 2*np.pi, h)
print('x:',x)
# compute function, y axis
y = np.cos(x)
print('y:',y)

# compute list of forward differences
forward_diff = np.diff(y)/h
print('forward_diff:', forward_diff)
# compute corresponding x axis
x_diff = x[:-1:]
print('x_diff:',x_diff)
# compute exact solution
exact_solution = -np.sin(x_diff) #derivative
print('exact_solution:', exact_solution)

# Plot representation
plt.figure(figsize = (12, 8))
plt.plot(x_diff, forward_diff, '--', label = 'Finite difference approximation')
plt.plot(x_diff, exact_solution, label = 'Exact solution')
plt.legend()
plt.show()

# Compute max error between
# numerical derivative and exact solution
max_error = max(abs(exact_solution - forward_diff))
print(max_error)


#H2

import numpy as np
import matplotlib.pyplot as plt
plt.style.use('seaborn-poster')

# step size
h = 0.1
print('h:', h)
# define x axis
x = np.arange(0, 2*np.pi, h)
x2 = np.arange(0, 2*np.pi, h)
for i in x:
    x = x + x2
print('x:',x)
# compute function, y axis
y = np.cos(x)
print('y:',y)

# compute list of forward differences
forward_diff = np.diff(y)/h
print('forward_diff:', forward_diff)
# compute corresponding x axis
x_diff = x[:-1:]
print('x_diff:',x_diff)
# compute exact solution
exact_solution = -np.sin(x_diff) #derivative
print('exact_solution:', exact_solution)

# Plot representation
plt.figure(figsize = (12, 8))
plt.plot(x_diff, forward_diff, '--', label = 'Finite difference approximation')
plt.plot(x_diff, exact_solution, label = 'Exact solution')
plt.legend()
plt.show()

# Compute max error between
# numerical derivative and exact solution
max_error = max(abs(exact_solution - forward_diff))
print(max_error)



