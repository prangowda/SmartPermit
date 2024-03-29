<?php
    include 'connection.php';

    $uid = $_POST['uid'];
    $type = $_POST['type'];
    $name = $_POST['name'];
    $number = $_POST['number'];
    $address = $_POST['address'];
    $trade = $_POST['trade'];
    $pnum = "LI".rand(100000, 999999)."AY";

    // $uid = 1;
    // $type = "f";
    // $name = "d";
    // $number = "4";
    // $address = "c";
    // $trade = "dd";
    // $pnum = "PE".rand(100000, 999999)."AY";

    if(mysqli_query($conn, "insert into license (license_type, license_number, name, address, contact_number, trade_address, user_id, status) values ('$type', '$pnum', '$name', '$address', '$number', '$trade', '$uid', 'Pending' )"))
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