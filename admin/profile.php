<?php
    session_start();
    include '../connection.php';

    if(!isset($_SESSION['login']))
    {
        header("Location: login.php");
    }
    
    include 'link.php';
    include 'sidebar.php';
    include 'header.php';

    $res = mysqli_query($conn, "select * from admin where status = true and id = '$_SESSION[id]'");
    $row = mysqli_fetch_assoc($res);
    ?>
    
  <div class="main-content">
    <div class="container-fluid content-top-gap">

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb my-breadcrumb">
            <li class="breadcrumb-item"><a href="index.php">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Profile</li>
            </ol>
        </nav>
        
        <div class="card card_border py-2 mb-4">
            <div class="cards__heading">
                <h3>Admin Profile <span></span></h3>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-lg-6 align-self ">
                <div class="pl-4 mt-2">
                <label for="validationDefault01" class="form-label">Name : <?php echo $row['name'];?></label>
            </div>

            <div class="pl-4 mt-2">
                <label for="validationDefault01" class="form-label">User Name : <?php echo $row['username'];?></label>
            </div>

            <div class="pl-4 mt-2">
                <label for="validationDefault01" class="form-label">Email : <?php echo $row['email'];?></label>
            </div>

            <div class="pl-4 mt-2">
                <label for="validationDefault01" class="form-label">Contact : <?php echo $row['contact'];?></label>
            </div>

            <div class="pl-4 mt-2">
                <label for="validationDefault01" class="form-label">Status : <?php if($row['status']){echo 'Active';}else{echo 'In-Active';}?></label>
            </div>

            <div class="pl-4 mt-2">
                <label for="validationDefault01" class="form-label">Date Created : <?php echo $row['date'];?></label>
            </div>
                </div>
            <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                <img src="image/profile.jpg" alt="" class="img-fluid rounded" height="350" width="350" />
              </div>
              </div>
            </div>
        </div>
    </div>
</div>

<?php
    include 'footer.php';
  ?>
  