package fr.coop.api.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe pour la gestion de la connexion à la base de données MariaDB
 */
public class MariaDBRepository {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    /**
     * Constructeur par défaut qui initialise la connexion avec les paramètres par défaut.
     */
    public MariaDBRepository() {
        this.url = "jdbc:mariadb://mysql-cooperativejavaiutaix.alwaysdata.net/cooperativejavaiutaix_bd";
        this.user = "405910";
        this.password = "azerty123456789_";
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
}