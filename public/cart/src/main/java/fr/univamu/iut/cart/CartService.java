package fr.univamu.iut.cart;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations des paniers et les formater en JSON
 */
public class CartService {

    /**
     * Référence vers le dépôt des paniers
     */
    protected CartRepositoryInterface cartRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param cartRepo objet implémentant l'interface d'accès aux données
     */
    public CartService(CartRepositoryInterface cartRepo, UserRepositoryInterface userRepo, ProductRepositoryInterface productRepo)
    {
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
    public String getCartJSON(int id) {
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
     * Méthode permettant d'enregistrer un panier (la date est définie automatiquement)
     * @param idUser identifiant de l'utilisateur
     * @param idProduct identifiant du produit
     * @return
     */
    public boolean registerCart(String idUser, String idProduct) {
        boolean result = false;

        Product myProduct = productRepo.getProduct( idProduct );

        if( myProduct == null )
            throw  new NotFoundException("product not exists");

        if( myBook.status == 'd')
        {
            User myUser = userRepo.getUser(id);

            if( myUser == null)
                throw  new NotFoundException("user not exists");

            result = bookRepo.updateBook(myBook.reference, myBook.title, myBook.authors, 'r');

            if( result )
                result = cartRepo.registerReservation(idUser, idProduct);
        }
        return result;
    }

    /**
     * Enpoint permettant de soumettre une nouvelle réservation de livre demandée par un utilisateur enregistré
     * @param idUser identifiant de l'utilisateur souhaitant faire la réservation
     * @param idProduct identifiant du produit
     * @return un objet Response indiquant "registred" si la réservation a été faite ou une erreur "conflict" sinon
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response registerReservation(@FormParam("id") String idUser, @FormParam("id") String idProduct){

        if( service.registerCart(idUser, idProduct) )
            return Response.ok("registred").build();
        else
            return Response.status( Response.Status.CONFLICT ).build();
    }

    /**
     * Méthode supprimant une réservation
     * @param reference référence du livre à libérer
     * @return true si la réservation a été supprimée, false sinon
     */
    boolean removeCart(String reference){
        boolean result = false;

        // récupération des informations du livre
        Book myBook = bookRepo.getBook( reference );

        //si le livre n'est pas trouvé
        if( myBook == null )
            throw  new NotFoundException("book not exists");
        else
        {
            // modifier le statut du livre à "d" (disponible) dans le dépôt des livres
            result = bookRepo.updateBook(myBook.reference, myBook.title, myBook.authors, 'd');

            // supprimer la réservation
            if(result ) result = reservationRepo.releaseReservation( reference );
        }
        return result;
    }

    /**
     * Endpoint permettant de supprimer une réservation
     * @param reference référence du livre à "libérer"
     * @return un objet Response indiquant "removed" si la réservation a été annulée ou une erreur "not found" sinon
     */
    @DELETE
    @Path("{reference}")
    public Response removeReservation(@PathParam("reference") String reference){

        if( service.removeReservation(reference) )
            return Response.ok("removed").build();
        else
            return Response.status( Response.Status.NOT_FOUND ).build();
    }
}
