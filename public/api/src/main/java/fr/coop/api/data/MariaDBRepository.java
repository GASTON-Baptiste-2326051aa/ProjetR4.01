package fr.coop.api.data;

import java.sql.*;

public class MariaDBRepository {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public MariaDBRepository() {
        this.url = "jdbc:mariadb://mysql-cooperativejavaiutaix.alwaysdata.net/cooperativejavaiutaix_bd";
        this.user = "405910";
        this.password = "azerty123456789_";
        this.connection = createConnection();
    }

    public MariaDBRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connection = createConnection();
    }

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

    public Connection getConnection() {
        if (this.connection == null) {
            this.connection = createConnection();
        }
        return this.connection;
    }
}