package fr.coop.api.data.interfaces;

import fr.coop.api.domain.Product;

import java.util.List;

public interface ProductRepositoryInterface {
    int createProduct(String name, String description, double availableStock, String unitType);
    boolean updateProduct(int productId, String newName, String newDescription, double newAvailableStock, String newUnitType);
    boolean deleteProduct(int productId);
    Product getProduct(int productId);
    List<Product> getAllProducts();
}
