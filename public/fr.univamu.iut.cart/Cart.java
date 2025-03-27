package fr.univamu.iut.cart;

/**
 * Classe représentant un panier
 */
public class Cart{
    /**
     * Identifiant du panier
     */
    protected int id;
    /**
     * Nom du panier
     */
    protected String name;
    /**
     * Description du panier
     */
    protected String description;
    /**
     * Prix du panier
     */
    protected int price;
    /**
     * Quantité disponible
     */
    protected int available_quantity;


    /**
     * Constructeur par défaut
     */
    public Cart(){

    }

    /**
     * Constructeur de Panier
     * @param id identifiant du panier
     * @param name nom du panier
     * @param description description du panier
     * @param price prix du panier
     * @param available_quantity quantité restante
     */
    public Cart(int id, String name, String description, int price, int available_quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available_quantity = available_quantity;
    }

    /**
     * Renvoie l'identifiant de Panier
     * @return un nombre contenant l'identifiant du panier
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de Panier
     * @param id un nombre
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Renvoie le nom de Panier
     * @return une chaîne de caractère contenant le nom du panier
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public int getAvailable_quantity() {
        return available_quantity;
    }

    /**
     *
     * @param available_quantity
     */
    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    /**
     *
     */
    public java.lang.String toString() {
        return "Panier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", available_quantity=" + available_quantity +
                '}';
    }
}