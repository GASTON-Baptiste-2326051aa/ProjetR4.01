<?php

namespace views;


class ViewCart
{

    public function __construct()
    {
    }

    public function show($cart) : void
    {
        ob_start();
        ?>
        <section class='cart-conteneur'>
            <h1></h1> <--!TODO : afficher le nom du panier-->
        </section>
        <?php
        $layout = new ViewLayout('Cart', ob_get_clean());
        $layout->show();
    }


}