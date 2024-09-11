package timecapsule;

import java.sql.*;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/timecapsule";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public void saveLetter(String email, String message, String deliveryDate, String style, String audience, String multimediaPath) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO letters (user_id, message, delivery_date, style, audience, multimedia_path) VALUES ((SELECT user_id FROM users WHERE email = ?), ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, message);
                stmt.setString(3, deliveryDate);
                stmt.setString(4, style);
                stmt.setString(5, audience);
                stmt.setString(6, multimediaPath);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMultimediaPath(String filePath) {
        // Implement multimedia saving logic here
    }

    public boolean validateUser(String email, String password) {
        boolean isValid = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        isValid = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
