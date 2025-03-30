package fr.coop.api;

import fr.coop.api.domain.User;
import fr.coop.api.service.UserAndProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/user_and_product")
@ApplicationScoped
public class UserAndProductRessource {
    private UserAndProductService userAndProductService; // = new UserAndProductService();

    public UserAndProductRessource(){}

    @Inject
    public UserAndProductRessource(UserAndProductService userAndProductService) {
        this.userAndProductService = userAndProductService;
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public Response getAllUsers() {
        String users = userAndProductService.getAllUsersJSON();
        return Response.ok(users).build();
    }

    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    public Response getUser(@PathParam("id") int id) {
        String user = userAndProductService.getUserJSON(id);
        return Response.ok(user).build();
    }

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

    @DELETE
    @Path("/user/delete/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {
            userAndProductService.deleteUser(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }
}