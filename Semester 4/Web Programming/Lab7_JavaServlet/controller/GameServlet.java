package com.example.labjspservlet.controller;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.labjspservlet.dao.GameStateDAO;
import com.example.labjspservlet.dao.GameMoveDAO;
import com.example.labjspservlet.model.GameMove;
import com.example.labjspservlet.listener.ContextListener;
import com.example.labjspservlet.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "GameServlet", value = "/GameServlet")
public class GameServlet extends HttpServlet {
    private GameStateDAO gameStateDAO;
    private GameMoveDAO gameMoveDAO;
    private static final Logger logger = Logger.getLogger(GameServlet.class.getName());


    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConn");
        gameStateDAO = new GameStateDAO(conn);
        gameMoveDAO = new GameMoveDAO(conn);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int gameStateId;
        try {
            gameStateId = gameStateDAO.startNewGame(user.getUid());
            session.setAttribute("gameStateId", gameStateId);
            session.setAttribute("startTime", System.currentTimeMillis());
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer gameStateId = (Integer) session.getAttribute("gameStateId");
        //logger.info("gsid:" + gameStateId);

        if (gameStateId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        String moves = request.getParameter("moves");
        try {
            //logger.info(moves);
            //logger.info(moves.getClass().getName());
            JsonNode movesArray = mapper.readTree(moves);

            for (int i = 0; i < movesArray.size(); i++) {
                JsonNode move = movesArray.get(i);
                gameMoveDAO.recordMove(gameStateId, move.get("direction").asText());

            }

            long startTime = (long) session.getAttribute("startTime");
            int elapsedTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
            gameStateDAO.updateElapsedTime(gameStateId, elapsedTime);
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }

        User user = (User) session.getAttribute("user");
        try {
            gameStateId = gameStateDAO.startNewGame(user.getUid());
            session.setAttribute("gameStateId", gameStateId);
            session.setAttribute("startTime", System.currentTimeMillis());
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }

        response.sendRedirect("game.jsp");
    }
}
