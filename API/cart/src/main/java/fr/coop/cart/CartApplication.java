package fr.coop.cart;

import fr.coop.cart.other_api.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class CartApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connexion à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface CartRepositoryInterface utilisée
     *         pour accéder aux données des paniers, voire les modifier
     */
    @Produces
    private CartRepositoryInterface openDbConnection() {
        CartRepositoryMariadb db = null;

        try {
            db = new CartRepositoryMariadb("jdbc:mariadb://mysql-cooperativejavaiutaix.alwaysdata.net/cooperativejavaiutaix_bd", "405910", "azerty123456789_");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }


    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param cartRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes CartRepositoryInterface cartRepo) {
        cartRepo.close();
    }


}
