<?php

namespace views;


class ViewHomepage
{
    public function  __construct()
    {
    }

    public function show($paniers) : void
    {
        ob_start();
        ?>
        <section>
            <h1>Accueil</h1>
            <p>Bienvenue sur la page d'accueil de la coopérative.</p>
            <h2>Les paniers disponibles</h2>

            <?php foreach ($paniers as $panier) {
                //TODO : afficher les paniers
                ?>

                <?php
            }
            ?>
            <a href="/public/index.php?action=cart">Voir mon panier</a>
        </section>
        <?php
        $layout = new ViewLayout('Homepage', ob_get_clean());
        $layout->show();
    }
}