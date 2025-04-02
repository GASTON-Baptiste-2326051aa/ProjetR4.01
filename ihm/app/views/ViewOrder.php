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
        <section class="order-page">
            <h1>Ma Commande</h1>

            <?php if (empty($paniers)) { ?>
                <p>Votre commande est vide.</p>
            <?php } else { ?>
                <div class="paniers-container">
                    <?php foreach ($paniers as $panier) { ?>
                        <div class="panier-card">
                            <h3><?= htmlspecialchars($panier['name']) ?></h3>
                            <p><strong>Prix :</strong> <?= $panier['price'] ?>€</p>
                            <p><strong>Quantité disponible :</strong> <?= $panier['available_quantity'] ?></p>

                            <form method="post" action="/index.php?action=order&delete&id=<?= $panier['id'] ?>">
                                <input type="hidden" name="id" value="<?= $panier['id'] ?>">
                                <button type="submit" class="btn btn-delete">Supprimer</button>
                            </form>
                        </div>
                    <?php } ?>
                </div>

                <div class="actions">
                    <form method="post" action="/index.php?action=order&clear">
                        <button type="submit" class="btn btn-clear">Vider la commande</button>
                    </form>

                    <form method="post" action="/index.php?action=order&validate">
                        <button type="submit" class="btn btn-validate">Valider la commande</button>
                    </form>
                </div>
            <?php } ?>

            <a href="/index.php" class="btn btn-home">Retour à l'accueil</a>
        </section>

        <?php
        $layout = new ViewLayout('Commande', ob_get_clean());
        $layout->show();
    }
}
