<?php

namespace controllers;

use Exception;
use models\service\ModelApiCart;
use views\ViewHomepage;

/**
 * Class ControllerHomepage
 * @package controllers
 * @version 1.0
 * @author Baptiste GASTON
 */
class ControllerHomepage implements Controller
{
    private modelApiCart $modelApiCart;

    public function __construct()
    {
        $this->modelApiCart = new ModelApiCart('http://localhost:8080/cart-1.0-SNAPSHOT/api');
    }

    /**
     * Exécute les actions de la page d'accueil.
     * @throws Exception
     */
    public function execute() : void
    {
        $carts = $this->modelApiCart->getAllCarts();
        $view = new ViewHomepage();
        $view->show($carts);
    }
}
