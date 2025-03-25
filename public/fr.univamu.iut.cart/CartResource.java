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

    }


}