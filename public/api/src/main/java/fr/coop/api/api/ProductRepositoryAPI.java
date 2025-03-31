package fr.coop.api.api;

import fr.coop.api.data.interfaces.ProductRepositoryInterface;
import fr.coop.api.domain.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ProductRepositoryAPI extends RepositoryAPI implements ProductRepositoryInterface {

    public ProductRepositoryAPI(String url) {
        super(url);
    }

    /**
     * @param name           Le nom du produit. 
     * @param description    La description du produit.
     * @param availableStock Le stock disponible du produit.
     * @param unitType       Le type d'unité du produit.
     * @return
     */
    @Override
    public String createProduct(String name, String description, double availableStock, String unitType) {
        return null;
    }

    /**
     * @param productId         L'ID du produit à mettre à jour. 
     * @param newName           Le nouveau nom du produit.
     * @param newDescription    La nouvelle description du produit.
     * @param newAvailableStock Le nouveau stock disponible du produit.
     * @param newUnitType       Le nouveau type d'unité du produit.
     * @return
     */
    @Override
    public boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType) {
        return false;
    }

    /**
     * @param productId L'ID du produit à supprimer. 
     * @return
     */
    @Override
    public boolean deleteProduct(String productId) {
        return false;
    }

    /**
     * @param productId L'ID du produit à récupérer. 
     * @return
     */
    @Override
    public Product getProduct(String productId) {
        Product product = null;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget endPoint = target.path("products/" + productId);
        Response response = endPoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200)
            product = response.readEntity(Product.class);

        return product;
    }

    /**
     * @return 
     */
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
