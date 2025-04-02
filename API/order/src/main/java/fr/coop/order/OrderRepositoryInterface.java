package fr.coop.order;

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
    public Order getOrder( String id );

    /**
     * Méthode retournant la liste des commandes
     * @return une liste d'objets commandes
     */
    public ArrayList<Order> getAllOrders() ;

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
}