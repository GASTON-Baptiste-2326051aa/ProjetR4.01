package fr.univamu.iut.cart;

import java.util.*;

/**
 *
 */
public interface CartRepositoryInterface {

    /**
     *
     */
    public void close();

    /**
     *
     * @param id
     * @return
     */
    public Cart getCart(int id);

    /**
     *
     * @return
     */
    public ArrayList<Cart> getAllCarts();

    /**
     *
     * @param id
     * @param name
     * @param description
     * @param price
     * @param available_quantity
     * @return
     */
    public boolean updateCart(int id, String name, String description, int price, int available_quantity);




}