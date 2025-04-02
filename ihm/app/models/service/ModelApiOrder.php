<?php

namespace models\service;

use Exception;

class ModelApiOrder extends ModelApi
{
    public function __construct($link)
    {
        parent::__construct($link);
    }

    /**
     * @throws Exception
     */
    public function getCommand(string $id) : array
    {
        $url= '/command' . '/' . $id;
        return $this->request($url);
    }
    public function validateCommand()
    {
        //TODO : valider une commande
    }

    /**
     * @throws Exception
     */
    public function getAllCommands() : array
    {
        $url = '/command';
        return $this->request($url);
    }
}
