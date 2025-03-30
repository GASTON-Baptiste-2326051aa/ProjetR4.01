package fr.coop.api.domain;

public record Product(
        int id,
        String name,
        String description,
        double availableStock,
        String quantityUnit
) {}
