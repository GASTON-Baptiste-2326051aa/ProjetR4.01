package fr.coop.order.data;

import fr.coop.order.domain.Order;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accèder aux commandes stockées dans une base de données Mariadb
 */
public class OrderRepositoryMariadb implements OrderRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection ;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public OrderRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Order getOrder(String id) {

        Order selectedOrder = null;

        String query = "SELECT * FROM `ORDER` LEFT JOIN ORDER_CONTENT ON `ORDER`.ID = ORDER_CONTENT.ORDER_ID WHERE `ORDER`.ID=?";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(id));

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            if (!result.isBeforeFirst()) { // Vérifie si le ResultSet contient des lignes
                return selectedOrder;
            }

            ArrayList<String> cartIds = new ArrayList<>();

            while (result.next()) {
                if (selectedOrder == null) {
                    selectedOrder = new Order(result.getString("id"), result.getString("user_id"),
                            result.getString("limit_date"), result.getString("relay_address"),
                            result.getBoolean("is_validate"));
                }
                cartIds.add(result.getString("cart_id"));
            }

            if (selectedOrder != null) { // Ajouter la dernière commande
                selectedOrder.setCartId(cartIds.toArray(new String[0]));
                cartIds.clear();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedOrder;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> listOrders = new ArrayList<>();

        String query = "SELECT * FROM `ORDER` LEFT JOIN ORDER_CONTENT ON `ORDER`.ID = ORDER_CONTENT.ORDER_ID ORDER BY `ORDER`.ID";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {

            ResultSet result = ps.executeQuery();

            if (!result.isBeforeFirst()) { // Vérifie si le ResultSet contient des lignes
                return listOrders;
            }

            String currentOrderId = "";
            Order currentOrder = null;
            ArrayList<String> cartIds = new ArrayList<>();

            while (result.next()) {
                String orderId = result.getString("id");

                if (!orderId.equals(currentOrderId)) {
                    if (currentOrder != null) {
                        currentOrder.setCartId(cartIds.toArray(new String[0]));
                        cartIds.clear();
                    }
                    currentOrderId = orderId;
                    currentOrder = new Order(currentOrderId, result.getString("user_id"),
                            result.getString("limit_date"), result.getString("relay_address"),
                            result.getBoolean("is_validate"));
                    listOrders.add(currentOrder);
                }
                cartIds.add(result.getString("cart_id"));
            }

            if (!cartIds.isEmpty() && currentOrder != null) { // Ajouter la dernière commande
                currentOrder.setCartId(cartIds.toArray(new String[0]));
                cartIds.clear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return listOrders;
    }


    @Override
    public boolean addOrder( String id, String userId, String date, String relayAddress, String valid) {
        String query = "INSERT INTO `ORDER` (ID, USER_ID, LIMIT_DATE, RELAY_ADDRESS, IS_VALIDATE) VALUES (?,?,?,?,?)";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));
            ps.setInt(2, Integer.parseInt(userId));
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, relayAddress );
            ps.setBoolean(5, Boolean.parseBoolean(valid));

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }


    @Override
    public boolean updateOrder( String id, String userId, String date, String relayAddress, String valid) {
        String query = "UPDATE `ORDER` SET USER_ID=?, LIMIT_DATE=?, RELAY_ADDRESS=?, IS_VALIDATE=? WHERE ID=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(userId));
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, relayAddress );
            ps.setBoolean(4, Boolean.parseBoolean(valid));
            ps.setInt(5, Integer.parseInt(id));

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }


    @Override
    public boolean deleteOrder(String id) {

        // suppression des relations stockant les paniers présents dans la commande
        String query = "DELETE FROM ORDER_CONTENT WHERE ORDER_ID=?";
        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));

            // exécution de la requête
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // suppression de la commande
        query = "DELETE FROM `ORDER` WHERE ID=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }


    @Override
    public boolean addCart(String id, String cartId) {
        String query = "INSERT INTO ORDER_CONTENT (ORDER_ID, CART_ID) VALUES (?,?)";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));
            ps.setInt(2, Integer.parseInt(cartId));

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }


    @Override
    public boolean removeCart(String id, String cartId) {
        String query = "DELETE FROM ORDER_CONTENT WHERE ORDER_ID=? AND CART_ID=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, Integer.parseInt(id));
            ps.setInt(2, Integer.parseInt(cartId));

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }
}