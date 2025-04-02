<?php

namespace controllers;

use Exception;
use models\service\ModelApiOrder;
use views\ViewOrder;

class ControllerOrder implements Controller
{
    private ModelApiOrder $modelApiCommands;
    public function __construct()
    {
        $this->modelApiCommands = new ModelApiOrder('http://localhost:8080/command-1.0-SNAPSHOT/api/orders');
    }

    /**
     * @throws Exception
     */
    public function execute() : void
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['id'])) {
            $id = (int) $_POST['id'];
            if (!isset($_SESSION['commands'])) {
                $_SESSION['commands'] = [];
            }
            if (!in_array($id, $_SESSION['commands'])) {
                $_SESSION['commands'][] = $id;
            }
        }
        $infoCommands = [];
        foreach ($_SESSION['commands'] as $command) {
            $newCommand = $this->modelApiCommands->getCommand($command);
            if ($newCommand) {
                $infoCommands[] = $newCommand;
            }
        }
        $view = new ViewOrder();
        $view->show($infoCommands);
    }
}
