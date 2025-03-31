package fr.univamu.iut.cart;

import java.util.*;

/**
 * Interface définissant les opérations de gestion des paniers.
 */
public interface CartRepositoryInterface {

    /**
     * Ferme la connexion à la base de données ou libère les ressources associées.
     */
    public void close();

    /**
     * Récupère un panier à partir de son identifiant.
     * @param id Identifiant du panier.
     * @return Le panier correspondant à l'identifiant, ou null s'il n'existe pas.
     */
    public Cart getCart(int id);

    /**
     * Récupère la liste de tous les paniers.
     * @return Une liste contenant tous les paniers enregistrés.
     */
    public ArrayList<Cart> getAllCarts();

    /**
     * Met à jour les informations d'un panier existant.
     * @param id Identifiant du panier à mettre à jour.
     * @param name Nouveau nom du panier.
     * @param description Nouvelle description du panier.
     * @param price Nouveau prix du panier.
     * @param available_quantity Nouvelle quantité disponible.
     * @return true si la mise à jour a réussi, false sinon.
     */
    public boolean updateCart(int id, String name, String description, int price, int available_quantity);
}
