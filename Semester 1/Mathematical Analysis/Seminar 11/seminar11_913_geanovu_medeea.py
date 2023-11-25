import numpy as np
import sympy as sy
import matplotlib.pyplot as plt

plt.rcParams["figure.figsize"] = (12,7)

def f_formula(A):
    x_not = sy.symbols('x')
    y_not = sy.symbols('y')
    XT = np.array([x_not, y_not])
    X = XT.T
    AX = np.dot(A,X)
    return np.dot(XT, AX) #XT*A*X

def f(x, y, A):
    f_res = f_formula(A)
    x_not = sy.symbols('x')
    y_not = sy.symbols('y')
    return f_res.subs([(x_not, x), (y_not, y)])

def gradient_formula(A):
    x_not = sy.symbols('x')
    y_not = sy.symbols('y')
    return sy.diff(f(x_not,y_not,A), x_not), sy.diff(f(x_not,y_not,A), y_not)

def gradient(x, y, A):
    x_not = sy.symbols('x')
    y_not = sy.symbols('y')
    dx, dy = gradient_formula(A)
    return dx.subs(x_not, x), dy.subs(y_not, y)

def get_z_line_for_function(Xline, Yline, A):
    Zline = []
    l = len(Xline[0])
    for i in range(l):
        z = []
        for j in range(l):
            z.append(f(Xline[i][j], Yline[i][j], A))
        Zline.append(z)
    Zline = np.array(Zline)
    return Zline

def get_z_line_for_gradient_plane(Xline, Yline, A, x0, y0):
    z0 = f(x0, y0, A)
    Zline = []
    l = len(Xline[0])
    for i in range(l):
        z = []
        for j in range(l):
            gr_dx, gr_dy = gradient(x0, y0, A)
            z_val = z0 + gr_dx * (Xline[i][j] - x0) + gr_dy * (Yline[i][j] - y0)
            z.append(z_val)
        Zline.append(z)
    Zline = np.array(Zline)
    return Zline

if __name__ == '__main__':

    A1 = np.array([[-1, 0], [0, -1]])
    A2 = np.array([[-2, 0], [0, -2]])
    A3 = np.array([[1, 0], [0, 1]])
    A4 = np.array([[2, 0], [0, 2]])
    A5 = np.array([[-1, 0], [0, 1]])
    A6 = np.array([[2, 0], [0, -2]])

    fig = plt.figure()
    ax1 = fig.add_subplot(231, projection='3d')
    ax2 = fig.add_subplot(232, projection='3d')
    ax3 = fig.add_subplot(233, projection='3d')
    ax4 = fig.add_subplot(234, projection='3d')
    ax5 = fig.add_subplot(235, projection='3d')
    ax6 = fig.add_subplot(236, projection='3d')

    xline = np.arange(-5, 6, 1)
    yline = np.arange(-5, 6, 1)
    Xline, Yline = np.meshgrid(xline, yline)

    Zline = get_z_line_for_function(Xline, Yline, A1)
    ax1.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='cividis', edgecolor='none', alpha=0.7)
    Zline = get_z_line_for_gradient_plane(Xline, Yline, A1, -1, -1)
    ax1.plot_surface(Xline, Yline, Zline, alpha=0.7, color='cyan')
    z0 = f(-1, -1, A1)
    ax1.scatter(-1, -1, z0, color='black')

    Zline = get_z_line_for_function(Xline, Yline, A2)
    ax4.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='cividis', edgecolor='none', alpha=0.7)
    Zline = get_z_line_for_gradient_plane(Xline, Yline, A2, 1, 1)
    ax4.plot_surface(Xline, Yline, Zline, alpha=0.7, color='cyan')
    z0 = f(1, 1, A2)
    ax4.scatter(1, 1, z0, color='black')

    Zline = get_z_line_for_function(Xline, Yline, A3)
    ax2.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='plasma', edgecolor='none', alpha=0.7)
    Zline = get_z_line_for_gradient_plane(Xline, Yline, A3, -1, -1)
    ax2.plot_surface(Xline, Yline, Zline, alpha=0.7, color='purple')
    z0 = f(-1, -1, A3)
    ax2.scatter(-1, -1, z0, color='black')

    Zline = get_z_line_for_function(Xline, Yline, A4)
    ax5.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='plasma', edgecolor='none', alpha=0.7)
    Zline = get_z_line_for_gradient_plane(Xline, Yline, A4, 1, 1)
    ax5.plot_surface(Xline, Yline, Zline, alpha=0.7, color='purple')
    z0 = f(1, 1, A4)
    ax5.scatter(1, 1, z0, color='black')

    Zline = get_z_line_for_function(Xline, Yline, A5)
    ax3.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='PuRd', edgecolor='none', alpha=0.7)
    Zline = get_z_line_for_gradient_plane(Xline, Yline, A5, -1, -1)
    ax3.plot_surface(Xline, Yline, Zline, alpha=0.7, color='red')
    z0 = f(-1, -1, A5)
    ax3.scatter(-1, -1, z0, color='black')

    Zline = get_z_line_for_function(Xline, Yline, A6)
    ax6.plot_surface(Xline, Yline, Zline, rstride=1, cstride=1, cmap='PuRd', edgecolor='none', alpha=0.7)
    Zline = get_z_line_for_gradient_plane(Xline, Yline, A6, 1, 1)
    ax6.plot_surface(Xline, Yline, Zline, alpha=0.7, color='red')
    z0 = f(1, 1, A6)
    ax6.scatter(1, 1, z0, color='black')

    plt.show()



