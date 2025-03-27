package fr.univamu.iut.book;

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
    public CartResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param bookRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject CartResource( CartRepositoryInterface cartRepo ){
        this.service = new BookService(cartRepo) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     */
    public CartResource( CartService service ){
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des livres (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllCarts() {
        return service.getAllCartsJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un livre dont la référence est passée paramètre dans le chemin
     * @param reference référence du livre recherché
     * @return les informations du livre recherché au format JSON
     */
    @GET
    @Path("{reference}")
    @Produces("application/json")
    public String getCart( @PathParam("reference") int id){

        String result = service.getCartJSON(id);

        // si le panier n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jours le statut d'un panier uniquement
     * (la requête patch doit fournir le nouveau statut sur Cart, les autres informations sont ignorées)
     * @param id l'identifiant du panier dont il faut changer le statut
     * @param cart le panier transmis en HTTP au format JSON et convertit en objet Cart
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{reference}")
    @Consumes("application/json")
    public Response updateCart(@PathParam("reference") int id , Cart cart ){

        // si le panier n'a pas été trouvé
        if( ! service.updateBook(id, cart) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }
}