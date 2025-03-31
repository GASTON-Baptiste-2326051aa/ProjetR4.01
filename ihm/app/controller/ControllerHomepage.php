<?php

namespace controller;

use view\ViewHomepage;


class ControllerHomepage implements Controller
{
    public function __construct()
    {

    }

    public function execute() : void
    {
        //TODO : récupérer les paniers avec un model
        $paniers = [];
        $view = new ViewHomepage();
        $view->show($paniers);
    }
}