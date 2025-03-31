package fr.coop.api.data.repository;

import fr.coop.api.data.interfaces.UserRepositoryInterface;
import fr.coop.api.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends Repository implements UserRepositoryInterface {
    public UserRepository() {
        super();
    }

    /*public UserRepository(String infoConnection, String user, String password) {
        super(infoConnection, user, password);
    }*/

    @Override
    public String createUser(String firstName, String name, String password) {
        String query = "INSERT INTO USER (FIRST_NAME, NAME, PASSWORD) VALUES (?, ?, ?)";
        int userId = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }

        return String.format("U%d", userId);
    }

    @Override
    public boolean updateUser(String userId, String firstName, String name, String newPassword) {
        String query = "UPDATE USER SET first_name = ?, name = ?, password = ? WHERE id = ?";
        boolean updated = false;
        int id;

        if (userId.charAt(0) == 'U') {
            id = Integer.parseInt(userId.substring(1));
        } else {
            throw new IllegalArgumentException("Invalid user ID format");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, newPassword);
            preparedStatement.setInt(4, id);
            updated = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }

        return updated;
    }

    @Override
    public boolean deleteUser(String userId) {
        String query = "DELETE FROM USER WHERE id = ?";
        boolean deleted = false;
        int id;

        if (userId.charAt(0) == 'U') {
            id = Integer.parseInt(userId.substring(1));
        } else {
            throw new IllegalArgumentException("Invalid user ID format");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }

        return deleted;
    }

    @Override
    public User getUser(String userId) {
        User user = null;
        String query = "SELECT * FROM USER WHERE id = ?";
        int id;

        if (userId.charAt(0) == 'U') {
            id = Integer.parseInt(userId.substring(1));
        } else {
            throw new IllegalArgumentException("Invalid user ID format");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        String.format("U%d", resultSet.getInt("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User(
                        String.format("U%d", resultSet.getInt("id")),
                        resultSet.getString("first_name"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
        }

        return users;
    }
}
