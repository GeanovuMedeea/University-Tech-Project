package com.example.labjspservlet.controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.sql.*;
import com.example.labjspservlet.dao.UserDAO;
import com.example.labjspservlet.model.User;
import java.io.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConn");
        userDAO = new UserDAO(conn);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.getUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                //response.sendRedirect("game.jsp");
                //response.sendRedirect(request.getContextPath() + "/GameServlet");
                //response.sendRedirect(request.getContextPath() + "/GameListServlet");
                response.sendRedirect("list.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Invalid username or password\"");
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Login error");
            response.sendRedirect("login.jsp");
            //throw new IOException("Database access error", e);
        }
    }

    public void destroy() {
    }
}
