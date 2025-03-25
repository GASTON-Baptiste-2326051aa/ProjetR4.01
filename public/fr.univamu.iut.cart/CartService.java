package fr.univamu.iut.cart;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonBuilder;
import java.util.ArrayList;

/**
 *
 */
public class CartService {

    /**
     *
     */
    protected CartRepositoryInterface cartRepo;

    /**
     *
     * @param cartRepo
     */
    public CartService(CartRepositoryInterface cartRepo) {
        this.cartRepo = cartRepo;
    }

    /**
     *
     * @return
     */
    public String getAllCartsJSON(){
        ArrayList<Cart> allCarts = cartRepo.getAllCarts();
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allCarts);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     *
     * @param id
     * @return
     */
    public String getCartJSON(int id){
        String result = null;
        Cart myCart = cartRepo.getCart(id);

        if(myCart != null){
            try (Jsonb jsonb = JsonbBuilder.create()){
                result = jsonb.toJson(myCart);
            }
            catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

        return result;

    }

    /**
     *
     * @param id
     * @param cart
     * @return
     */
    public boolean updateCart(int id, Cart cart){
        return cartRepo.updateCart(id, cart.name, cart.description, cart.price
        cart.available_quantity)
    }









}