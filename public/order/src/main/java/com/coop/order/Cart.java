package com.coop.order;

/**
 * Classe représentant un panier.
 */
public class Cart {
    /**
     * Identifiant du panier.
     */
    protected String id;
    /**
     * Nom du panier.
     */
    protected String name;
    /**
     * Description du panier.
     */
    protected String description;
    /**
     * Prix du panier.
     */
    protected int price;
    /**
     * Quantité disponible.
     */
    protected int available_quantity;

    /**
     * Constructeur par défaut.
     */
    public Cart() {
    }

    /**
     * Constructeur de Panier.
     *
     * @param id                Identifiant du panier.
     * @param name              Nom du panier.
     * @param description       Description du panier.
     * @param price             Prix du panier.
     * @param available_quantity Quantité disponible du panier.
     */
    public Cart(String id, String name, String description, int price, int available_quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available_quantity = available_quantity;
    }

    /**
     * Renvoie l'identifiant du panier.
     *
     * @return L'identifiant du panier.
     */
    public String getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du panier.
     *
     * @param id Nouvel identifiant du panier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Renvoie le nom du panier.
     *
     * @return Le nom du panier.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du panier.
     *
     * @param name Nouveau nom du panier.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Renvoie la description du panier.
     *
     * @return La description du panier.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifie la description du panier.
     *
     * @param description Nouvelle description du panier.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Renvoie le prix du panier.
     *
     * @return Le prix du panier.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Modifie le prix du panier.
     *
     * @param price Nouveau prix du panier.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Renvoie la quantité disponible du panier.
     *
     * @return La quantité disponible du panier.
     */
    public int getAvailable_quantity() {
        return available_quantity;
    }

    /**
     * Modifie la quantité disponible du panier.
     *
     * @param available_quantity Nouvelle quantité disponible du panier.
     */
    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    /**
     * Renvoie une représentation textuelle de l'objet.
     *
     * @return Une chaîne contenant les informations du panier.
     */
    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", available_quantity=" + available_quantity +
                '}';
    }
}
