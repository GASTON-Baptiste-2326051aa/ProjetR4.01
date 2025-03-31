<?php
namespace controller;
use Exception;
use model\service\ModelApiProductUser;
use view\ViewLogin;

class ControllerLogin implements Controller
{
    private modelApiProductUser $modelApiProductUser;
    public function __construct()
    {
        $this->modelApiProductUser = new modelApiProductUser('http://localhost:8080/api-1.0-SNAPSHOT/api/user_and_product');

    }

    /**
     * @throws Exception
     */
    public function execute() : void
    {
        if ((isset($_GET[$_POST['id']])) || isset($_POST['password'])) {
            $id = htmlspecialchars($_POST['id']);
            $password = htmlspecialchars($_POST['password']);
            $isLogin = $this->modelApiProductUser->login($id, $password);
            if($isLogin['status'] == 200)
            {
                $user = $this->modelApiProductUser->getUser($id);
                session_start();
                $_SESSION['user'] = $user;
                $_SESSION['logged'] = true;
                header('Location: index.php?controller=homepage');
            }
            else
            {
                $view = new ViewLogin();
                $view->show('Login failed');
            }
        }
        else
        {
            $view = new ViewLogin();
            $view->show();
        }
    }

}