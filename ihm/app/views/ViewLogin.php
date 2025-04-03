<?php

namespace views;

class ViewLogin
{
    /**
     * Affiche la vue de connexion.
     *
     * @param string|null $error Message d'erreur à afficher (optionnel).
     * @return void
     */
    public function show($error = null): void
    {
        ob_start();
        ?>
        <section class="login">
            <?php if ($error) : ?>
                <div class="error">
                    <p> <?php echo $error?></p>
                </div>
            <?php endif; ?>
            <h1>Connexion</h1>
            <form action="/index?action=login" method="POST">
                   <label for="id_client">ID:</label>
                   <input type="text" id="id_client" name="id_client" required>

                   <label for="password">Mot de passe:</label>
                   <input type="password" id="password" name="password" required>
                   <input type="submit" value="Connexion">
            </form>
            <?php
            $layout = new ViewLayout('Login', ob_get_clean());
            $layout->show();
    }
}
