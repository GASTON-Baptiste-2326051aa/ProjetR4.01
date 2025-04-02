<?php

namespace models\service;

use Exception;

/**
 * Class ModelApiCart
 * @package models\service
 * @version 1.0
 * @author Baptiste GASTON
 */
class ModelApiCart extends ModelApi
{
    public function __construct($link)
    {
        parent::__construct($link);
    }

    /**
     * Récupère un panier par son ID.
     * @param string $id
     * @return array
     * @throws Exception
     */
    public function getCart(string $id) : array
    {
        $url = '/carts' . '/' . $id;
        return $this->request($url);
    }

    /**
     * Récupère tous les paniers.
     * @return array
     * @throws Exception
     */
    public function getAllCarts() : array
    {
        $url = '/carts';
        return $this->request($url);
    }

    /**
     * Récupère les produits d'un panier.
     */
    public function getCartProduct() : void
    {
        //TODO : récupérer les produits d'un panier
    }
}
