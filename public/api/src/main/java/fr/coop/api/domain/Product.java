package fr.coop.api.domain;

public record Product(
        String id,
        String name,
        String description,
        double availableStock,
        String unitType
) {}
