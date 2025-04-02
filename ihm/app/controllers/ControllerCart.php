<?php

namespace controllers;
use views\ViewCart;

class ControllerCart implements Controller
{
    public function __construct()
    {
    }

    public function execute() : void
    {
        $paniers = [];
        $view = new ViewCart();
        $view->show($paniers);
    }
}