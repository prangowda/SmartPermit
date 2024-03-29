<?php
    include 'connection.php';

    // $email = "demo";
    // $password = "demo";

    $email = $_POST['email'];
    $password = $_POST['password'];

    $res = mysqli_query($conn, "select id, type from user where email = '$email' and password = '$password' and status = true");
    if(mysqli_num_rows($res)>0)
    {
        $row = mysqli_fetch_assoc($res);
        $responce['success']=true;
        $responce['id']=$row['id'];
        $responce['type']=$row['type'];
        $responce['message']="You have logged in successfully..!"; 
    }
    else
    {
        $responce['success']=false;
        $responce['message']="Invalid credentials you entered..!";
    }

    echo json_encode($responce);
?>