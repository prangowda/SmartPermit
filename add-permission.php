<?php
    include 'connection.php';

    $uid = $_POST['uid'];
    $type = $_POST['type'];
    $desc = $_POST['desc'];
    $name = $_POST['name'];
    $number = $_POST['number'];
    $address = $_POST['address'];
    $validfrom = $_POST['validfrom'];
    $validto = $_POST['validto'];
    $pnum = "PE".rand(100000, 999999)."AY";

    // $uid = 1;
    // $type = "f";
    // $desc = "s";
    // $name = "d";
    // $number = "4";
    // $address = "c";
    // $validfrom = "2021-03-03";
    // $validto = "2020-08-08";
    // $pnum = "PE".rand(100000, 999999)."AY";

    if(mysqli_query($conn, "insert into permission (permission_type, permission_number, description, valid_from, valid_to, user_id, name, address, phone, permission_status) values ('$type', '$pnum', '$desc', '$validfrom', '$validto', '$uid', '$name', '$address', '$number', 'Pending' )"))
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