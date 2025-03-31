package fr.coop.api.service;

import fr.coop.api.data.interfaces.ProductRepositoryInterface;
import fr.coop.api.data.interfaces.UserRepositoryInterface;
import fr.coop.api.data.repository.ProductRepository;
import fr.coop.api.data.repository.UserRepository;
import fr.coop.api.domain.Product;
import fr.coop.api.domain.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.List;

/**
 * Service pour la gestion des utilisateurs et des produits.
 */
public class UserAndProductService {
    protected UserRepositoryInterface userRepository;
    protected ProductRepositoryInterface productRepository;

    /**
     * Constructeur par défaut qui initialise les dépôts d'utilisateurs et de produits.
     */
    public UserAndProductService() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
    }

    /*public UserAndProductService(String infoConnection, String user, String password) {
        this.userRepository = new UserRepository(infoConnection, user, password);
        this.productRepository = new ProductRepository(infoConnection, user, password);
    }*/

    /**
     * Crée un nouvel utilisateur.
     *
     * @param firstName Le prénom de l'utilisateur.
     * @param name      Le nom de l'utilisateur.
     * @param password  Le mot de passe de l'utilisateur.
     */
    public void createUser(String firstName, String name, String password) {
        userRepository.createUser(firstName, name, password);
    }

    /**
     * Met à jour les détails d'un utilisateur existant.
     *
     * @param userId      L'ID de l'utilisateur à mettre à jour.
     * @param firstName   Le nouveau prénom de l'utilisateur.
     * @param name        Le nouveau nom de l'utilisateur.
     * @param newPassword Le nouveau mot de passe de l'utilisateur.
     */
    public void updateUser(String userId, String firstName, String name, String newPassword) {
        userRepository.updateUser(userId, firstName, name, newPassword);
    }

    /**
     * Supprime un utilisateur avec l'ID donné.
     *
     * @param userId L'ID de l'utilisateur à supprimer.
     */
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    /**
     * Vérifie si un utilisateur existe avec l'ID et le mot de passe donné.
     *
     * @param userId   L'ID de l'utilisateur à vérifier.
     * @param password Le mot de passe de l'utilisateur à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    public boolean isUserExist(String userId, String password) {
        return userRepository.isUserExist(userId, password);
    }

    /**
     * Récupère un utilisateur avec l'ID donné et le convertit en JSON.
     *
     * @param userId L'ID de l'utilisateur à récupérer.
     * @return L'utilisateur en format JSON, ou un message d'erreur si l'utilisateur n'est pas trouvé.
     */
    public String getUserJSON(String userId) {
        User user = userRepository.getUser(userId);
        String json = null;

        try (Jsonb jsonb = JsonbBuilder.create()) {
            if (user != null) {
                json = jsonb.toJson(user);
            } else {
                json = "{\"error\": \"User not found\"}";
            }
        } catch (Exception e) {
            System.err.println("Error converting user to JSON: " + e.getMessage());
        }

        return json;
    }

    /**
     * Récupère tous les utilisateurs et les convertit en JSON.
     *
     * @return La liste des utilisateurs en format JSON, ou un message d'erreur si aucun utilisateur n'est trouvé.
     */
    public String getAllUsersJSON() {
        List<User> users = userRepository.getAllUsers();
        String json = null;

        try (Jsonb jsonb = JsonbBuilder.create()) {
            if (users != null) {
                json = jsonb.toJson(users);
            } else {
                json = "{\"error\": \"No users found\"}";
            }
        } catch (Exception e) {
            System.err.println("Error converting users to JSON: " + e.getMessage());
        }

        return json;
    }

    /**
     * Crée un nouveau produit.
     *
     * @param name           Le nom du produit.
     * @param description    La description du produit.
     * @param availableStock Le stock disponible du produit.
     * @param unitType       Le type d'unité du produit.
     * @return L'ID du produit créé.
     */
    public String createProduct(String name, String description, double availableStock, String unitType) {
        return productRepository.createProduct(name, description, availableStock, unitType);
    }

    /**
     * Met à jour les détails d'un produit existant.
     *
     * @param productId         L'ID du produit à mettre à jour.
     * @param newName           Le nouveau nom du produit.
     * @param newDescription    La nouvelle description du produit.
     * @param newAvailableStock Le nouveau stock disponible du produit.
     * @param newUnitType       Le nouveau type d'unité du produit.
     * @return true si la mise à jour a réussi, false sinon.
     */
    public boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType) {
        return productRepository.updateProduct(productId, newName, newDescription, newAvailableStock, newUnitType);
    }

    /**
     * Supprime un produit avec l'ID donné.
     *
     * @param productId L'ID du produit à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    public boolean deleteProduct(String productId) {
        return productRepository.deleteProduct(productId);
    }

    /**
     * Récupère un produit avec l'ID donné et le convertit en JSON.
     *
     * @param productId L'ID du produit à récupérer.
     * @return Le produit en format JSON, ou un message d'erreur si le produit n'est pas trouvé.
     */
    public String getProductJSON(String productId) {
        Product product = productRepository.getProduct(productId);
        String json = null;

        try (Jsonb jsonb = JsonbBuilder.create()) {
            if (product != null) {
                json = jsonb.toJson(product);
            } else {
                json = "{\"error\": \"Product not found\"}";
            }
        } catch (Exception e) {
            System.err.println("Error converting product to JSON: " + e.getMessage());
        }

        return json;
    }

    /**
     * Récupère tous les produits et les convertit en JSON.
     *
     * @return La liste des produits en format JSON, ou un message d'erreur si aucun produit n'est trouvé.
     */
    public String getAllProductsJSON() {
        List<Product> products = productRepository.getAllProducts();
        String json = null;

        try (Jsonb jsonb = JsonbBuilder.create()) {
            if (products != null) {
                json = jsonb.toJson(products);
            } else {
                json = "{\"error\": \"No products found\"}";
            }
        } catch (Exception e) {
            System.err.println("Error converting products to JSON: " + e.getMessage());
        }

        return json;
    }
}