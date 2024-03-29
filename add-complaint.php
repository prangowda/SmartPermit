<?php
    include 'connection.php';

    $uid = $_POST['uid'];
    $type = $_POST['type'];
    $title = $_POST['title'];
    $desc = $_POST['desc'];
    $name = $_POST['name'];
    $number = $_POST['number'];
    $address = $_POST['address'];
    $city = $_POST['city'];
    $pin = $_POST['pin'];
    $cnum = "CO".rand(100000, 999999)."AY";

    // $uid = 1;
    // $type = "f";
    // $title = "a";
    // $desc = "s";
    // $name = "d";
    // $number = "4";
    // $address = "c";
    // $city = "v";
    // $pin = "5";
    // $cnum = "CO".rand(100000, 999999)."AY";

    if(mysqli_query($conn, "insert into complaints (complaint_type, complaint_number, complaint, description, user_id, name, address, contact_number, city, pincode, complaint_status) values ('$type', '$cnum', '$title', '$desc', '$uid', '$name', '$address', '$number', '$city', '$pin', 'Pending' )"))
    {
        $responce['success']=true;
        $responce['message']="Your complaint has been submitted successfully..!";
    }
    else
    {
        $responce['success']=false;
        $responce['message']="Ooops, Unable to send your data..!";
    }

    echo json_encode($responce);
?>