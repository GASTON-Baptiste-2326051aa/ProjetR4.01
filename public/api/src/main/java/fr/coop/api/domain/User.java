package fr.coop.api.domain;

/**
 * Représente un utilisateur avec ses détails.
 *
 * @param id        L'ID de l'utilisateur.
 * @param firstName Le prénom de l'utilisateur.
 * @param name      Le nom de l'utilisateur.
 * @param password  Le mot de passe de l'utilisateur.
 */
public record User(
        String id,
        String firstName,
        String name,
        String password
) {}