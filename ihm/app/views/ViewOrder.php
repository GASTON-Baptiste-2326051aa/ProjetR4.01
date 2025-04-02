<?php
namespace views;

class ViewOrder
{
    public function __construct()
    {
    }

    public function show($paniers) : void
    {
        ob_start();
        ?>
        <section>
            <h1>Ma Commande</h1>
            <?php if (empty($paniers)) { ?>
                <p>Votre commande est vide.</p>
            <?php } else { ?>
                <ul>
                    <?php foreach ($paniers as $panier) { ?>
                        <li>
                            <?= htmlspecialchars($panier['name']) ?> - <?= $panier['price'] ?>€
                            <form method="post" action="/index.php?action=remove_from_command">
                                <input type="hidden" name="id" value="<?= $panier['id'] ?>">
                                <button type="submit">Supprimer</button>
                            </form>
                        </li>
                    <?php } ?>
                </ul>
                <form method="post" action="/index.php?action=clear_command">
                    <button type="submit">Vider la commande</button>
                </form>
            <?php } ?>
        </section>

        <?php
        $layout = new ViewLayout('Command', ob_get_clean());
        $layout->show();
    }
}
