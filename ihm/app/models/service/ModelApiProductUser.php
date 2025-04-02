<?php

namespace models\service;

use Exception;

/**
 * Class ModelApiProductUser
 * @package models\service
 * @version 1.0
 * @author Baptiste GASTON
 */
class ModelApiProductUser extends ModelApi
{
    public function __construct($link)
    {
        parent::__construct($link);
    }

    /**
     * Authentifie un utilisateur.
     * @param string $id
     * @param string $password
     * @return array
     * @throws Exception
     */
    public function login(string $id, string $password): array
    {
        $url = '/user/exists';
        $data = [
            'id' => $id,
            'password' => $password
        ];

        var_dump(json_encode($data)); // Ajoute cette ligne pour voir le JSON envoyé
        return $this->request($url, $data, 'POST');
    }

    /**
     * Récupère un utilisateur par son ID.
     * @param string $id
     * @return array
     * @throws Exception
     */
    public function getUser(string $id): array
    {
        $url = '/user/' . $id;
        return $this->request($url);
    }

    /**
     * Récupère tous les utilisateurs.
     * @return array
     * @throws Exception
     */
    public function getAllUsers(): array
    {
        $url = '/user';
        return $this->request($url);
    }

    /**
     * Récupère un produit par son ID.
     * @param string $id
     * @return array
     * @throws Exception
     */
    public function getProduct(string $id): array
    {
        $url = '/product/' . $id;
        return $this->request($url);
    }

    /**
     * Récupère tous les produits.
     * @return array
     * @throws Exception
     */
    public function getAllProducts(): array
    {
        $url = '/product';
        return $this->request($url);
    }
}
