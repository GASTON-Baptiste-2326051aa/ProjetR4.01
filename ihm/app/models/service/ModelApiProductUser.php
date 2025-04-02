<?php

namespace models\service;

use Exception;

class ModelApiProductUser extends ModelApi
{
    public function __construct($link)
    {
        parent::__construct($link);
    }

    /**
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
     * @throws Exception
     */
    public function getUser(string $id): array
    {
        $url = '/user/' . $id;
        return $this->request($url);
    }

    /**
     * @throws Exception
     */
    public function getAllUsers(): array
    {
        $url = '/user';
        return $this->request($url);
    }

    /**
     * @throws Exception
     */
    public function getProduct(string $id): array
    {
        $url = '/product/' . $id;
        return $this->request($url);
    }

    /**
     * @throws Exception
     */
    public function getAllProducts(): array
    {
        $url = '/product';
        return $this->request($url);
    }
}
