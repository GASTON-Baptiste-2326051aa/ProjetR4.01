package fr.coop.user_and_product.api;

import fr.coop.user_and_product.data.interfaces.UserRepositoryInterface;
import fr.coop.user_and_product.domain.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * API pour le dépôt d'utilisateurs.
 */
public class UserRepositoryAPI extends RepositoryAPI implements UserRepositoryInterface {

    /**
     * Constructeur de l'API UserRepository.
     *
     * @param url L'URL de l'API.
     */
    public UserRepositoryAPI(String url) {
        super(url);
    }

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
        return null;
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
        return false;
    }

    /**
     * Supprime un utilisateur avec l'ID donné.
     *
     * @param userId L'ID de l'utilisateur à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean deleteUser(String userId) {
        return false;
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

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget endPoint = target.path("user/" + userId);
        Response response = endPoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200)
            user = response.readEntity(User.class);

        return user;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Une liste de tous les utilisateurs.
     */
    @Override
    public List<User> getAllUsers() {
        return List.of();
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
        return false;
    }
}