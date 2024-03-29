<?php

include 'connection.php';
$response=array();

$uid = 9;
// $uid = $_POST['userid'];

$qry = "select * from license where user_id = '$uid' order by id desc";
$res = mysqli_query($conn, $qry);
if(mysqli_num_rows($res)>0)
{
    while($rows = mysqli_fetch_assoc($res))
    {
        $send["lnumber"] = $rows['license_number'];
        $send["type"] = $rows['license_type'];
        $send["name"] = $rows['name'];
        $send["address"] = $rows['address'];
        $send["phone"] = $rows['contact_number'];
        $send["expdate"] = $rows['expire_date'];
        $send["tradeAddress"] = $rows['trade_address'];
        $send["status"] = $rows['status'];  
        $send["date"] = $rows['date'];  

        array_push($response,$send);
    }
}
else
{
    $response=null;
}  
echo (json_encode($response));
?>