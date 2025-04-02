<?php

namespace controllers;

use Exception;
use models\service\ModelApiProductUser;
use views\ViewLogin;

/**
 * Class ControllerLogin
 * @package controllers
 * @version 1.0
 * @author Baptiste GASTON
 */
class ControllerLogin implements Controller
{
    private ModelApiProductUser $modelApiProductUser;

    public function __construct()
    {
        $this->modelApiProductUser =
            new ModelApiProductUser('http://localhost:8080/user_and_product-1.0-SNAPSHOT/api/user_and_product');
    }

    /**
     * Exécute les actions de connexion.
     * @throws Exception
     */
    public function execute(): void
    {
        if (isset($_SESSION['logged']) && $_SESSION['logged'] === true) {
            header('Location: index.php?controller=homepage');
            exit();
        }

        if (isset($_POST['id_client']) && isset($_POST['password'])) {
            $id = htmlspecialchars($_POST['id_client']);
            $password = htmlspecialchars($_POST['password']);

            $isLogin = $this->modelApiProductUser->login($id, $password);

            if (isset($isLogin['exists']) && $isLogin['exists'] === true) { // Correction ici
                $_SESSION['user'] = $id;
                $_SESSION['logged'] = true;
                header('Location: index.php?controller=homepage');
                exit();
            } else {
                var_dump($isLogin);
                $view = new ViewLogin();
                $view->show('Login failed');
            }
        } else {
            $view = new ViewLogin();
            $view->show();
        }
    }
}
