<?php

namespace views;

/**
 * Layout affiché pour chaque page
 */
class ViewLayout
{
    private string $title;
    private string $content;

    /**
     * Constructeur de la classe
     * @param $title
     * @param $content
     * @version 1.0
     * @author Baptiste GASTON
     */
    public function __construct($title, $content)
    {
        $this->title = $title;
        $this->content = $content;
    }

    /**
     * Affiche le layout
     * @return void
     * @version 1.0
     * @author Baptiste GASTON
     */
    public function show() : void
    {
        ?>
        <!doctype html>
        <html lang="fr">
        <head>
            <meta charset="utf-8"/>
            <title><?= $this->title; ?> - Coopérative </title> <!-- Prend la valeur de $title -->
            <link href="../../../public/_assets/styles/style.css" rel="stylesheet"/>
        </head>
        <body>
        <header>
        </header>
        <main>
            <?=$this->content ?>
        </main>
        </body>
        <footer>
            <p>© 2024 - Collaborative</p>
        </footer>
        </html>
        <?php
    }
}

?>