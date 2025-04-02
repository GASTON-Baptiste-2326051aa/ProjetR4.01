<?php

namespace views;

use views\ViewLayout;

class ViewLogin
{

    public function show($error = null): void
    {
        ob_start();
        ?>
        <section>
            <?php if ($error): ?>
                <div class="error">
                    <p> <?php echo $error?></p>
                </div>
            <?php endif; ?>
            <h1>Connexion</h1>
            <form action="/public/index.php?action=login" method="post">
                   <label for="id">ID:</label>
                   <input type="text" id="id" name="id" required>

                   <label for="mdp">Mot de passe:</label>
                   <input type="password" id="mdp" name="mdp" required
                   <input type="submit" value="Connexion">
            </form>
            <?php
            $layout = new ViewLayout('Login', ob_get_clean());
            $layout->show();
    }
}


