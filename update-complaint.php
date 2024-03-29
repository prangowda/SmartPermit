<?php
    include 'connection.php';

    // $status = $_POST['status'];
    // $staff = $_POST['staff'];
    // $licence_no = $_POST['licence_no'];

    $status = $_POST['status'];
    $staff = $_POST['staff'];
    $complaint_no = $_POST['complaint_no'];

    if(mysqli_query($conn, "update complaints set complaint_status = '$status', staff_id = '$staff' where complaint_number = '$complaint_no'"))
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