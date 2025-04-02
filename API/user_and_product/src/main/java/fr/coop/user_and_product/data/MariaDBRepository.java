package fr.coop.user_and_product.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe pour la gestion de la connexion à la base de données MariaDB.
 */
public class MariaDBRepository implements AutoCloseable {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    /**
     * Constructeur par défaut qui initialise la connexion avec les paramètres par défaut.
     */
    public MariaDBRepository() {
        this.url = "jdbc:mariadb://mysql-cooperativejavaiutaix.alwaysdata.net/cooperativejavaiutaix_bd";
        this.user = "405910_2";
        this.password = "Azerty123456789_";
        this.connection = createConnection();
    }

    /**
     * Constructeur avec paramètres personnalisés pour initialiser la connexion.
     *
     * @param url      L'URL de la base de données.
     * @param user     Le nom d'utilisateur pour la connexion.
     * @param password Le mot de passe pour la connexion.
     */
    public MariaDBRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connection = createConnection();
    }

    /**
     * Crée une connexion à la base de données.
     *
     * @return La connexion à la base de données.
     */
    private Connection createConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère la connexion à la base de données. Si elle n'existe pas, elle est créée.
     *
     * @return La connexion à la base de données.
     */
    public Connection getConnection() {
        if (this.connection == null) {
            this.connection = createConnection();
        }
        return this.connection;
    }

    /**
     * Ferme cette ressource, en abandonnant toutes les ressources sous-jacentes.
     * Cette méthode est invoquée automatiquement sur les objets gérés par l'instruction try-with-resources.
     *
     * @throws Exception si cette ressource ne peut pas être fermée.
     */
    @Override
    public void close() throws Exception {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (Exception e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}