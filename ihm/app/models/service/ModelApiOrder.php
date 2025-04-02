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
        $url='/'. $id;
        return $this->request($url);
    }

    /**
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
     * @throws Exception
     */
    public function getAllCommands() : array
    {
        $url = '/command';
        return $this->request($url);
    }
}
