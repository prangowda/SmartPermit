<?php

include 'connection.php';
$response=array();

$qry = "select title, description, createddate, image from article where status = true order by id desc";
$res = mysqli_query($conn, $qry);
if(mysqli_num_rows($res)>0)
{
    while($rows = mysqli_fetch_assoc($res))
    {
        $send["date"] = $rows['createddate'];
        $send["title"] = $rows['title'];
        $send["desc"] = $rows['description'];
        $send["image"] = $rows['image'];

        array_push($response,$send);
    }
}
else
{
    $response=null;
}  
echo (json_encode($response));
?>