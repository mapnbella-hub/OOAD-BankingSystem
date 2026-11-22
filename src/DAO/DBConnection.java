package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Change the database name if yours is different (e.g. banking)
    private static final String URL =
            "jdbc:mysql://localhost:3306/banking ?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";   // XAMPP default is an empty password

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Quick test: run this file to check the connection works
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connected to: " + conn.getCatalog());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}