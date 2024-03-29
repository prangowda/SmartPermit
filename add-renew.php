<?php
    include 'connection.php';

    $uid = $_POST['uid'];
    $type = $_POST['type'];
    $name = $_POST['name'];
    $number = $_POST['number'];
    $address = $_POST['address'];
    $trade = $_POST['trade'];
    $exp = $_POST['exp'];
    $lino = $_POST['lino'];

    // $uid = 1;
    // $type = "f";
    // $name = "d";
    // $number = "4";
    // $address = "c";
    // $trade = "dd";
    // $exp = "l";
    // $lino = "p";

    if(mysqli_query($conn, "insert into renew (license_type, license_number, name, address, contact_number, trade_address, user_id, status, expire_date) values ('$type', '$lino', '$name', '$address', '$number', '$trade', '$uid', 'Pending' ,'$exp')"))
    {
        $responce['success']=true;
        $responce['message']="Your data has been submitted successfully..!";
    }
    else
    {
        $responce['success']=false;
        $responce['message']="Ooops, Unable to send your data..!";
    }

    echo json_encode($responce);
?>