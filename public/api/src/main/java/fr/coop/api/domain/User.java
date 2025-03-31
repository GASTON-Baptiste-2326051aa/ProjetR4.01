package fr.coop.api.domain;

public record User(
        String id,
        String firstName,
        String name,
        String password
) {}
