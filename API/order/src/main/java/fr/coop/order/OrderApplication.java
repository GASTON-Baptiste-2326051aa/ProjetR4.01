package fr.coop.order;

import fr.coop.order.data.OrderRepositoryInterface;
import fr.coop.order.data.OrderRepositoryMariadb;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class OrderApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface OrderRepositoryInterface utilisée
     *          pour accéder aux données des commandes, voire les modifier
     */
    @Produces
    private OrderRepositoryInterface openDbConnection(){
        OrderRepositoryMariadb db = null;

        try{
            db = new OrderRepositoryMariadb("jdbc:mariadb://mysql-cooperativejavaiutaix.alwaysdata.net/cooperativejavaiutaix_bd", "405910", "azerty123456789_");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param orderRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes OrderRepositoryInterface orderRepo ) {
        orderRepo.close();
    }
}