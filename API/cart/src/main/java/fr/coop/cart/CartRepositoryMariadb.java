package fr.coop.cart;

import java.io.Closeable;
import java.sql.*;
import java.util.*;

/**
 * Classe permettant d'accéder aux paniers stockés dans une base de données MariaDB
 */
public class CartRepositoryMariadb implements CartRepositoryInterface, Closeable {

    /**
     * Accès à la base de données
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection informations de connexion à la base de données
     * @param user identifiant de connexion
     * @param pwd mot de passe
     */
    public CartRepositoryMariadb(String infoConnection, String user, String pwd) throws SQLException,
            ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    /**
     * Fermeture de la connexion à la base de données
     */
    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Récupère un panier spécifique à partir de son identifiant
     * @param id identifiant du panier
     * @return l'objet Cart correspondant ou null si non trouvé
     */
    @Override
    public Cart getCart(String id) {

        Cart selectedCart = null;

        String query = "SELECT * FROM CART JOIN CART_CONTENT ON CART.ID = CART_CONTENT.CART_ID WHERE CART.ID=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(id));

            ResultSet result = ps.executeQuery();

            if (!result.isBeforeFirst()) {
                return null;
            }

            ArrayList<String> productIds = new ArrayList<>();

            while (result.next()) {
                if (selectedCart == null) {
                    selectedCart = new Cart(result.getString("id"), result.getString("name"),
                            result.getString("description"), result.getDouble("price"),
                            result.getInt("available_quantity"));
                }
                productIds.add(result.getString("product_id"));
            }

            if (selectedCart != null) {
                selectedCart.setProductId(productIds.toArray(new String[0]));
                productIds.clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedCart;
    }

    /**
     * Récupère la liste de tous les paniers
     * @return une liste contenant tous les objets Cart
     */
    @Override
    public ArrayList<Cart> getAllCarts() {
        ArrayList<Cart> listCarts = new ArrayList<>();

        String query = "SELECT * FROM CART JOIN CART_CONTENT ON CART.ID = CART_CONTENT.CART_ID ORDER BY CART.ID";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {

            ResultSet result = ps.executeQuery();

            if (!result.isBeforeFirst()) {
                return listCarts;
            }

            String currentCartId = "";
            Cart currentCart = null;
            ArrayList<String> productIds = new ArrayList<>();

            while (result.next()) {
                String cartId = result.getString("id");

                if (!cartId.equals(currentCartId)) {
                    if (currentCart != null) {
                        currentCart.setProductId(productIds.toArray(new String[0]));
                        productIds.clear();
                    }
                    currentCartId = cartId;
                    currentCart = new Cart(currentCartId, result.getString("name"),
                            result.getString("description"), result.getDouble("price"),
                            result.getInt("available_quantity"));
                    listCarts.add(currentCart);
                }
                productIds.add(result.getString("product_id"));
            }

            if (!productIds.isEmpty() && currentCart != null) {
                currentCart.setProductId(productIds.toArray(new String[0]));
                productIds.clear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listCarts;
    }

    @Override
    public boolean addCart(String id, String name, String description, double price, int available_quantity) {
        String query = "INSERT INTO CART (ID, NAME, DESCRIPTION, PRICE, AVAILABLE_QUANTITY) VALUES (?,?,?,?,?)";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));
            ps.setString(2, name);
            ps.setString(3, description);
            ps.setDouble(4, price);
            ps.setInt(5, available_quantity);

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    @Override
    public boolean updateCart(String id, String name, String description, double price, int available_quantity) {
        String query = "UPDATE CART SET NAME=?, DESCRIPTION=?, PRICE=?, AVAILABLE_QUANTITY=? WHERE ID=?";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setInt(4, available_quantity);
            ps.setInt(5, Integer.parseInt(id));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    @Override
    public boolean deleteCart(String id) {
        String query = "DELETE FROM CART WHERE ID=?";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    @Override
    public boolean addProduct(String cartId, String productId) {
        String query = "INSERT INTO CART_CONTENT (CART_ID, PRODUCT_ID) VALUES (?,?)";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(cartId));
            ps.setInt(2, Integer.parseInt(productId));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    @Override
    public boolean removeProduct(String cartId, String productId) {
        String query = "DELETE FROM CART_CONTENT WHERE CART_ID=? AND PRODUCT_ID=?";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(cartId));
            ps.setInt(2, Integer.parseInt(productId));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }


}

