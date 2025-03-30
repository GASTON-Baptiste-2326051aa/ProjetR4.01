package fr.coop.api.data.interfaces;

import fr.coop.api.domain.User;

import java.util.List;

public interface UserRepositoryInterface {
    int createUser(String firstName, String name, String password);
    boolean updateUser(int userId, String firstName, String name, String newPassword);
    boolean deleteUser(int userId);
    User getUser(int userId);
    List<User> getAllUsers();
}