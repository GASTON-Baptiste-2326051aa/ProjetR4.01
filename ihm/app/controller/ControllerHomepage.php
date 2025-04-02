<?php

namespace controller;

use Exception;
use model\service\ModelApiCart;
use view\ViewHomepage;


class ControllerHomepage implements Controller
{
    private modelApiCart $modelApiCart;
    public function __construct()
    {
        $this->modelApiCart = new ModelApiCart('http://localhost:8080/api-1.0-SNAPSHOT/api/cart');
    }

    /**
     * @throws Exception
     */
    public function execute() : void
    {
        $paniers = $this->modelApiCart->getAllCarts();
        $view = new ViewHomepage();
        $view->show($paniers);
    }
}