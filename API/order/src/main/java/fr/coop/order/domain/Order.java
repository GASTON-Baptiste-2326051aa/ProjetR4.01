package fr.coop.order.domain;

/**
 * Classe représentant une commande
 */
public class Order {

    /**
     * Identifiant de la commande
     */
    protected String id;

    /**
     * Identifiant de l'utilisateur
     */
    protected String userId;

    /**
     * Identifiants des paniers
     */
    protected String[] cartId;

    /**
     * Date de la livraison
     */
    protected String date;

    /**
     * Adresse du relai
     */
    protected String relayAddress;

    /**
     * Si la commande a été validée
     */
    public boolean valid;

    /**
     * Constructeur par défaut
     */
    public Order(){
    }

    /**
     * Constructeur de commande
     * @param id identifiant de la commande
     * @param userId identifiant de l'utilisateur
     * @param date date de la livraison
     * @param relayAddress adresse du relai
     * @param valid si la commande a été validée
     */
    public Order(String id, String userId, String date, String relayAddress, boolean valid) {
        this.id = id;
        this.userId = userId;
        this.cartId = new String[0];
        this.date = date;
        this.relayAddress = relayAddress;
        this.valid = valid;
    }

    /**
     * Méthode permettant d'accéder à l'identifiant de la commande
     * @return l'identifiant de la commande
     */
    public String getId() {
        return id;
    }

    /**
     * Méthode permettant de modifier l'identifiant de la commande
     * @param id un entier contenant l'identifiant à utiliser
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Méthode permettant d'accéder à l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Méthode permettant de modifier l'identifiant de l'utilisateur
     * @param userId un entier contenant l'identifiant à utiliser
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Méthode permettant d'accéder aux identifiants des paniers
     * @return les identifiants des paniers
     */
    public String[] getCartId() {
        return cartId;
    }

    /**
     * Méthode permettant de modifier les identifiants des paniers
     * @param cartId un tableau contenant les identifiants à utiliser
     */
    public void setCartId(String[] cartId) {
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
     * Méthode permettant d'accéder à la valeur de l'attribut 'valid'
     * @return la valeur de 'valid'
     */
    public boolean getValid() {
        return valid;
    }

    /**
     * Méthode permettant de valider ou non la commande
     * @param valid le nouvel état de la commande
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Méthode mettant tous les identifiants des paniers dans une chaîne de caractères
     * @return une chaîne de caractères contenant les identifiants des paniers
     */
    public String cartIdToString() {
        String cartIdString = "[";
        for (String id : cartId) {
            cartIdString += "'" + id + "', ";
        }
        cartIdString = cartIdString.substring(cartIdString.length()-2) + "]";
        return cartIdString;
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
                ", validée=" + valid +
                '}';
    }
}