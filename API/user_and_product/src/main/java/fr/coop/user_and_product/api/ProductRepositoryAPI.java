package fr.coop.user_and_product.api;

import fr.coop.user_and_product.data.interfaces.ProductRepositoryInterface;
import fr.coop.user_and_product.domain.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * API pour le dépôt de produits.
 */
public class ProductRepositoryAPI extends RepositoryAPI implements ProductRepositoryInterface {

    /**
     * Constructeur de l'API ProductRepository.
     *
     * @param url L'URL de l'API.
     */
    public ProductRepositoryAPI(String url) {
        super(url);
    }

    /**
     * Crée un nouveau produit avec les détails donnés.
     *
     * @param name           Le nom du produit.
     * @param description    La description du produit.
     * @param availableStock Le stock disponible du produit.
     * @param unitType       Le type d'unité du produit.
     * @return L'ID du produit créé.
     */
    @Override
    public String createProduct(String name, String description, double availableStock, String unitType) {
        return null;
    }

    /**
     * Met à jour les détails d'un produit existant.
     *
     * @param productId         L'ID du produit à mettre à jour.
     * @param newName           Le nouveau nom du produit.
     * @param newDescription    La nouvelle description du produit.
     * @param newAvailableStock Le nouveau stock disponible du produit.
     * @param newUnitType       Le nouveau type d'unité du produit.
     * @return true si la mise à jour a réussi, false sinon.
     */
    @Override
    public boolean updateProduct(String productId, String newName, String newDescription, double newAvailableStock, String newUnitType) {
        return false;
    }

    /**
     * Supprime un produit avec l'ID donné.
     *
     * @param productId L'ID du produit à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    @Override
    public boolean deleteProduct(String productId) {
        return false;
    }

    /**
     * Récupère un produit avec l'ID donné.
     *
     * @param productId L'ID du produit à récupérer.
     * @return Le produit avec l'ID donné, ou null s'il n'est pas trouvé.
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
     * Récupère tous les produits.
     *
     * @return Une liste de tous les produits.
     */
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}