package fr.coop.api.service;

import fr.coop.api.data.interfaces.ProductRepositoryInterface;
import fr.coop.api.data.interfaces.UserRepositoryInterface;
import fr.coop.api.data.repository.ProductRepository;
import fr.coop.api.data.repository.UserRepository;
import fr.coop.api.domain.Product;
import fr.coop.api.domain.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.List;

@ApplicationScoped
public class UserAndProductService {
    protected UserRepositoryInterface userRepository;
    protected ProductRepositoryInterface productRepository;

    public UserAndProductService() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
    }

    /*public UserAndProductService(String infoConnection, String user, String password) {
        this.userRepository = new UserRepository(infoConnection, user, password);
        this.productRepository = new ProductRepository(infoConnection, user, password);
    }*/

    public void createUser(String firstName, String name, String password) {
        userRepository.createUser(firstName, name, password);
    }

    public void updateUser(String userId, String firstName, String name, String newPassword) {
        userRepository.updateUser(userId, firstName, name, newPassword);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

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

    public String createProduct(String name, String description, double availableStock, String unitType) {
        return productRepository.createProduct(name, description, availableStock, unitType);
    }

    public boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType) {
        return productRepository.updateProduct(productId, newName, newDescription, newAvailableStock, newUnitType);
    }

    public boolean deleteProduct(String productId) {
        return productRepository.deleteProduct(productId);
    }

    public String getProductJSON(String productId) {
        return productRepository.getProduct(productId).toString();
    }

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
