package fr.coop.api.data.repository;

import fr.coop.api.data.MariaDBRepository;

import java.sql.Connection;

/**
 * Classe de base pour les dépôts
 */
public class Repository {
    protected MariaDBRepository mariaDBRepository;
    protected Connection connection;

    /**
     * Constructeur par défaut qui initialise la connexion à la base de données.
     */
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

    /**
     * Vérifie si la connexion à la base de données a été établie avec succès.
     *
     * @throws IllegalStateException si la connexion n'a pas pu être établie.
     */
    private void checkConnection() {
        if (this.connection == null) {
            throw new IllegalStateException("La connexion à la base de données n'a pas pu être établie.");
        }
    }
}