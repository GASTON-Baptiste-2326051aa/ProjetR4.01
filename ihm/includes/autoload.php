<?php

/**
 * Enregistre une fonction d'autoload pour charger automatiquement les classes.
 *
 * @package Autoload
 * @version 1.0
 * @author Baptiste GASTON
 */

spl_autoload_register(function ($class) {
    $classPath = str_replace('\\', DIRECTORY_SEPARATOR, $class);

    $baseDirs = [
        __DIR__ . DIRECTORY_SEPARATOR . '..' . DIRECTORY_SEPARATOR . 'app' . DIRECTORY_SEPARATOR,
        __DIR__ . DIRECTORY_SEPARATOR, // includes lui-même
    ];

    foreach ($baseDirs as $baseDir) {
        $file = $baseDir . $classPath . '.php';
        if (file_exists($file)) {
            include_once $file;
            return;
        }
    }

    error_log("Autoload failed for class: $class");
});
