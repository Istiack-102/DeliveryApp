package Rider;
import Utils.DBUtil;
import Utils.PasswordUtil;
import Utils.ValidNumberChecker;
import java.sql.*;

public class RiderDB {

    // Method to check if the username is available
    public static boolean isUsernameAvailable(String username) {
        String query = "SELECT * FROM Riders WHERE username = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return !rs.next();  // If no results are returned, username is available
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to validate rider login
    public static boolean validateRiderLogin(String username, String password) {
        String query = "SELECT * FROM Riders WHERE username = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(PasswordUtil.hashPassword(password));  // Compare hashed password
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to register a new rider
    public static boolean registerRider(String username, String password, String name, String phone, String vehicleInfo) {
        // Check if the username is available
        if (!isUsernameAvailable(username)) {
            return false;  // Username is already taken
        }

        // Validate the phone number using ValidNumberChecker
        if (!ValidNumberChecker.isValidBangladeshPhoneNumber(phone)) {
            return false;  // Invalid phone number format
        }

        // Hash the password before storing it
        String hashedPassword = PasswordUtil.hashPassword(password);

        String query = "INSERT INTO Riders (username, password, name, phone, vehicle_info) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);  // Store the hashed password
            stmt.setString(3, name);
            stmt.setString(4, phone);
            stmt.setString(5, vehicleInfo);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
