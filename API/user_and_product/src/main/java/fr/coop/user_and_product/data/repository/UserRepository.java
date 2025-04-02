package fr.coop.user_and_product.data.repository;

import fr.coop.user_and_product.data.interfaces.UserRepositoryInterface;
import fr.coop.user_and_product.domain.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour le dépôt d'utilisateurs
 */
@ApplicationScoped
public class UserRepository extends Repository implements UserRepositoryInterface {
    public UserRepository() {
        super();
    }

    /*public UserRepository(String infoConnection, String user, String password) {
        super(infoConnection, user, password);
    }*/

    /**
     * Crée un nouvel utilisateur avec les détails donnés.
     *
     * @param firstName Le prénom de l'utilisateur.
     * @param name      Le nom de l'utilisateur.
     * @param password  Le mot de passe de l'utilisateur.
     * @return L'ID de l'utilisateur créé.
     */
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

        return String.valueOf(userId);
    }

    /**
     * Met à jour les détails d'un utilisateur existant.
     *
     * @param userId      L'ID de l'utilisateur à mettre à jour.
     * @param firstName   Le nouveau prénom de l'utilisateur.
     * @param name        Le nouveau nom de l'utilisateur.
     * @param newPassword Le nouveau mot de passe de l'utilisateur.
     * @return true si la mise à jour a réussi, false sinon.
     */
    @Override
    public boolean updateUser(String userId, String firstName, String name, String newPassword) {
        String query = "UPDATE USER SET first_name = ?, name = ?, password = ? WHERE id = ?";
        boolean updated = false;
        int id = Integer.parseInt(userId);

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

    /**
     * Supprime un utilisateur avec l'ID donné.
     *
     * @param userId L'ID de l'utilisateur à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean deleteUser(String userId) {
        String query = "DELETE FROM USER WHERE id = ?";
        boolean deleted = false;
        int id = Integer.parseInt(userId);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }

        return deleted;
    }

    /**
     * Récupère un utilisateur avec l'ID donné.
     *
     * @param userId L'ID de l'utilisateur à récupérer.
     * @return L'utilisateur avec l'ID donné, ou null s'il n'est pas trouvé.
     */
    @Override
    public User getUser(String userId) {
        User user = null;
        String query = "SELECT * FROM USER WHERE id = ?";
        int id = Integer.parseInt(userId);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        String.valueOf(resultSet.getInt("id")),
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

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Une liste de tous les utilisateurs.
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM USER";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User(
                        String.valueOf(resultSet.getInt("id")),
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

    /**
     * Vérifie si un utilisateur existe avec l'ID et le mot de passe donné.
     *
     * @param userId   L'ID de l'utilisateur à vérifier.
     * @param password Le mot de passe de l'utilisateur à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    @Override
    public boolean isUserExist(String userId, String password) {
        String query = "SELECT * FROM USER WHERE id = ? AND password = ?";
        boolean exists = false;
        int id = Integer.parseInt(userId);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }

        return exists;
    }
}