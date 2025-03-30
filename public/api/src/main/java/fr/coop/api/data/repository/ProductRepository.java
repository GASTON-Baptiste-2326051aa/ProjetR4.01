package fr.coop.api.data.repository;

import fr.coop.api.data.interfaces.ProductRepositoryInterface;
import fr.coop.api.domain.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends Repository implements ProductRepositoryInterface {
    public ProductRepository() {
        super();
    }

    /*public ProductRepository(String infoConnection, String user, String password) {
        super(infoConnection, user, password);
    }*/

    @Override
    public int createProduct(String name, String description, double availableStock, String unitType) {
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

        return productId;
    }

    @Override
    public boolean updateProduct(int productId, String newName, String newDescription, double newAvailableStock, String newUnitType) {
        String query = "UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, AVAILABLE_STOCK = ?, UNIT_TYPE = ? WHERE ID = ?";
        boolean updated = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setDouble(3, newAvailableStock);
            preparedStatement.setString(4, newUnitType);
            preparedStatement.setInt(5, productId);
            updated = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }

        return updated;
    }

    @Override
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM PRODUCT WHERE ID = ?";
        boolean deleted = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            deleted = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }

        return deleted;
    }

    @Override
    public Product getProduct(int productId) {
        String query = "SELECT * FROM PRODUCT WHERE ID = ?";
        Product product = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        resultSet.getInt("ID"),
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

    @Override
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM PRODUCT";
        List<Product> products = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("ID"),
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
