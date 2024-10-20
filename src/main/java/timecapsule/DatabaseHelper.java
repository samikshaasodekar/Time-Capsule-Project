package timecapsule;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/timecapsule";
    private static final String USER = "root"; // Your database username
    private static final String PASSWORD = "root"; // Your database password

    public void saveLetter(String email, String message, String deliveryDate, String style, String audience) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
        String checkUserQuery = "SELECT user_id FROM users WHERE email = ?";
        int userId = -1;
        try (PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery)) {
            checkUserStmt.setString(1, email);
            try (ResultSet rs = checkUserStmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }
        }

        if (userId == -1) {
            String insertUserQuery = "INSERT INTO users (email) VALUES (?)";
            try (PreparedStatement insertUserStmt = conn.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertUserStmt.setString(1, email);
                insertUserStmt.executeUpdate();

                // Get the generated user_id
                try (ResultSet generatedKeys = insertUserStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    }
                }
            }
        }

        String insertLetterQuery = "INSERT INTO letters (user_id, message, delivery_date, style, audience) " +
                                   "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertLetterQuery)) {
            stmt.setInt(1, userId);  // Use the correct user_id
            stmt.setString(2, message);
            stmt.setString(3, deliveryDate);
            stmt.setString(4, style);
            stmt.setString(5, audience);
            stmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public boolean validateUser(String email, String password) {
        boolean isValid = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    isValid = rs.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
