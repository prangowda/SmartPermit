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
    ?>
    
  <div class="main-content">
    <div class="container-fluid content-top-gap">

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb my-breadcrumb">
            <li class="breadcrumb-item"><a href="index.php">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Search</li>
            </ol>
        </nav>

        <div class="cards__heading ">
            <div class="row">
                <div class="col-3">
                    <h3>Search</h3>
                </div>
                <div class="col-6">
                    <div class="search-box">
                        <form action="result.php" method="get">
                            <input class="search-input" placeholder="Search Here..." type="search" id="search" name="unid" required>
                            <button class="search-submit" value="" name="submit_search"><span class="fa fa-search"></span></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="card card_border p-lg-3">
          <div class="card-body p-0">
            <div class="row">
                <?php
                if(isset($_GET['submit_search']))
                {
                    $unid = $_GET['unid'];
                    $res1 = mysqli_query($conn, "select first_name,unique_id,status, last_name, gender, email, contact, date_of_birth, height, weight,blood_group,blood_pressure, pulse from user where unique_id = '$unid' and user_type='User'");
                    if(mysqli_num_rows($res1)>0)
                    {
                        $row1 = mysqli_fetch_assoc($res1);
                        ?>
                        
                        <div class="card-body">
                            <div class="cards__heading">
                                <h3>Search Results..</h3>
                            </div>
                            <div class="row">
                                <div class="col-lg-6 align-self pr-lg-4">
                                    <h5 class="my-3">Personal Information</h5>
                                    <label for="validationDefault01" class="form-label">Name : </label>
                                    <?php echo "<strong>".$row1['first_name']." ".$row1['last_name']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Unique ID : </label>
                                    <?php echo "<strong>".$row1['unique_id']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Gender : </label>
                                    <?php echo "<strong>".$row1['gender']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Email : </label>
                                    <?php echo "<strong>".$row1['email']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Contact Number : </label>
                                    <?php echo "<strong>".$row1['contact']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Date Of Birth : </label>
                                    <?php echo "<strong>".$row1['date_of_birth']."</strong>";?><br>
                                </div>
                                <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                                    <h5 class="my-3">Physical Information</h5>
                                    <label for="validationDefault01" class="form-label">Height : </label>
                                    <?php echo "<strong>".$row1['height']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Weight : </label>
                                    <?php echo "<strong>".$row1['weight']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Blood Group : </label>
                                    <?php echo "<strong>".$row1['blood_group']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Blood Pressure : </label>
                                    <?php echo "<strong>".$row1['blood_pressure']."</strong>";?><br>
                                    <label for="validationDefault02" class="input__label">Pulse : </label>
                                    <?php echo "<strong>".$row1['pulse']."</strong>";?><br>
                                    <a href="update-physical.php?unid=<?php echo $unid;?>" class="btn btn-primary float-right" >Update</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6 align-self pr-lg-4">
                                    <div class="cards__heading">
                                        <h3>Priscription</h3>
                                    </div>
                                    <a href="add-prescription.php?unid=<?php echo $unid;?>&medid=<?php echo rand(100000,999999);?>" class="btn btn-style btn-primary mr-2" >Write New</a>
                                    <a href="view-prescription.php?unid=<?php echo $unid;?>" class="btn btn-style border-btn" >View Old</a>
                                </div>
                                <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                                    <img src="image/prescription.jpg" alt="" class="img-fluid rounded" />
                                </div>
                            </div>
                        </div>
                    <?php
                    }
                    else
                    {?>
                        <div class="cards__heading">
                            <h3>User ID not found..</h3>
                        </div>
                    <?php
                    }
                }
                else
                {?>
                <div class="text-center col-12">
                    <img src="image/search.jpg" alt="" class="img-fluid rounded" />              
                </div>
                <?php   
                }
                ?>
            </div>
          </div>
        </div>

        
    </div>
</div>

<?php
    include 'footer.php';
  ?>
  