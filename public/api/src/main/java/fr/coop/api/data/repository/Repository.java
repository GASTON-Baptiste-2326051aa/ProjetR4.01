package fr.coop.api.data.repository;

import fr.coop.api.data.MariaDBRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Repository {
    protected MariaDBRepository mariaDBRepository;
    protected Connection connection;

    public Repository() {
        this.mariaDBRepository = new MariaDBRepository();
        this.connection = mariaDBRepository.getConnection();
        checkConnection();
    }

    /*public Repository(String infoConnection, String user, String password) {
        this.mariaDBRepository = new MariaDBRepository(infoConnection, user, password);
        this.connection = mariaDBRepository.getConnection();
        checkConnection();
    }*/

    private void checkConnection() {
        if (this.connection == null) {
            throw new IllegalStateException("La connexion à la base de données n'a pas pu être établie.");
        }
    }
}