package fr.coop.api.domain;

/**
 * Représente un produit avec ses détails.
 *
 * @param id             L'ID du produit.
 * @param name           Le nom du produit.
 * @param description    La description du produit.
 * @param availableStock Le stock disponible du produit.
 * @param unitType       Le type d'unité du produit.
 */
public record Product(
        String id,
        String name,
        String description,
        double availableStock,
        String unitType
) {}