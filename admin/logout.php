<?php
session_start();
    $_SESSION['id'] = "";
    $_SESSION['name'] = "";
    $_SESSION['login'] = false;

    unset($_SESSION['id']);
    unset($_SESSION['name']);
    unset($_SESSION['login']);

    header("Location: login.php");

?>