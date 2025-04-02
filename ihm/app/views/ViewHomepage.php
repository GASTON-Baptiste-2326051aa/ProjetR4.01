<?php
namespace views;

class ViewHomepage
{
    public function __construct()
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

            <div class="paniers-container">
                <?php foreach ($paniers as $panier) { ?>
                    <div class="panier-card">
                        <h3>
                            <a href="/index.php?action=cart&id=<?= $panier['id'] ?>">
                                <?= htmlspecialchars($panier['name']) ?>
                            </a>
                        </h3>
                        <p><strong>Prix :</strong> <?= $panier['price'] ?>€</p>
                        <p><strong>Quantité disponible :</strong> <?= $panier['available_quantity'] ?></p>
                        <form method="POST" action="/index.php?action=command">
                            <input type="hidden" name="id" value="<?= $panier['id'] ?>">
                            <button type="submit">Ajouter à la commande</button>
                        </form>
                    </div>
                <?php } ?>
            </div>

            <a href="/index.php?action=command" class="view-cart">Voir ma commande</a>
        </section>

        <?php
        $layout = new ViewLayout('Homepage', ob_get_clean());
        $layout->show();
    }
}

