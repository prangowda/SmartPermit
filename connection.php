<?php
    $server = "localhost";
    $user = "root";
    $password = "";
    $database = "ocr";

    $conn = mysqli_connect($server, $user, $password, $database);

    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
      }
    
?>