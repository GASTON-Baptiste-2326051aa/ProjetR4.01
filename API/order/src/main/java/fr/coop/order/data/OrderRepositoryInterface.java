package fr.coop.order.data;

import fr.coop.order.domain.Order;

import java.util.*;

/**
 * Interface d'accès aux données des commandes
 */
public interface OrderRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les commandes
     */
    public void close();

    /**
     * Méthode retournant la commande dont l'identifiant est passé en paramètre
     * @param id identifiant de la commande recherchée
     * @return un objet Order représentant la commande recherchée
     */
    public Order getOrder(String id );

    /**
     * Méthode retournant la liste des commandes
     * @return une liste d'objets commandes
     */
    public ArrayList<Order> getAllOrders() ;

    /**
     * Méthode permettant d'enregistrer une commande
     * @param id identifiant de la commande
     * @param userId identifiant de l'utilisateur
     * @param date date de livraison
     * @param relayAddress adresse du relai
     * @param valid état de la commande
     * @return true si la commande a été créée, false sinon
     */
    public boolean addOrder( String id, String userId, String date, String relayAddress, String valid);

    /**
     * Méthode permettant de mettre à jours une commande enregistrée
     * @param id identifiant de la commande à mettre à jours
     * @param userId nouvel identifiant de l'utilisateur
     * @param date nouvelle date de livraison
     * @param relayAddress nouvelle adresse du relai
     * @param valid nouvel état de la commande
     * @return true si la commande existe et la mise à jours a été faite, false sinon
     */
    public boolean updateOrder( String id, String userId, String date, String relayAddress, String valid);

    /**
     * Méthode permettant de supprimer une commande enregistrée
     * @param id identifiant de la commande à supprimer
     * @return true si la commande a été supprimée, false sinon
     */
    public boolean deleteOrder(String id);

    /**
     * Méthode permettant d'ajouter un panier à une commande enregistrée
     * @param id identifiant de la commande à mettre à jour
     * @param cartId identifiant du panier à ajouter
     * @return true si la commande existe et la mise à jour a été faite, false sinon
     */
    public boolean addCart(String id, String cartId);

    /**
     * Méthode permettant de retirer un panier d'une commande enregistrée
     * @param id identifiant de la commande à mettre à jour
     * @param cartId identifiant du panier à retirer
     * @return true si la command existe et la mise à jour a été faite, false sinon
     */
    public boolean removeCart(String id, String cartId);
}