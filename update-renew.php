<?php
    include 'connection.php';

    // $status = $_POST['status'];
    // $staff = $_POST['staff'];
    // $licence_no = $_POST['licence_no'];

    $status = $_POST['status'];
    $staff = $_POST['staff'];
    $licence_no = $_POST['licence_no'];
    $exp = $_POST['exp'];

    if(mysqli_query($conn, "update renew set status = '$status', staff_id = '$staff', expire_date = '$exp' where license_number = '$licence_no'"))
    {
        $responce['success']=true;
        $responce['message']="Data updated successfully..!";
    }
    else
    {
        $responce['success']=false;
        $responce['message']="Ooops, Unable to update data..!";
    }

    echo json_encode($responce);
?>