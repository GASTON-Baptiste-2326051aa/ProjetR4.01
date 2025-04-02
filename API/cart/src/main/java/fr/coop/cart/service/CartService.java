package fr.coop.cart.service;

import fr.coop.cart.data.CartRepositoryInterface;
import fr.coop.cart.domain.Cart;

import java.util.ArrayList;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class CartService {

    protected final CartRepositoryInterface cartRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param cartRepo objet implémentant l'interface d'accès aux données
     *
     */
    public CartService(CartRepositoryInterface cartRepo) {
        this.cartRepo = cartRepo;

    }

    /**
     * Méthode retournant les informations sur tous les paniers au format JSON
     * @return une chaîne de caractères contenant les informations sur tous les paniers en format JSON
     */
    public String getAllCartsJSON() {
        ArrayList<Cart> allCarts = cartRepo.getAllCarts();
        String result = null;

        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCarts);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un panier recherché
     * @param id identifiant du panier recherché
     * @return une chaîne de caractères contenant les informations sur le panier en format JSON
     */
    public String getCartJSON(String id) {
        String result = null;
        Cart myCart = cartRepo.getCart(id);

        if (myCart != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCart);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    /**
     * Méthode permettant de créer panier
     * @param cart pannier à enregistrer
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean addCart(Cart cart) {
        return cartRepo.addCart(cart.getId(), cart.getName(), cart.getDescription(), cart.getPrice(), cart.getAvailable_quantity());
         }

    /**
     * Méthode permettant de mettre à jour un panier
     * @param id identifiant du panier à mettre à jour
     * @param cart nouvel objet contenant les informations mises à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateCart(String id, Cart cart) {
        return cartRepo.updateCart(id, cart.getName(), cart.getDescription(), cart.getPrice(), cart.getAvailable_quantity());
    }

    /**
     * Méthode permettant de supprimer un panier
     * @param id identifiant du panier à supprimer
     * @return true si le panier a été supprimé, false sinon
     */
    public boolean deleteCart(String id) {
        return cartRepo.deleteCart(id);
    }


    /**
     * Méthode permettant d'ajouter un produit au panier
     * @param idCart identifiant du panier
     * @param idProduct identifiant du produit
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean addProduct(String idCart, String idProduct) {
        return cartRepo.addProduct(idCart, idProduct);
    }

    /**
     * Méthode permettant de retirer un produit du panier
     * @param idCart identifiant du panier
     * @param idProduct identifiant du produit à retirer
     * @return true si le produit a été retiré, false sinon
     */
    public boolean removeProduct(String idCart, String idProduct) {
        return cartRepo.removeProduct(idCart, idProduct);
    }


}
