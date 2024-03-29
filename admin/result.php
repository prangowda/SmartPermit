<?php
    session_start();
    include '../connection.php';
    if(!isset($_SESSION['login']))
    {
        header("Location: login.php");
    }

    if(isset($_GET['del']))
    {
        if(mysqli_query($conn,"delete from category where id = '".$_GET['id']."'"))
        {
            echo "<script>alert('Category deleted successfully..!');location.href='category.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to delete category..!')</script>";
        }
    }

    if(isset($_GET['approve'])){
        $lno = $_GET['lno'];
        if(mysqli_query($conn,"update license set status = 'Approved' where license_number = '$lno'"))
        {
            echo "<script>alert('Status updated successfully..!');location.href='search.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to update status..!');location.href='search.php';</script>";
        }
        
    }
    if(isset($_GET['reject'])){
        $lno = $_GET['lno'];
        if(mysqli_query($conn,"update license set status = 'Rejected' where license_number = '$lno'"))
        {
            echo "<script>alert('Status updated successfully..!');location.href='search.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to update status..!');location.href='search.php';</script>";
        }
    }

    if(isset($_GET['approve1'])){
        $lno = $_GET['lno'];
        if(mysqli_query($conn,"update renew set status = 'Approved' where license_number = '$lno'"))
        {
            echo "<script>alert('Status updated successfully..!');location.href='search.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to update status..!');location.href='search.php';</script>";
        }
        
    }
    if(isset($_GET['reject1'])){
        $lno = $_GET['lno'];
        if(mysqli_query($conn,"update renew set status = 'Rejected' where license_number = '$lno'"))
        {
            echo "<script>alert('Status updated successfully..!');location.href='search.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to update status..!');location.href='search.php';</script>";
        }
    }


    include 'link.php';
    include 'sidebar.php';
    include 'header.php';
    ?>
    
  <div class="main-content">
    <div class="container-fluid content-top-gap">

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb my-breadcrumb">
            <li class="breadcrumb-item"><a href="index.php">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Search Result</li>
            </ol>
        </nav>
        <div class="cards__heading">
        <div></div>
          <h3>Search Result</h3>
        </div>
        <div class="card card_border p-lg-5 p-3 mb-4">
          <div class="card-body py-3 p-0">
            <div class="row">
            <div class="col-lg-6 align-self pr-lg-4" id="printableArea">
            <?php
                $sno = $_GET['unid'];
                
                $res1 = mysqli_query($conn, "select * from renew where license_number = '$sno' order by id desc");
                $res2 = mysqli_query($conn, "select * from license where license_number = '$sno' order by id desc");
                $res3 = mysqli_query($conn, "select * from permission where permission_number = '$sno' order by id desc");
                $res4 = mysqli_query($conn, "select * from complaints where complaint_number = '$sno' order by id desc");
                if(mysqli_num_rows($res1)>0){
                    $row1 = mysqli_fetch_assoc($res1);
                    ?>
                        License Type : <?php echo $row1['license_type'];?></br>
                        License No : <strong><?php echo $row1['license_number'];?></strong></br>
                        Name : <?php echo $row1['name'];?></br>
                        Address : <?php echo $row1['address'];?></br>
                        Contact Number : <?php echo $row1['contact_number'];?></br>
                        Expire Date : <?php echo $row1['expire_date'];?></br>
                        Trade Address : <?php echo $row1['trade_address'];?></br>
                        License Status : <?php echo $row1['status'];?></br>
                        Date : <?php echo $row1['date'];?></br>

                        <form method="get" action="#">
                            <input type="hidden" name="unid" value="<?php echo $sno;?>">
                            <input type="hidden" name="lno" value="<?php echo $row1['license_number'];?>">
                            <button class="btn btn-success mt-4" type="submit" name="approve1" >Approve</button>
                            <button class="btn btn-danger mt-4" type="submit" name="reject1" >Reject</button>
                        </form>
                    <?php
                }
                else if(mysqli_num_rows($res2)>0){
                    $row2 = mysqli_fetch_assoc($res2);
                    ?>
                        License Type : <?php echo $row2['license_type'];?></br>
                        License No : <strong><?php echo $row2['license_number'];?></strong></br>
                        Name : <?php echo $row2['name'];?></br>
                        Address : <?php echo $row2['address'];?></br>
                        Contact Number : <?php echo $row2['contact_number'];?></br>
                        Expire Date : <?php echo $row2['expire_date'];?></br>
                        Trade Address : <?php echo $row2['trade_address'];?></br>
                        Date : <?php echo $row2['date'];?></br>
                        License Status : <?php echo $row2['status'];?></br>

                        <form method="get" action="#">
                            <input type="hidden" name="unid" value="<?php echo $sno;?>">
                            <input type="hidden" name="lno" value="<?php echo $row2['license_number'];?>">
                            <button class="btn btn-success mt-4" type="submit" name="approve" >Approve</button>
                            <button class="btn btn-danger mt-4" type="submit" name="reject" >Reject</button>
                        </form>
                    <?php
                }
                else if(mysqli_num_rows($res3)>0){
                    $row3 = mysqli_fetch_assoc($res3);
                    ?>
                        Permission Type : <?php echo $row3['permission_type'];?></br>
                        Permission No : <strong><?php echo $row3['permission_number'];?></strong></br>
                        Description : <?php echo $row3['description'];?></br>
                        Name : <?php echo $row3['name'];?></br>
                        Address : <?php echo $row3['address'];?></br>
                        Contact Number : <?php echo $row3['phone'];?></br>
                        Valid From : <?php echo $row3['valid_from'];?></br>
                        Valid To : <?php echo $row3['valid_to'];?></br>
                        Permission Status : <?php echo $row3['permission_status'];?></br>
                        Date : <?php echo $row3['date'];?></br>

                        <button class="btn btn-primary mt-4" type="submit" name="submit" onclick="printDiv('printableArea')">Print</button>
                    <?php
                }
                else if(mysqli_num_rows($res4)>0){
                    $row4 = mysqli_fetch_assoc($res4);
                    ?>
                        Complaint Type : <?php echo $row4['complaint_type'];?></br>
                        Complaint No : <strong><?php echo $row4['complaint_number'];?></strong></br>
                        Complaint : <?php echo $row4['complaint'];?></br>
                        Description : <?php echo $row4['description'];?></br>
                        Complaint : <?php echo $row4['complaint'];?></br>
                        Name : <?php echo $row4['name'];?></br>
                        Address : <?php echo $row4['address'];?></br>
                        Contact Number : <?php echo $row4['contact_number'];?></br>
                        City : <?php echo $row4['city'];?></br>
                        Pincode : <?php echo $row4['pincode'];?></br>
                        Complaint Status : <?php echo $row4['complaint_status'];?></br>
                        Date : <?php echo $row4['date'];?></br>

                        <button class="btn btn-primary mt-4" type="submit" name="submit" onclick="printDiv('printableArea')">Print</button>
                    <?php
                }
                else{
                    echo "<script>alert('Invalid number you entered..!');location.href='search.php';</script>";
                }
            ?>
            </div>
            <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                <img src="image/category.jpg" alt="" class="img-fluid rounded" height="300" width="300" />
              </div>
            </div>
          </div>
        </div>
    </div>
</div>
<script>
function printDiv(divName) {
     var printContents = document.getElementById(divName).innerHTML;
     var originalContents = document.body.innerHTML;

     document.body.innerHTML = printContents;

     window.print();

     document.body.innerHTML = originalContents;
}
</script>
<?php
    include 'footer.php';
  ?>