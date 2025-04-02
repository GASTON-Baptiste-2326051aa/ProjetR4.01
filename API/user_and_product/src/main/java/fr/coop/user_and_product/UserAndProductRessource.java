package fr.coop.user_and_product;

import fr.coop.user_and_product.domain.Product;
import fr.coop.user_and_product.domain.User;
import fr.coop.user_and_product.service.UserAndProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource JAX-RS pour la gestion des utilisateurs et des produits.
 */
@Path("/user_and_product")
@ApplicationScoped
public class UserAndProductRessource {
    private UserAndProductService userAndProductService;

    /**
     * Constructeur par défaut.
     */
    public UserAndProductRessource(){}

    /**
     * Constructeur avec injection de dépendance.
     *
     * @param userAndProductService Le service de gestion des utilisateurs et des produits.
     */
    @Inject
    public UserAndProductRessource(UserAndProductService userAndProductService) {
        this.userAndProductService = userAndProductService;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return La réponse HTTP contenant la liste des utilisateurs en format JSON.
     */
    @GET
    @Path("/user")
    @Produces("application/json")
    public Response getAllUsers() {
        String users = userAndProductService.getAllUsersJSON();
        return Response.ok(users).build();
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur.
     * @return La réponse HTTP contenant l'utilisateur en format JSON.
     */
    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    public Response getUser(@PathParam("id") String id) {
        String user = userAndProductService.getUserJSON(id);
        return Response.ok(user).build();
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userJson Le JSON représentant l'utilisateur à créer.
     * @return La réponse HTTP indiquant le statut de la création.
     */
    @PUT
    @Path("/user/create")
    @Consumes("application/json")
    public Response createUser(String userJson) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            User user = jsonb.fromJson(userJson, User.class);
            userAndProductService.createUser(user.firstName(), user.name(), user.password());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON").build();
        }
    }

    /**
     * Met à jour un utilisateur existant.
     *
     * @param userJson Le JSON représentant l'utilisateur à mettre à jour.
     * @return La réponse HTTP indiquant le statut de la mise à jour.
     */
    @PUT
    @Path("/user/update")
    @Consumes("application/json")
    public Response updateUser(String userJson) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            User user = jsonb.fromJson(userJson, User.class);
            userAndProductService.updateUser(user.id(), user.firstName(), user.name(), user.password());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON").build();
        }
    }

    /**
     * Supprime un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à supprimer.
     * @return La réponse HTTP indiquant le statut de la suppression.
     */
    @DELETE
    @Path("/user/delete/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        try {
            userAndProductService.deleteUser(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }

    /**
     * Vérifie si un utilisateur existe avec l'ID et le mot de passe donnés.
     *
     * @param json Le JSON contenant l'ID et le mot de passe de l'utilisateur.
     * @return La réponse HTTP indiquant si l'utilisateur existe ou non.
     */
    @GET
    @Path("/user/exists")
    @Consumes("application/json")
    @Produces("application/json")
    public Response isUserExist(String json) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            UserCredentials credentials = jsonb.fromJson(json, UserCredentials.class);
            boolean exists = userAndProductService.isUserExist(credentials.getId(), credentials.getPassword());
            if (exists) {
                return Response.ok("{\"exists\": true}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"exists\": false}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON").build();
        }
    }

    public static class UserCredentials {
        private final String id;
        private final String password;

        public UserCredentials(String id, String password) {
            this.id = id;
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public String getPassword() {
            return password;
        }
    }

    /**
     * Récupère tous les produits.
     *
     * @return La réponse HTTP contenant la liste des produits en format JSON.
     */
    @GET
    @Path("/product")
    @Produces("application/json")
    public Response getAllProducts() {
        String products = userAndProductService.getAllProductsJSON();
        return Response.ok(products).build();
    }

    /**
     * Récupère un produit par son ID.
     *
     * @param id L'ID du produit.
     * @return La réponse HTTP contenant le produit en format JSON.
     */
    @GET
    @Path("/product/{id}")
    @Produces("application/json")
    public Response getProduct(@PathParam("id") String id) {
        String product = userAndProductService.getProductJSON(id);
        return Response.ok(product).build();
    }

    /**
     * Crée un nouveau produit.
     *
     * @param productJson Le JSON représentant le produit à créer.
     * @return La réponse HTTP indiquant le statut de la création.
     */
    @PUT
    @Path("/product/create")
    @Consumes("application/json")
    public Response createProduct(String productJson) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            Product product = jsonb.fromJson(productJson, Product.class);
            userAndProductService.createProduct(product.name(), product.description(), product.availableStock(), product.unitType());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON").build();
        }
    }

    /**
     * Met à jour un produit existant.
     *
     * @param productJson Le JSON représentant le produit à mettre à jour.
     * @return La réponse HTTP indiquant le statut de la mise à jour.
     */
    @PUT
    @Path("/product/update")
    @Consumes("application/json")
    public Response updateProduct(String productJson) {
        try {
            Jsonb jsonb = JsonbBuilder.create();
            Product product = jsonb.fromJson(productJson, Product.class);
            userAndProductService.updateProduct(product.id(), product.name(), product.description(), product.availableStock(), product.unitType());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid JSON").build();
        }
    }

    /**
     * Supprime un produit par son ID.
     *
     * @param id L'ID du produit à supprimer.
     * @return La réponse HTTP indiquant le statut de la suppression.
     */
    @DELETE
    @Path("/product/delete/{id}")
    public Response deleteProduct(@PathParam("id") String id) {
        try {
            userAndProductService.deleteProduct(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found").build();
        }
    }
}