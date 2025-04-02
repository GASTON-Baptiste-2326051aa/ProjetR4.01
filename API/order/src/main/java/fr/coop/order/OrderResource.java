package fr.coop.order;

import fr.coop.order.data.OrderRepositoryInterface;
import fr.coop.order.domain.Order;
import fr.coop.order.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


/**
 * Ressource associée aux demandes
 * (point d'accès de l'API REST)
 */
@Path("/orders")
@ApplicationScoped
public class OrderResource {

    /**
     * Service utilisé pour accéder aux données des commandes et récupérer/modifier leurs informations
     */
    private OrderService service;

    /**
     * Constructeur par défaut
     */
    public OrderResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param orderRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject OrderResource(OrderRepositoryInterface orderRepo ){
        this.service = new OrderService( orderRepo) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux commandes
     */
    public OrderResource(OrderService service ){
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de toutes les commandes enregistrées
     * @return la liste des commandes (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String Orders() {
        return service.getAllOrdersJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'une commande dont l'identifiant est passé paramètre dans le chemin
     * @param id identifiant de la commande recherchée
     * @return les informations de la commande recherchée au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getOrder( @PathParam("id") String id){

        String result = service.getOrderJSON(id);

        // si la commande n'a pas été trouvée
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant d'enregistrer une commande
     * @param order la commande transmise en HTTP au format JSON et convertie en objet Order
     * @return une réponse "created" si l'enregistrement a été effectué, une erreur BadRequest sinon
     */
    @POST
    @Consumes("application/json")
    public Response addOrder(Order order) {


        // si la commande n'a pas été créée
        if (!service.addOrder(order))
            throw new BadRequestException();
        else
            return Response.ok("created").build();
    }

    /**
     * Endpoint permettant de mettre à jours les informations d'une commande
     * @param id l'identifiant de la commande dont il faut changer le statut
     * @param order la commande transmise en HTTP au format JSON et convertie en objet Order
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateOrder(@PathParam("id") String id, Order order ){

        // si la commande n'a pas été trouvée
        if( ! service.updateOrder(id, order) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de supprimer une commande
     * @param id l'identifiant de la commande qu'il faut supprimer
     * @return une réponse "deleted" si la suppression a été effectuée, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") String id) {
        System.out.println(id);

        // si la commande n'a pas été trouvée
        if (!service.deleteOrder(id))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }

    /**
     * Endpoint permettant d'ajouter un panier à une commande
     * @param id l'identifiant de la commande à mettre à jour
     * @param cartId l'identifiant du panier à ajouter
     * @return une réponse "added" si l'ajout a été effectué, une erreur NotFound sinon
     */
    @POST
    @Path("{id}")
    @Consumes("text/plain")
    public Response addCart(@PathParam("id") String id, String cartId ) {

        // si la commande ou le panier n'a pas a été trouvé
        if (!service.addCart(id, cartId))
            throw new NotFoundException();
        else
            return Response.ok("added").build();
    }

    /**
     * Méthode permettant de retirer un panier d'une commande
     * @param id l'identifiant de la commande à mettre à jour
     * @param cartId l'identifiant du panier à retirer
     * @return une réponse "removed" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}/{cartId}")
    public Response removeCart(@PathParam("id") String id, @PathParam("cartId") String cartId) {

        // si la commande ou le panier n'a pas été trouvé
        if (!service.removeCart(id, cartId))
            throw new NotFoundException();
        else
            return Response.ok("removed").build();
    }
}