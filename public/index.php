<?php

use controller\ControllerHomepage;

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

require '../ihm/includes/autoload.php';

include '../ihm/app/controller/ControllerHomepage.php';

try {
    if (!isset($_SESSION['logged'])) {
        $controller = new ControllerHomepage();
        $controller->execute();
    }
    if (filter_input(INPUT_GET, 'action')) {
        $action = filter_input(INPUT_GET, 'action', FILTER_SANITIZE_SPECIAL_CHARS);
        $str_ctrl = 'controllers\\Controller' . ucfirst($action);

        if (class_exists($str_ctrl)) {
            $controller = new $str_ctrl(); // Instanciation dynamique du contrôleur
            if (isset($_GET['page'])) {
                $page = urldecode($_GET['page']);
            } elseif (isset($_GET['id'])) {
                $page = urldecode($_GET['id']);
            } else {
                $page = 0;
            }
            $controller->execute([$page]); // Appel de la méthode non statique
        } else {
            throw new Exception('Le contrôleur demandé n\'existe pas.');
        }
    } elseif (filter_input(INPUT_GET, 'ctrl')) {
        $ctrl = filter_input(INPUT_GET, 'ctrl', FILTER_SANITIZE_SPECIAL_CHARS);
        $str_ctrl = 'controllers\\Controller' . ucfirst($ctrl);

        if (class_exists($str_ctrl)) {
            $controller = new $str_ctrl(); // Instanciation dynamique du contrôleur
            $controller->execute();
        } else {
            throw new Exception('Le contrôleur demandé n\'existe pas.');
        }
    } else {
        $controller = new ControllerHomepage();
        $controller->execute();
    }
} catch (Exception $e) {
    echo $e->getMessage();
}