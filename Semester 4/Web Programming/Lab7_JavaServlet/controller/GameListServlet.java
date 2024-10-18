package com.example.labjspservlet.controller;

import com.example.labjspservlet.dao.GameMoveDAO;
import com.example.labjspservlet.dao.GameStateDAO;
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

@WebServlet(name = "GameListServlet", value = "/GameListServlet")
public class GameListServlet extends HttpServlet {
    private static final int ITEMS_PER_PAGE = 4;
    private GameMoveDAO gameMoveDAO;
    private static final Logger logger = Logger.getLogger(GameServlet.class.getName());

    public void init() {
        Connection conn = (Connection) getServletContext().getAttribute("DBConn");
        gameMoveDAO = new GameMoveDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int minGid=Integer.parseInt(request.getParameter("minGid"));
            int maxGid=Integer.parseInt(request.getParameter("maxGid"));
            int sortMoves=Integer.parseInt(request.getParameter("sort"));

            List<GameMove> moves = gameMoveDAO.getAllFilteredMoves(minGid,maxGid,sortMoves);

            //List<GameMove> moves = gameMoveDAO.getAllMoves();
            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            int start = (page - 1) * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, moves.size());
            List<GameMove> paginatedMoves = moves.subList(start, end);

            //normal list
            /*request.setAttribute("items", paginatedMoves);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil((double) moves.size() / ITEMS_PER_PAGE));

            RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
            dispatcher.forward(request, response);*/

            // ajax method
            response.setContentType("application/json");
            JSONArray jsonMoves = new JSONArray();
            for (GameMove move : paginatedMoves) {
                JSONObject jObj = new JSONObject();
                jObj.put("gid", move.getGid());
                jObj.put("gsid", move.getGsid());
                jObj.put("move", move.getMove());
                jObj.put("moveTime", move.getMoveTime().toString());
                jsonMoves.add(jObj);
            }

            // PrintWriter out = response.getWriter();
            // out.println(jsonMoves.toJSONString());
            // out.flush();

            //request.setAttribute("items", jsonMoves);
            //request.setAttribute("currentPage", page);
            //request.setAttribute("totalPages", (int) Math.ceil((double) moves.size() / ITEMS_PER_PAGE));

            response.setContentType("application/json");
            response.getWriter().write(jsonMoves.toJSONString());
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }

    /*private List<GameMove> generateItems() {
        List<GameMove> items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items.add(new GameMove(i, "Item " + i));
        }
        return items;
    }*/
}