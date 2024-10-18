package com.example.labjspservlet.controller;

import com.example.labjspservlet.dao.GameMoveDAO;
import com.example.labjspservlet.model.GameMove;
import com.example.labjspservlet.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet(name = "DeleteGameMoveServlet", value = "/DeleteGameMoveServlet")
public class DeleteGameMoveServlet extends HttpServlet {
    private GameMoveDAO gameMoveDAO;
    private static final Logger logger = Logger.getLogger(GameServlet.class.getName());

    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConn");
        gameMoveDAO = new GameMoveDAO(conn);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int gid = Integer.parseInt(request.getParameter("gid"));
            gameMoveDAO.deleteMove(gid);
            response.sendRedirect("list.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error adding game move", e);
        }
    }
}
