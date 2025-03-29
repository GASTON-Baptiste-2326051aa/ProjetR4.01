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
    public CartRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException,
            java.lang.ClassNotFoundException {
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
    public Cart getCart(int id) {
        Cart selectedCart = null;
        String query = "SELECT * FROM Cart WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                String description = result.getString("description");
                int price = result.getInt("price");
                int available_quantity = result.getInt("available_quantity");

                selectedCart = new Cart(id, name, description, price, available_quantity);
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
        String query = "SELECT * FROM Cart";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                int price = result.getInt("price");
                int available_quantity = result.getInt("available_quantity");

                listCarts.add(new Cart(id, name, description, price, available_quantity));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCarts;
    }

    /**
     * Met à jour un panier existant dans la base de données
     * @param id identifiant du panier à mettre à jour
     * @param name nouveau nom du panier
     * @param description nouvelle description du panier
     * @param price nouveau prix du panier
     * @param available_quantity nouvelle quantité disponible
     * @return true si la mise à jour a réussi, false sinon
     */
    @Override
    public boolean updateCart(int id, String name, String description, int price, int available_quantity) {
        String query = "UPDATE Cart SET name=?, description=?, price=?, available_quantity=? WHERE id=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, price);
            ps.setInt(4, available_quantity);
            ps.setInt(5, id);

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowModified != 0);
    }
}
