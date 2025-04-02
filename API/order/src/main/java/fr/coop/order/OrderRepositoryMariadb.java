package fr.coop.order;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
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

        String query = "SELECT * FROM Order WHERE id=? LEFT JOIN Order_content ON Order.id = Order_content.Order_id";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, id);

            // exécution de la requête
            ResultSet result = ps.executeQuery();
            if (result.getFetchSize() == 0) {
                return selectedOrder;
            }

            String[] cartId = new String[result.getFetchSize()];
            String tmp = "";
            int index = 0;

            // récupération des tuples résultats
            // (si l'identifiant de la commande est valide)
            while ( result.next() )
            {
                tmp = result.getString("cart_id");
                if (tmp != null) {
                    cartId[index] = tmp;
                    ++index;
                }
            }

            result.previous();

            String userId = result.getString("user_id");
            String relayAddress = result.getString("relay_address");
            String date = result.getString("limit_date");
            boolean valid = result.getBoolean("is_validate");

            selectedOrder = new Order(id, userId, date, relayAddress, valid);
            selectedOrder.setCartId(cartId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedOrder;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> listOrders = new ArrayList<>();

        String query = "SELECT * FROM Order WHERE id=? LEFT JOIN Order_content ON Order.id = Order_content.Order_id ORDER BY id";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){

            // exécution de la requête
            ResultSet result = ps.executeQuery();
            if (result.getFetchSize() == 0) {
                return listOrders;
            }

            String[] cartId = new String[result.getFetchSize()];
            String tmp = "";
            String currentOrderId = "";
            int index = 0;

            // récupération des tuples résultats
            // (si l'identifiant de la commande est valide)
            while ( result.next() )
            {
                tmp = result.getString("cart_id");
                if (tmp != null) {
                    if (!tmp.equals(currentOrderId)) {
                        result.previous();
                        String id = result.getString("id");
                        String userId = result.getString("user_id");
                        String relayAddress = result.getString("relay_address");
                        String date = result.getString("limit_date");
                        boolean valid = result.getBoolean("is_validate");
                        Order currentOrder = new Order(id, userId, relayAddress, date, valid);
                        currentOrder.setCartId(Arrays.copyOfRange(cartId, 0, index));
                        listOrders.add(currentOrder);
                        index = 0;
                        result.next();
                    }
                    cartId[index] = tmp;
                    ++index;
                }
            }

            result.previous();
            String id = result.getString("id");
            String userId = result.getString("user_id");
            String relayAddress = result.getString("relay_address");
            String date = result.getString("limit_date");
            boolean valid = result.getBoolean("is_validate");
            Order currentOrder = new Order(id, userId, relayAddress, date, valid);
            currentOrder.setCartId(Arrays.copyOfRange(cartId, 0, index));
            listOrders.add(currentOrder);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOrders;
    }

    @Override
    public boolean updateOrder( String id, String userId, String date, String relayAddress, boolean valid) {
        String query = "UPDATE Order SET user_id=?, limit_date=?, relay_address=?, is_validate=? where id=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, userId);
            ps.setString(2, date);
            ps.setString(3, relayAddress );
            ps.setString(4, String.valueOf(valid));
            ps.setString(5, id);

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }
}