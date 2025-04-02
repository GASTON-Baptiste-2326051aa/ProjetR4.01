package fr.coop.user_and_product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Classe principale de l'application définissant le chemin de base pour les ressources JAX-RS.
 */
@ApplicationPath("/api")
//@ApplicationScoped
public class CoopApplication extends Application {
}