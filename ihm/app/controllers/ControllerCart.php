<?php

namespace controllers;

use Exception;
use models\service\ModelApiCart;
use views\ViewCart;

/**
 * Class ControllerCart
 * @package controllers
 * @version 1.0
 * @author Baptiste GASTON
 */
class ControllerCart implements Controller
{
    private ModelApiCart $modelApiCart;

    public function __construct()
    {
        $this->modelApiCart = new ModelApiCart('http://localhost:8080/cart-1.0-SNAPSHOT/api');
    }

    /**
     * Exécute les actions pour le panier.
     * @throws Exception
     */
    public function execute() : void
    {
        if (!isset($_GET['id']) || !is_numeric($_GET['id'])) {
            header("Location: /index.php?action=homepage");
            exit;
        }

        $id = (int) $_GET['id'];
        $panier = $this->modelApiCart->getCart($id);

        if (!$panier) {
            header("Location: /index.php?action=homepage");
            exit;
        }

        $view = new ViewCart();
        $view->show($panier);
    }
}
