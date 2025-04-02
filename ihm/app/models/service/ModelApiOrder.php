<?php

namespace models\service;

use Exception;

/**
 * Class ModelApiOrder
 * @package models\service
 * @version 1.0
 * @autor Baptiste GASTON
 */
class ModelApiOrder extends ModelApi
{
    public function __construct($link)
    {
        parent::__construct($link);
    }

    /**
     * Récupère une commande par son ID.
     * @param string $id
     * @return array
     * @throws Exception
     */
    public function getCommand(string $id) : array
    {
        $url='/'. $id;
        return $this->request($url);
    }

    /**
     * Valide une commande.
     * @param $id
     * @param $cartId
     * @param $userId
     * @param $date
     * @param $relayAddress
     * @param $valid
     * @return string
     * @throws Exception
     */
    public function validateOrder($id, $cartId, $userId, $date, $relayAddress, $valid): string
    {
        $orderData = [
            'id' => $id,
            'cartId' => $cartId,
            'userId' => $userId,
            'date' => $date,
            'relayAddress' => $relayAddress,
            'valid' => $valid
        ];
        $response = $this->request('', $orderData, 'POST');
        if ($response === 'created') {
            return $response;
        } else {
            throw new Exception("Failed to create order: " . json_encode($response));
        }
    }

    /**
     * Récupère toutes les commandes.
     * @return array
     * @throws Exception
     */
    public function getAllCommands() : array
    {
        $url = '/command';
        return $this->request($url);
    }
}
