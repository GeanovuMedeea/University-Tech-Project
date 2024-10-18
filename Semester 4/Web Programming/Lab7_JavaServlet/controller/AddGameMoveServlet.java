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

@WebServlet(name = "AddGameMoveServlet", value = "/AddGameMoveServlet")
public class AddGameMoveServlet extends HttpServlet {
    private GameMoveDAO gameMoveDAO;
    private static final Logger logger = Logger.getLogger(GameServlet.class.getName());

    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConn");
        gameMoveDAO = new GameMoveDAO(conn);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int gsid = Integer.parseInt(request.getParameter("gsid"));
            String move = request.getParameter("move");
            gameMoveDAO.recordMove(gsid, move);
            response.sendRedirect("list.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error adding game move", e);
        }
    }
}
