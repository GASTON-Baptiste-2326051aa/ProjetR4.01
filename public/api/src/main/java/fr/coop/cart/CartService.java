package fr.coop.cart;

import fr.coop.api.data.interfaces.ProductRepositoryInterface;
import fr.coop.api.data.interfaces.UserRepositoryInterface;
import fr.coop.api.domain.Product;
import fr.coop.api.domain.User;
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

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param cartRepo objet implémentant l'interface d'accès aux données
     */
    @Inject
    public CartService(CartRepositoryInterface cartRepo, UserRepositoryInterface userRepo, ProductRepositoryInterface productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    /**
     * Récupère tous les paniers et les retourne en format JSON
     * @return une chaîne de caractères contenant la liste des paniers en JSON
     */
    public String getAllCartsJSON() {
        ArrayList<Cart> allCarts = cartRepo.getAllCarts();
        String result = null;

        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCarts);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Récupère un panier spécifique en fonction de son ID et le retourne en JSON
     * @param id identifiant du panier recherché
     * @return une chaîne JSON représentant le panier
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
     * Met à jour un panier dans la base de données
     * @param id identifiant du panier à mettre à jour
     * @param cart nouvel objet contenant les informations mises à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateCart(int id, Cart cart) {
        return cartRepo.updateCart(id, cart.name, cart.description, cart.price, cart.available_quantity);
    }

    /**
     * Méthode permettant d'ajouter un produit à un panier pour un utilisateur donné
     * @param idUser identifiant de l'utilisateur
     * @param idProduct identifiant du produit
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean addProductToCart(String idUser, String idProduct) {
        boolean result;

        Product myProduct = productRepo.getProduct(idProduct);

        if (myProduct == null)
            throw new NotFoundException("Product not found");

        User myUser = userRepo.getUser(idUser);

        if (myUser == null)
            throw new NotFoundException("User not found");

        // Ajouter le produit au panier
        result = cartRepo.addProduct(idUser, idProduct);

        return result;
    }

    /**
     * Endpoint permettant d'ajouter un produit au panier
     * @param idUser identifiant de l'utilisateur
     * @param idProduct identifiant du produit
     * @return une réponse HTTP indiquant "added" si l'ajout a été effectué, ou "conflict" en cas d'erreur
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addProduct(@FormParam("idUser") String idUser, @FormParam("idProduct") String idProduct) {

        if (addProductToCart(idUser, idProduct))
            return Response.ok("added").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    /**
     * Méthode supprimant un produit d'un panier
     * @param idCart identifiant du panier
     * @param idProduct identifiant du produit à retirer
     * @return true si le produit a été retiré, false sinon
     */
    public boolean removeProductFromCart(String idCart, String idProduct) {
        boolean result;

        Product myProduct = productRepo.getProduct(idProduct);

        if (myProduct == null)
            throw new NotFoundException("Product not found");

        result = cartRepo.removeProduct(idCart, idProduct);

        return result;
    }

    /**
     * Endpoint permettant de retirer un produit d'un panier
     * @param idCart identifiant du panier
     * @param idProduct identifiant du produit à retirer
     * @return une réponse HTTP indiquant "removed" si le produit a été retiré, ou "not found" sinon
     */
    @DELETE
    @Path("{idCart}/{idProduct}")
    public Response removeProduct(@PathParam("idCart") String idCart, @PathParam("idProduct") String idProduct) {

        if (removeProductFromCart(idCart, idProduct))
            return Response.ok("removed").build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    public boolean removeCart(String id) {
        return false;
    }
}
