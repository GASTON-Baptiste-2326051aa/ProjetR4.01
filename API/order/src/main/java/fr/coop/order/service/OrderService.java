package fr.coop.order.service;

import fr.coop.order.data.OrderRepositoryInterface;
import fr.coop.order.domain.Order;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;


/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'éccès aux données)
 */
public class OrderService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les commandes
     */
    protected OrderRepositoryInterface orderRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param orderRepo objet implémentant l'interface d'accès aux données
     */
    public OrderService(OrderRepositoryInterface orderRepo) {
        this.orderRepo = orderRepo;
    }

    /**
     * Méthode retournant les informations sur les commandes au format JSON
     * @return une chaîne de caractères contenant les informations au format JSON
     */
    public String getAllOrdersJSON(){

        ArrayList<Order> allOrders = orderRepo.getAllOrders();

        // création du json et conversion de la liste de commandes
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allOrders);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur une commande recherchée
     * @param id l'identifiant de la commande recherchée
     * @return une chaîne de caractères contenant les informations au format JSON
     */
    public String getOrderJSON( String id ){
        String result = null;
        Order myOrder = orderRepo.getOrder(id);

        // si la commande a été trouvée
        if( myOrder != null ) {

            // création du json et conversion de la commande
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myOrder);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode permettant d'enregistrer une commande
     * @param order les informations de la commande
     * @return trye si la commande a pu être créée, false sinon
     */
    public boolean addOrder(Order order) {
        return orderRepo.addOrder(order.getId(), order.getUserId(), order.getDate(), order.getRelayAddress(), String.valueOf(order.getValid()));
    }

    /**
     * Méthode permettant de mettre à jour les informations d'une commande
     * @param id identifiant de la commande à mettre à jour
     * @param order les nouvelles informations a utiliser
     * @return true si la commande a pu être mise à jour, false sinon
     */
    public boolean updateOrder(String id, Order order) {
        return orderRepo.updateOrder(id, order.getUserId(), order.getDate(), order.getRelayAddress(), String.valueOf(order.getValid()));
    }

    /**
     * Méthode permettant de supprimer une commande
     * @param id identifiant de la commande à supprimer
     * @return true si la commande a été supprimée, false sinon
     */
    public boolean deleteOrder(String id) {
        return orderRepo.deleteOrder(id);
    }

    /**
     * Méthode permettant d'ajouter un panier à une commande
     * @param id identifiant de la commande
     * @param cartId identifiant du panier
     * @return true si la commande a pu être mise à jour, false sinon
     */
    public boolean addCart(String id, String cartId) {
        return orderRepo.addCart(id, cartId);
    }

    /**
     * Méthode permettant de retirer un panier d'une commande
     * @param id identifiant de la commande
     * @param cartId identifiant du panier
     * @return true si la commande a pu être mise à jour, false sinon
     */
    public boolean removeCart(String id, String cartId) {
        return orderRepo.removeCart(id, cartId);
    }
}