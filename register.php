<?php
    include 'connection.php';

    $name = $_POST['name'];
    $number = $_POST['number'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // $name = "demo";
    // $number = "demo";
    // $email = "demo";
    // $password = "demo";

    if(mysqli_query($conn, "insert into user (name, email, contact, password, type, status) values ('$name', '$email', '$number', '$password', 'User', true)"))
    {
        $responce['success']=true;
        $responce['message']="You have registered successfully..!";
    }
    else
    {
        $responce['success']=false;
        $responce['message']="Ooops, Unable to register your data..!";
    }

    echo json_encode($responce);
?>