package fr.coop.cart;

import fr.coop.cart.other_api.Product;
import fr.coop.cart.other_api.ProductRepositoryInterface;
import fr.coop.cart.other_api.User;
import fr.coop.cart.other_api.UserRepositoryInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations des paniers et les formater en JSON
 */
@RequestScoped
@Path("/carts")
@Produces("application/json")
public class CartService {

    protected final UserRepositoryInterface userRepo;
    protected final ProductRepositoryInterface productRepo;
    protected final CartRepositoryInterface cartRepo;

    @Inject
    public CartService(CartRepositoryInterface cartRepo, UserRepositoryInterface userRepo, ProductRepositoryInterface productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @GET
    public String getAllCartsJSON() {
        ArrayList<Cart> allCarts = cartRepo.getAllCarts();
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(allCarts);
        } catch (Exception e) {
            throw new WebApplicationException("Error generating JSON", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public String getCartJSON(@PathParam("id") String id) {
        Cart myCart = cartRepo.getCart(id);
        if (myCart == null) {
            throw new NotFoundException("Cart not found");
        }
        try (Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(myCart);
        } catch (Exception e) {
            throw new WebApplicationException("Error generating JSON", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public boolean updateCart(@PathParam("id") String id, Cart cart) {
        return cartRepo.updateCart(id, cart.name, cart.description, cart.price, cart.available_quantity);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addProduct(@FormParam("idCart") String idCart, @FormParam("idProduct") String idProduct) {
        if (cartRepo.addProduct(idCart, idProduct)) {
            return Response.ok("added").build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{idCart}/{idProduct}")
    public Response removeProduct(@PathParam("idCart") String idCart, @PathParam("idProduct") String idProduct) {
        if (cartRepo.removeProduct(idCart, idProduct)) {
            return Response.ok("removed").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeCart(@PathParam("id") String id) {
        if (cartRepo.deleteCart(id)) {
            return Response.ok("deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
