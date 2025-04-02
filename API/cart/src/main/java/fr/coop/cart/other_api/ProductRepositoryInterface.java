package fr.coop.cart.other_api;

import fr.coop.cart.other_api.Product;

import java.util.List;

/**
 * Interface pour le dépôt de produits
 */
public interface ProductRepositoryInterface {

    /**
     * Crée un nouveau produit avec les détails donnés.
     *
     * @param name           Le nom du produit.
     * @param description    La description du produit.
     * @param availableStock Le stock disponible du produit.
     * @param unitType       Le type d'unité du produit.
     * @return L'ID du produit créé.
     */
    String createProduct(String name, String description, double availableStock, String unitType);

    /**
     * Met à jour les détails d'un produit existant.
     *
     * @param productId         L'ID du produit à mettre à jour.
     * @param newName           Le nouveau nom du produit.
     * @param newDescription    La nouvelle description du produit.
     * @param newAvailableStock Le nouveau stock disponible du produit.
     * @param newUnitType       Le nouveau type d'unité du produit.
     * @return true si la mise à jour a réussi, false sinon.
     */
    boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType);

    /**
     * Supprime un produit avec l'ID donné.
     *
     * @param productId L'ID du produit à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    boolean deleteProduct(String productId);

    /**
     * Récupère un produit avec l'ID donné.
     * @param productId L'ID du produit à récupérer.
     * @return Le produit avec l'ID donné, ou null s'il n'est pas trouvé.
     */
    Product getProduct(String productId);

    /**
     * Récupère tous les produits.
     * @return Une liste de tous les produits.
     */
    List<Product> getAllProducts();
}