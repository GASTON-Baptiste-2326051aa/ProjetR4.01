package fr.coop.user_and_product.api;

/**
 * Classe de base pour les API de dépôt.
 */
public class RepositoryAPI {
    protected String url;

    /**
     * Constructeur de RepositoryAPI.
     *
     * @param url L'URL de l'API.
     */
    public RepositoryAPI(String url) {
        this.url = url;
    }
}