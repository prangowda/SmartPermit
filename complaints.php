<?php

include 'connection.php';
$response=array();

$uid = $_POST['userid'];

$qry = "select complaints.complaint_type, complaints.complaint_number, complaints.complaint, complaints.description, complaints.name, complaints.address, complaints.contact_number, complaints.city, complaints.pincode, complaints.complaint_status, complaints.date from complaints where complaints.user_id = '$uid' order by complaints.id desc";
$res = mysqli_query($conn, $qry);
if(mysqli_num_rows($res)>0)
{
    while($rows = mysqli_fetch_assoc($res))
    {
        $send["number"] = $rows['complaint_number'];
        $send["type"] = $rows['complaint_type'];
        $send["complaint"] = $rows['complaint'];
        $send["description"] = $rows['description'];
        $send["name"] = $rows['name'];
        $send["address"] = $rows['address'];
        $send["phone"] = $rows['contact_number'];
        $send["city"] = $rows['city'];
        $send["pincode"] = $rows['pincode'];
        $send["status"] = $rows['complaint_status'];  
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