<?php

namespace view;

use views\ViewLayout;

class ViewLogin
{

    public function show(): void
    {
        ob_start();
        ?>
        <section>
            <h1>Connexion</h1>
            <form action="/public/index.php?action=login" method="post">
               <form action="/public/index.php?action=login" method="post">
                   <label for="nom">Nom:</label>
                   <input type="text" id="nom" name="nom" required>
                   <label for="prenom">Prénom:</label>
                   <input type="text" id="prenom" name="prenom" required>
                   <label for="mdp">Mot de passe:</label>
                   <input type="password" id="mdp" name="mdp" required
                   <input type="submit" value="Connexion">
               </form>
            </form>
            <?php
            $layout = new ViewLayout('Login', ob_get_clean());
            $layout->show();
    }
}


