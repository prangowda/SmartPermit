<?php

include 'connection.php';
$response=array();

$qry = "select * from permission where permission_status = 'Pending' order by id desc";
$res = mysqli_query($conn, $qry);
if(mysqli_num_rows($res)>0)
{
    while($rows = mysqli_fetch_assoc($res))
    {
        $send["pnumber"] = $rows['permission_number'];
        $send["type"] = $rows['permission_type'];
        $send["description"] = $rows['description'];
        $send["name"] = $rows['name'];
        $send["address"] = $rows['address'];
        $send["phone"] = $rows['phone'];
        $send["validfrom"] = $rows['valid_from'];
        $send["validto"] = $rows['valid_to'];
        $send["status"] = $rows['permission_status'];  
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