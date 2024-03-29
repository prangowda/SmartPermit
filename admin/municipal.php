<?php
    session_start();
    include '../connection.php';
    if(!isset($_SESSION['login']))
    {
        header("Location: login.php");
    }

    if(isset($_GET['del']))
    {
        if(mysqli_query($conn,"delete from user where id = '".$_GET['id']."'"))
        {
            echo "<script>alert('Municipal deleted successfully..!');location.href='municipal.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to delete municipal..!')</script>";
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
            <li class="breadcrumb-item active" aria-current="page">Municipal</li>
            </ol>
        </nav>
        <div class="cards__heading">
        <div></div>
          <h3>Manage Municipal <a href="add-municipal.php"><span class="float-right text-primary"><i class="fa fa-plus"></i> Add Municipal</span></a></h3>
        </div>
        <div class="card card_border p-lg-5 p-3 mb-4">
          <div class="card-body py-3 p-0">
            <div class="row">
            <table cellpadding="0" cellspacing="0" border="0" class="datatable-1 table table-bordered table-striped	 display table-hover" width="100%">
                                <thead>
                                    <tr>
                                        <th>#</th>
										<th>Name</th>
										<th>Address</th>
										<th>Contact</th>
										<th>Email</th>
										<th>Date</th>
										<th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>

                                <?php 
                                    $query=mysqli_query($conn,"select * from user where type = 'Municipal' order by id desc");
                                    $cnt=1;
                                    while($row=mysqli_fetch_array($query))
                                    {?>									
                                        <tr>
                                            <td><?php echo htmlentities($cnt);?></td>
                                            <td><?php echo htmlentities($row['name']);?></td>
                                            <td><?php echo htmlentities($row['address']);?></td>
                                            <td><?php echo htmlentities($row['contact']);?></td>
                                            <td><?php echo htmlentities($row['email']);?></td>
                                            <td><?php echo htmlentities($row['date']);?></td>
                                            <td> <?php if($row['status']){echo 'Active';}else{echo 'In-Active';}?></td>
                                            <td>
                                                <a href="municipal.php?id=<?php echo $row['id']?>&del=delete" onClick="return confirm('Are you sure you want to delete?')"><i class="fa fa-trash"></i></a></td>
                                        </tr>
                                        <?php $cnt=$cnt+1; 
                                    } ?>
                                </tbody>			
                            </table>
            </div>
          </div>
        </div>
    </div>
</div>

<?php
    include 'footer.php';
  ?>