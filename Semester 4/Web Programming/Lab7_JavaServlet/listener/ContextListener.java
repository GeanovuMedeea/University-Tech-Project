package com.example.labjspservlet.listener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.*;
import java.sql.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ContextListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dbConnectionString = sce.getServletContext().getInitParameter("DBConnection");
        String username = sce.getServletContext().getInitParameter("DBUsername");
        String password = sce.getServletContext().getInitParameter("DBPassword");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://" + dbConnectionString, username, password);
            sce.getServletContext().setAttribute("DBConn", conn);
            logger.info("Database connection established successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Failed to connect to the database", e);
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection conn = (Connection) sce.getServletContext().getAttribute("DBConnection");

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.info("Failed to remove driver for database" + e);
            }
        }

        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
                    DriverManager.deregisterDriver(driver);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to remove driver for database", e);
        }

    }
}