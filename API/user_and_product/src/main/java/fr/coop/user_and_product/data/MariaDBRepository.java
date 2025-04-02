package fr.coop.user_and_product.data;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe pour la gestion de la connexion à la base de données MariaDB
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
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * @throws Exception if this resource cannot be closed
     * @apiNote While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
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