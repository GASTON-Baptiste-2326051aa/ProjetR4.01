<?php

namespace view;

use views\ViewLayout;

class ViewCommand
{
    public function show($command) : void
    {
        ob_start();
        ?>
        <section class='command-conteneur'>

        </section>
        <?php
        $layout = new ViewLayout('Command', ob_get_clean());
        $layout->show();
    }
}