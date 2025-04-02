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
        $url = '/user/exist';
        $data = [
            'id' => $id,
            'password' => $password
        ];
        return $this->request($url,$data,'POST' );
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