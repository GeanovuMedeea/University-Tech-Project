package com.example.labjspservlet.dao;
import java.sql.*;
import com.example.labjspservlet.model.GameMove;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMoveDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(GameMoveDAO.class.getName());

    public GameMoveDAO(Connection conn) {
        this.conn = conn;
    }

    public void recordMove(int gsid, String move) throws SQLException {
        String sql = "INSERT INTO GameMoves (gsid, move, moveTime) VALUES (?, ?, GETDATE())";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gsid);
            stmt.setString(2, move);
            stmt.executeUpdate();
        }
    }

    public List<GameMove> getAllMoves() throws SQLException {
        List<GameMove> moves = new ArrayList<>();
        String sql = "SELECT * FROM GameMoves";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int gid = rs.getInt("gid");
                int gsid = rs.getInt("gsid");
                String move = rs.getString("move");
                Timestamp moveTime = rs.getTimestamp("moveTime");
                GameMove gameMove = new GameMove(gid, gsid, move, moveTime);
                moves.add(gameMove);
            }
        }
        return moves;
    }

    public List<GameMove> getAllFilteredMoves(int minGid, int maxGid, int sortMoves) throws SQLException {
        List<GameMove> moves = new ArrayList<>();
        logger.info("minGid:"+minGid);
        logger.info("maxGid:"+ maxGid);
        logger.info("sortMoves:"+ sortMoves);
        StringBuilder sql = new StringBuilder();

        if(maxGid==0)
            sql.append("SELECT * FROM GameMoves");
        else
            sql.append("SELECT * FROM GameMoves WHERE gid BETWEEN ? AND ?");

        if (sortMoves == 1) {
            sql.append(" ORDER BY move");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            if(maxGid!=0) {
                stmt.setInt(1, minGid);
                stmt.setInt(2, maxGid);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int gid = rs.getInt("gid");
                int gsid = rs.getInt("gsid");
                String move = rs.getString("move");
                Timestamp moveTime = rs.getTimestamp("moveTime");
                GameMove gameMove = new GameMove(gid, gsid, move, moveTime);
                moves.add(gameMove);
            }
        }
        return moves;
    }

    public GameMove getMoveById(int gid) throws SQLException {
        String sql = "SELECT * FROM GameMoves WHERE gid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gid);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int gsid = rs.getInt("gsid");
                    String move = rs.getString("move");
                    Timestamp moveTime = rs.getTimestamp("moveTime");
                    return new GameMove(gid, gsid, move, moveTime);
                } else {
                    return null;
                }
            }
        }
    }

    public void updateMove(int gid, int gsid, String move) throws SQLException {
        String sql = "UPDATE GameMoves SET gsid = ?, move = ?, moveTime = GETDATE() WHERE gid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gsid);
            stmt.setString(2, move);
            stmt.setInt(3, gid);
            stmt.executeUpdate();
        }
    }

    public void deleteMove(int gid) throws SQLException {
        String sql = "DELETE FROM GameMoves WHERE gid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gid);
            stmt.executeUpdate();
        }
    }
}
