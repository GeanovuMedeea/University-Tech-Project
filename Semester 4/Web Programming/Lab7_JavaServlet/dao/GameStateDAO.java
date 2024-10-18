package com.example.labjspservlet.dao;
import java.sql.*;
import java.util.logging.Logger;

import com.example.labjspservlet.listener.ContextListener;
import com.example.labjspservlet.model.GameState;

public class GameStateDAO {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(GameStateDAO.class.getName());

    public GameStateDAO(Connection conn) {
        this.conn = conn;
    }

    public int startNewGame(int uid) throws SQLException {
        logger.info("UID: " + uid);
        String sql = "INSERT INTO GameState (uid, startTime, elapsedTime) VALUES (?, GETDATE(), 0)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, uid);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public void updateElapsedTime(int gsid, int elapsedTime) throws SQLException {
        String sql = "UPDATE GameState SET elapsedTime = ? WHERE gsid = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, elapsedTime);
            stmt.setInt(2, gsid);
            stmt.executeUpdate();
        }
    }
}
