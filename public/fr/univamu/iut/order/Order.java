package fr.univamu.iut.order;

/**
 * Classe représentant une commande
 */
public class Order {

    /**
     * Identifiant de la commande
     */
    protected int id;

    /**
     * Identifiant de l'utilisateur
     */
    protected int userId;

    /**
     * Identifiants des paniers
     */
    protected int[] cartId;

    /**
     * Date de la livraison
     */
    protected String date;

    /**
     * Adresse du relai
     */
    protected String relayAddress

    /**
     * Constructeur par défaut
     */
    public Order(){
    }

    /**
     * Constructeur de réservation
     * @param id identifiant de la commande
     * @param userId identifiant de l'utilisateur
     * @param cartId identifiants des paniers
     * @param date date de la livraison
     * @param relayAddress adresse du relai
     */
    public Order(int id, id userId, int[] cartId, String date, String relayAddress) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.date = date;
        this.relayAddress = relayAddress;
    }

    /**
     * Méthode permettant d'accéder à l'identifiant de la commande
     * @return l'identifiant de la commande
     */
    public int getId() {
        return id;
    }

    /**
     * Méthode permettant de modifier l'identifiant de la commande
     * @param id un entier contenant l'identifiant à utiliser
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Méthode permettant d'accéder à l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Méthode permettant de modifier l'identifiant de l'utilisateur
     * @param userId un entier contenant l'identifiant à utiliser
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Méthode permettant d'accéder aux identifiants des paniers
     * @return les identifiants des paniers
     */
    public int[] getCartId() {
        return cartId;
    }

    /**
     * Méthode permettant de modifier les identifiants des paniers
     * @param cartId un tableau contenant les identifiants à utiliser
     */
    public void setCartId(int[] cartId) {
        this.cartId = cartId;
    }

    /**
     * Méthode permettant d'accéder à la date de livraison
     * @return la date de livraison
     */
    public String getDate() {
        return date;
    }

    /**
     * Méthode permettant de modifier la date de livraison
     * @param date une chaîne de caractères contenant la date à utiliser
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Méthode permettant d'accéder à l'adresse du relai
     * @return l'adresse de livraison
     */
    public String getRelayAddress() {
        return relayAddress;
    }

    /**
     * Méthode permettant de modifier l'adresse du relai
     * @param relayAddress une chaîne de caractères contenant l'adresse à utiliser
     */
    public void setRelayAddress(String relayAddress) {
        this.relayAddress = relayAddress;
    }

    /**
     * Méthode mettant tous les identifiants des paniers dans une chaîne de caractères
     * @return une chaîne de caractères contenant les identifiants des paniers
     */
    public String cartIdToString() {
        String cartIdString = "[";
        for (int i = 0; i < cartId.length; ++i) {
            if (cartId[i] != null) {
                cartIdString += "'" + cartId[i] + "', ";
            }
        }
        cartIdString.substring(cartIdString.length()-2, cartIdString.length());
        cartIdString += "]";
    }

    /**
     * Méthode permettant d'afficher les données de l'instance
     * @return une chaîne de caractères contenant les données de l'instance
     */
    @Override
    public String toString() {
        return "Commande{" +
                "identifiant='" + id + '\'' +
                ", identifiant de l'utilisateur='" + userId + '\'' +
                ", identifiants des paniers=" + cartIdToString() +
                ", date de livraison='" + date + '\'' +
                ", adresse du relai='" + relayAddress + '\'' +
                '}';
    }
}