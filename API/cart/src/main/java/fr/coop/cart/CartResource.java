package fr.coop.cart;

import fr.coop.cart.other_api.ProductRepositoryInterface;
import fr.coop.cart.other_api.UserRepositoryInterface;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux paniers
 * (point d'accès de l'API REST)
 */
@Path("/carts")
@ApplicationScoped
public class CartResource {

    /**
     * Service utilisé pour accéder aux données des paniers et récupérer/modifier leurs informations
     */
    private CartService service;

    /**
     * Constructeur par défaut
     */
    public CartResource() {}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param cartRepo objet implémentant l'interface d'accès aux données
     */

    public @Inject CartResource(CartRepositoryInterface cartRepo, UserRepositoryInterface userRepo, ProductRepositoryInterface productRepo) {
        this.service = new CartService(cartRepo, userRepo, productRepo);
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux paniers
     */
    public CartResource(CartService service) {
        this.service = service;
    }

    /**
     * Endpoint permettant de récupérer tous les paniers enregistrés
     * @return la liste des paniers (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllCarts() {
        return service.getAllCartsJSON();
    }

    /**
     * Endpoint permettant de récupérer les informations d'un panier dont l'identifiant est passé en paramètre dans le chemin
     * @param id identifiant du panier recherché
     * @return les informations du panier recherché au format JSON
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getCart(@PathParam("id") String id) {
        String result = service.getCartJSON(id);

        // Si le panier n'a pas été trouvé
        if (result == null)
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour les informations d'un panier
     * @param id identifiant du panier à mettre à jour
     * @param cart le panier transmis en HTTP au format JSON et converti en objet Cart
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateCart(@PathParam("id") String id, Cart cart) {
        // Si le panier n'a pas été trouvé
        if (!service.updateCart(Integer.parseInt(id), cart))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de supprimer un panier
     * @param id identifiant du produit à "libérer"
     * @return un objet Response indiquant "removed" si le panier a été annulé ou une erreur "not found" sinon
     */
    @DELETE
    @Path("{id}")
    public Response removeCart(@PathParam("id") String id){

        if( service.removeCart(id) )
            return Response.ok("removed").build();
        else
            return Response.status( Response.Status.NOT_FOUND ).build();
    }
}
