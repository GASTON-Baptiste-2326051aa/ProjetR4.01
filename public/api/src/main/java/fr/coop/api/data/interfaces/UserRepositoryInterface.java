package fr.coop.api.data.interfaces;

import fr.coop.api.domain.User;

import java.util.List;

public interface UserRepositoryInterface {
    String createUser(String firstName, String name, String password);

    boolean updateUser(String userId, String firstName, String name, String newPassword);

    boolean deleteUser(String userId);

    User getUser(String userId);
    List<User> getAllUsers();
}