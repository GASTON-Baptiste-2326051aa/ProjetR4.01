<?php


namespace models\service;

use Exception;

class ModelApiCart extends ModelApi
{
    public function __construct($link)
    {
        parent::__construct($link);
    }

    /**
     * @throws Exception
     */
    public function getCart(string $id) : array
    {
        $url = '/cart' . '/' . $id;
        return $this->request($url);
    }

    /**
     * @throws Exception
     */
    public function getAllCarts() : array
    {
        $url = '/cart';
        return $this->request($url);
    }

    public function getCartProduct() : void
    {
        //TODO : récupérer les produits d'un panier
    }
}
