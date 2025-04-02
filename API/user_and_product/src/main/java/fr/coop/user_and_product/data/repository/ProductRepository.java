package fr.coop.user_and_product.data.repository;

import fr.coop.user_and_product.data.interfaces.ProductRepositoryInterface;
import fr.coop.user_and_product.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour le dépôt de produits
 */
@ApplicationScoped
public class ProductRepository extends Repository implements ProductRepositoryInterface {
    public ProductRepository() {
        super();
    }

    /*public ProductRepository(String infoConnection, String user, String password) {
        super(infoConnection, user, password);
    }*/

    /**
     * Crée un nouveau produit avec les détails donnés.
     *
     * @param name           Le nom du produit.
     * @param description    La description du produit.
     * @param availableStock Le stock disponible du produit.
     * @param unitType       Le type d'unité du produit.
     * @return L'ID du produit créé.
     */
    @Override
    public String createProduct(String name, String description, double availableStock, String unitType) {
        String query = "INSERT INTO PRODUCT (NAME, DESCRIPTION, AVAILABLE_STOCK, UNIT_TYPE) VALUES (?, ?, ?, ?)";
        int productId = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, availableStock);
            preparedStatement.setString(4, unitType);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
        }

        return String.valueOf(productId);
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
    @Override
    public boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType) {
        String query = "UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, AVAILABLE_STOCK = ?, UNIT_TYPE = ? WHERE ID = ?";
        boolean updated = false;
        int id = Integer.parseInt(productId.substring(1));

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setDouble(3, newAvailableStock);
            preparedStatement.setString(4, newUnitType);
            preparedStatement.setInt(5, id);
            updated = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }

        return updated;
    }

    /**
     * Supprime un produit avec l'ID donné.
     *
     * @param productId L'ID du produit à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean deleteProduct(String productId) {
        String query = "DELETE FROM PRODUCT WHERE ID = ?";
        boolean deleted = false;
        int id = Integer.parseInt(productId.substring(1));

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }

        return deleted;
    }

    /**
     * Récupère un produit avec l'ID donné.
     *
     * @param productId L'ID du produit à récupérer.
     * @return Le produit avec l'ID donné, ou null s'il n'est pas trouvé.
     */
    @Override
    public Product getProduct(String productId) {
        String query = "SELECT * FROM PRODUCT WHERE ID = ?";
        Product product = null;
        int id = Integer.parseInt(productId.substring(1));

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        String.valueOf(resultSet.getInt("ID")),
                        resultSet.getString("NAME"),
                        resultSet.getString("DESCRIPTION"),
                        resultSet.getDouble("AVAILABLE_STOCK"),
                        resultSet.getString("UNIT_TYPE")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting product: " + e.getMessage());
        }

        return product;
    }

    /**
     * Récupère tous les produits.
     *
     * @return Une liste de tous les produits.
     */
    @Override
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM PRODUCT";
        List<Product> products = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(
                        String.valueOf(resultSet.getInt("ID")),
                        resultSet.getString("NAME"),
                        resultSet.getString("DESCRIPTION"),
                        resultSet.getDouble("AVAILABLE_STOCK"),
                        resultSet.getString("UNIT_TYPE")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all products: " + e.getMessage());
        }

        return products;
    }
}