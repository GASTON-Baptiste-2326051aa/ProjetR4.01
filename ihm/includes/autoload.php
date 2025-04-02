<?php

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
