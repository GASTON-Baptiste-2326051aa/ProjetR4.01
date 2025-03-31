package fr.coop.api.data.interfaces;

import fr.coop.api.domain.Product;

import java.util.List;

public interface ProductRepositoryInterface {
    String createProduct(String name, String description, double availableStock, String unitType);

    boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType);

    boolean deleteProduct(String productId);

    Product getProduct(String productId);
    List<Product> getAllProducts();
}
