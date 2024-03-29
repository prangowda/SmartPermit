<?php
    include 'connection.php';

    // $status = $_POST['status'];
    // $staff = $_POST['staff'];
    // $licence_no = $_POST['licence_no'];

    $status = $_POST['status'];
    $staff = $_POST['staff'];
    $permission_no = $_POST['permission_no'];

    if(mysqli_query($conn, "update permission set permission_status = '$status', staff_id = '$staff' where permission_number = '$permission_no'"))
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