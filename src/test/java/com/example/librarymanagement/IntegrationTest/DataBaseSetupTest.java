package com.example.librarymanagement.IntegrationTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseSetupTest {
    protected static final String URL = "jdbc:mysql://localhost:3306/librarymanagementDb";
    protected static final String USER = "root";
    protected static final String PASSWORD = "";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    protected void executeUpdate(String sql) throws SQLException {
        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    protected void cleanupDatabase() throws SQLException {
        // Add statements to clean up your test database
        executeUpdate("DELETE FROM Transaction");
        executeUpdate("DELETE FROM Books");
        executeUpdate("DELETE FROM Patron");
        executeUpdate("DELETE FROM Librarian");
    }
}