<?php
    include 'connection.php';
    $status = $_POST['status'];
    $staff = $_POST['police'];
    $licence_no = $_POST['licence_no'];

    if(mysqli_query($conn, "update license set status = '$status', police_id = '$staff' where license_number = '$licence_no'"))
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