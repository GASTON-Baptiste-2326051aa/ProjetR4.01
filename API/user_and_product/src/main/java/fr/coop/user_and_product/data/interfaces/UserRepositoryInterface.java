package fr.coop.user_and_product.data.interfaces;

import fr.coop.user_and_product.domain.User;

import java.util.List;

/**
 * Interface pour le dépôt d'utilisateurs
 */
public interface UserRepositoryInterface {

    /**
     * Crée un nouvel utilisateur avec les détails donnés.
     *
     * @param firstName Le prénom de l'utilisateur.
     * @param name      Le nom de l'utilisateur.
     * @param password  Le mot de passe de l'utilisateur.
     * @return L'ID de l'utilisateur créé.
     */
    String createUser(String firstName, String name, String password);

    /**
     * Met à jour les détails d'un utilisateur existant.
     *
     * @param userId      L'ID de l'utilisateur à mettre à jour.
     * @param firstName   Le nouveau prénom de l'utilisateur.
     * @param name        Le nouveau nom de l'utilisateur.
     * @param newPassword Le nouveau mot de passe de l'utilisateur.
     * @return true si la mise à jour a réussi, false sinon.
     */
    boolean updateUser(String userId, String firstName, String name, String newPassword);

    /**
     * Supprime un utilisateur avec l'ID donné.
     *
     * @param userId L'ID de l'utilisateur à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    boolean deleteUser(String userId);

    /**
     * Récupère un utilisateur avec l'ID donné.
     *
     * @param userId L'ID de l'utilisateur à récupérer.
     * @return L'utilisateur avec l'ID donné, ou null s'il n'est pas trouvé.
     */
    User getUser(String userId);

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Une liste de tous les utilisateurs.
     */
    List<User> getAllUsers();

    /**
     * Vérifie si un utilisateur existe avec l'ID et le mot de passe donné.
     *
     * @param userId   L'ID de l'utilisateur à vérifier.
     * @param password Le mot de passe de l'utilisateur à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    boolean isUserExist(String userId, String password);
}