<?php

namespace controllers;

use Exception;
use models\service\ModelApiCart;
use models\service\ModelApiOrder;
use views\ViewOrder;

/**
 * Class ControllerOrder
 * @package controllers
 * @version 1.0
 * @author Baptiste GASTON
 */
class ControllerOrder implements Controller
{
    private ModelApiOrder $modelApiOrders;
    private ModelApiCart $modelApiCart;

    public function __construct()
    {
        $this->modelApiOrders = new ModelApiOrder('http://localhost:8080/order-1.0-SNAPSHOT/api/orders');
        $this->modelApiCart = new ModelApiCart('http://localhost:8080/cart-1.0-SNAPSHOT/api');
    }

    /**
     * Exécute les actions en fonction de la requête HTTP.
     * @throws Exception
     */
    public function execute(): void
    {
        if (session_status() == PHP_SESSION_NONE) {
            session_start();
        }

        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            if (isset($_GET['delete']) && isset($_POST['id'])) {
                $this->removeFromOrder((int)$_POST['id']);
            } elseif (isset($_GET['clear'])) {
                $this->clearOrder();
            } elseif (isset($_GET['validate'])) {
                $this->validateOrder();
            } elseif (isset($_POST['id'])) {
                $this->addToOrder((int)$_POST['id']);
            }
        }

        $this->showOrder();
    }

    /**
     * Ajoute un article à la commande.
     * @param int $id
     */
    private function addToOrder(int $id): void
    {
        if (!isset($_SESSION['order']) || !is_array($_SESSION['order'])) {
            $_SESSION['order'] = [];
        }
        if (!in_array($id, $_SESSION['order'])) {
            $_SESSION['order'][] = $id;
        }
    }

    /**
     * Valide la commande en cours.
     * @throws Exception
     */
    private function validateOrder(): void
    {
        if (!isset($_SESSION['user']) || empty($_SESSION['order'])) {
            throw new Exception("Utilisateur non connecté ou panier vide.");
        }

        $userId = $_SESSION['user'];
        $cartIds = array_keys($_SESSION['order']);
        $orderId = time();
        $date = date('Y-m-d');
        $relayAddress = "Adresse du relais par défaut";
        $valid = 'false';
        $this->modelApiOrders->validateOrder($orderId, $cartIds, $userId, $date, $relayAddress, $valid);
    }

    /**
     * Supprime un article de la commande.
     * @param int $id
     */
    private function removeFromOrder(int $id): void
    {
        if (isset($_SESSION['order'])) {
            $_SESSION['order'] = array_filter($_SESSION['order'], fn($cartId) => $cartId !== $id);
        }
    }

    /**
     * Vide la commande en cours.
     */
    private function clearOrder(): void
    {
        $_SESSION['order'] = [];
    }

    /**
     * Affiche la commande en cours.
     * @throws Exception
     */
    private function showOrder(): void
    {
        $infoCarts = [];
        if (!empty($_SESSION['order'])) {
            foreach ($_SESSION['order'] as $idCart) {
                $cart = $this->modelApiCart->getCart($idCart);
                if ($cart) {
                    $infoCarts[] = $cart;
                }
            }
        }

        $view = new ViewOrder();
        $view->show($infoCarts);
    }
}
