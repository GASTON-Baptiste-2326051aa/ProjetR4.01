package fr.coop.api.api;

import fr.coop.api.data.interfaces.UserRepositoryInterface;
import fr.coop.api.domain.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class UserRepositoryAPI extends RepositoryAPI implements UserRepositoryInterface {
    public UserRepositoryAPI(String url) {
        super(url);
    }

    @Override
    public String createUser(String firstName, String name, String password) {
        return null;
    }

    @Override
    public boolean updateUser(String userId, String firstName, String name, String newPassword) {
        return false;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public User getUser(String userId) {
        User user = null;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget endPoint = target.path("user/" + userId);
        Response response = endPoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200)
            user = response.readEntity(User.class);

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
