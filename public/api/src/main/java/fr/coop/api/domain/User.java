package fr.coop.api.domain;

public record User(
        int id,
        String firstName,
        String name,
        String password
) {}
