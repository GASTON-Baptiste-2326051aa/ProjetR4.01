package fr.univamu.iut.cart;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


/**
 *
 */
@Path ("/cart")
public class CartResource{

    /**
     *
     */
    private CartService service;

    /**
     * Constructeur par défaut
     */
    public CartResource(){}

    public CartResource(CartRepositoryInterface cartRepo){
        this.cartRepo = new CartService(cartRepo);
    }

    /**
     *
     * @param service
     */
    public CartResource(CartService service){
        this.service = service;
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getCart(@PathParam("id") int id){

        String result = service.getCartJSON(id);

        if result == null throw new NotFoundException();

        return result;

    }


    /**
     *
     * @param id
     * @param cart
     * @return
     */
    public Response updateCart(@PathParams("id") int id, Cart cart){
        if (!service.updateCart(id, cart)) throw new NotFoundException();
        else return Response.ok("updated").build();

    }


}