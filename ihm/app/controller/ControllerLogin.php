<?php
namespace controller;
use ModelApiProductUser;
use view\ViewLogin;

class ControllerLogin implements Controller
{
    private modelApiProductUser $modelApiProductUser;
    public function __construct()
    {
        //TODO : initialiser la connexion à l'API
        $this->modelApiProductUser = new modelApiProductUser();

    }

    public function execute() : void
    {
        //TODO : vérifier si l'utilisateur est connecté via l'api
        $isLogin = $this->modelApiProductUser->isLogin();
        if($isLogin)
        {

            $_SESSION['isLogin'] = true;
            header('Location: /index.php?action=homepage');
        }
        else
        {
            //TODO : afficher la page de connexion
            $view = new ViewLogin();
            $view->show();
        }
    }

}