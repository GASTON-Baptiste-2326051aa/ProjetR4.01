<?php

namespace models\service;

use Exception;

/**
 * Class ModelApi
 * @package models\service
 * @version 1.0
 * @author Baptiste GASTON
 */
class ModelApi
{
    protected string $link;

    public function __construct(string $link)
    {
        $this->link = $link;
    }

    /**
     * Effectue une requête HTTP.
     * @param string $url
     * @param array $data
     * @param string $method
     * @return mixed
     * @throws Exception
     */
    public function request(string $url, array $data = [], string $method = 'GET')
    {
        $url = $this->link . $url;

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);

        if (strtoupper($method) === 'POST') {
            curl_setopt($ch, CURLOPT_POST, true);
            // encode $data en JSON
            $jsonData = json_encode($data);
            curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonData);
            curl_setopt($ch, CURLOPT_HTTPHEADER, [
                'Content-Type: application/json',
                'Accept: application/json'
            ]);
        } elseif (!empty($data)) {
            $url .= '?' . http_build_query($data);
            curl_setopt($ch, CURLOPT_URL, $url);
        }

        // Exécuter la requête
        $response = curl_exec($ch);

        if (curl_errno($ch)) {
            throw new Exception("cURL error: " . curl_error($ch));
        }

        curl_close($ch);

        $decodedResponse = json_decode($response, true);
        if (json_last_error() !== JSON_ERROR_NONE) {
            if ($response === "created") {
                return $response;
            }
            throw new Exception("Invalid JSON response: " . $response);
        }

        return $decodedResponse;
    }
}
