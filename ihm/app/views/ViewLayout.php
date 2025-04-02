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
     * @param string $title
     * @param string $content
     */
    public function __construct(string $title, string $content)
    {
        $this->title = $title;
        $this->content = $content;
    }

    /**
     * Affiche le layout
     * @return void
     */
    public function show(): void
    {
        ?>
        <!doctype html>
        <html lang="fr">
        <head>
            <meta charset="utf-8"/>
            <title><?= htmlspecialchars($this->title); ?> - Coopérative</title>
            <link href="/_assets/styles/style.css" rel="stylesheet"/>
        </head>
        <body>

        <header>
            <nav>
                <ul>
                    <li><a href="/index.php">Accueil</a></li>
                    <li><a href="/index.php?action=order">Ma Commande</a></li>
                </ul>
            </nav>
        </header>

        <main>
                <?= $this->content ?>
        </main>

        <footer>
            <p>© 2024 - Collaborative</p>
        </footer>

        </body>
        </html>
        <?php
    }
}
