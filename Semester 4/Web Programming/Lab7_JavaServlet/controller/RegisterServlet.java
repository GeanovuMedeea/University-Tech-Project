package com.example.labjspservlet.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import com.example.labjspservlet.dao.UserDAO;
import java.io.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConn");
        userDAO = new UserDAO(conn);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            boolean isRegistered = userDAO.registerUser(username, password);
            if (isRegistered) {
                response.sendRedirect("login.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Register error");
                response.sendRedirect("register.jsp");
            }
        } catch (SQLException e) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Username already taken");
            response.sendRedirect("register.jsp");
            //throw new IOException("Database access error", e);
        }
    }

    public void destroy() {
    }
}
