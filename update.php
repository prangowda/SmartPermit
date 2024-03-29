<?php
    include 'connection.php';

    $uid = $_POST['uid'];
    $password = $_POST['password'];
    $oldpassword = $_POST['oldpassword'];

    $res = mysqli_query($conn, "select password from user where id = '$uid' and status = true");
    if(mysqli_num_rows($res)>0)
    {
        $row = mysqli_fetch_assoc($res);
        if($row['password']==$oldpassword)
        {
            if(mysqli_query($conn, "update user set password = '$password' where id = '$uid' and status = true "))
            {
                $responce['success']=true;
                $responce['message']="Your password updated successfully..!"; 
            }
            else
            {
                $responce['success']=false;
                $responce['message']="Oops, Unable to update your password..!"; 
            }
        }
        else
        {
            $responce['success']=false;
            $responce['message']="You have entered an invalid current password..!";
        }
    }
    else
    {
        $responce['success']=false;
        $responce['message']="You have sent an invalid request..!";
    }

    echo json_encode($responce);
?>