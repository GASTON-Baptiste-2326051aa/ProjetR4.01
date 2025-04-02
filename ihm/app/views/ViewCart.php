<?php
namespace views;

class ViewCart
{
    public function __construct()
    {
    }

    public function show($panier) : void
    {
        ob_start();
        ?>
        <section class="cart-section">
            <h1>Votre panier : <?= htmlspecialchars($panier['name']) ?></h1>
            <p><strong>Description :</strong> <?= htmlspecialchars($panier['description']) ?></p>
            <p><strong>Prix :</strong> <?= $panier['price'] ?>€</p>
            <p><strong>Quantité disponible :</strong> <?= $panier['available_quantity'] ?></p>

            <a href="/index.php?action=homepage" class="back-link">Retour à l'accueil</a>
        </section>

        <?php
        $layout = new ViewLayout('Panier', ob_get_clean());
        $layout->show();
    }
}
