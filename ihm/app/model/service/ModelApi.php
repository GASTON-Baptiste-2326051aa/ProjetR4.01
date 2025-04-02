<?php

namespace model\service;

use Exception;

class ModelApi
{
    private string $link ;
    public function __construct(string $link)
    {
        $this->link = $link;
    }

    /**
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
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
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
            throw new Exception(curl_error($ch));
        }
        curl_close($ch);
        return json_decode($response, true) ?? $response; // Retourne JSON décodé ou réponse brute
    }

}