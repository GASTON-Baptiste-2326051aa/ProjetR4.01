<?php

namespace controller;
use view\ViewCart;

class ControllerCart implements Controller
{
    public function __construct()
    {
    }

    public function execute() : void
    {
        //TODO : récupérer les paniers avec un model
        $paniers = [];
        $view = new ViewCart();
        $view->show($paniers);
    }
}