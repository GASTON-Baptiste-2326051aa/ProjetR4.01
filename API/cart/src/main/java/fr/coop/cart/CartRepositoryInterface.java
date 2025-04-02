package fr.coop.cart;

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
    public Cart getCart(String id);

    /**
     * Récupère la liste de tous les paniers.
     * @return Une liste contenant tous les paniers enregistrés.
     */
    public ArrayList<Cart> getAllCarts();

    /**
     * Ajoute un panier.
     * @param id Identifiant du panier.
     * @param name Nom du panier.
     * @param description Description du panier.
     * @param price Prix du panier.
     * @param available_quantity Quantité disponible.
     * @return true si l'ajout a réussi, false sinon.
     */
    public boolean addCart(String id, String name, String description, double price, int available_quantity);

    /**
     * Met à jour les informations d'un panier existant.
     * @param id Identifiant du panier à mettre à jour.
     * @param name Nouveau nom du panier.
     * @param description Nouvelle description du panier.
     * @param price Nouveau prix du panier.
     * @param available_quantity Nouvelle quantité disponible.
     * @return true si la mise à jour a réussi, false sinon.
     */
    public boolean updateCart(String id, String name, String description, double price, int available_quantity);

    /**
     * Supprime un panier de la base de données.
     * @param id Identifiant du panier.
     * @return true si la suppression a réussi, false sinon.
     */
    public boolean deleteCart(String id);


    /**
     * Ajoute un produit à un panier.
     * @param idCart Identifiant du panier.
     * @param idProduct Identifiant du produit.
     * @return true si la mise à jour a réussi, false sinon.
     */
    public boolean addProduct(String idCart, String idProduct);


    /**
     * Retire un produit d'un panier.
     * @param idCart Identifiant du panier.
     * @param idProduct Identifiant du produit.
     * @return true si la mise à jour a réussi, false sinon.
     */
    public boolean removeProduct(String idCart, String idProduct);


}
