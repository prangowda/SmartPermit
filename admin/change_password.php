<?php
    session_start();
    include '../connection.php';

    if(!isset($_SESSION['login']))
    {
        header("Location: login.php");
    }
    else if(isset($_POST['change']))
    {
        $cpass = $_POST['cpass'];
        $npass = $_POST['npass'];
        $opass = $_POST['opass'];
        $uid = $_SESSION['id'];

        if($cpass == $npass)
        {
            $res = mysqli_query($conn, "select password from admin where id = '$uid'");
            if(mysqli_num_rows($res)>0)
            {
                $row = mysqli_fetch_assoc($res);
                $oldpass = $row['password'];
                if($opass == $oldpass)
                {
                    if(mysqli_query($conn, "update admin set password = '$npass' where id = '$uid' "))
                    {
                        echo "<script>alert('Password updated successfully..!');</script>";
                    }
                    else
                    {
                        echo "<script>alert('Unable to update your password..!');</script>";
                    }
                }
                else
                {
                    echo "<script>alert('Invalid current password..!');</script>";
                }
            }
            else
            {
                echo "<script>alert('Unable to update your password..!');</script>";
            }
        }
        else
        {
            echo "<script>alert('Passwords dont match..!');</script>";
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
            <li class="breadcrumb-item active" aria-current="page">Settings</li>
            </ol>
        </nav>

        <div class="cards__heading">
          <h3>Change Password</h3>
        </div>

        <div class="card card_border p-lg-5 p-3 mb-4">
          <div class="card-body py-3 p-0">
            <div class="row">
              <div class="col-lg-6 align-self pr-lg-4">
                <form action="#" method="post">
                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-4 col-form-label input__label">Current Password</label>
                        <div class="col-sm-8">
                            <input type="password" name="opass" class="form-control input-style" id="inputPassword3"
                                placeholder="Current Password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required>
                        </div>
                    </div>
                    <div class="form-group row">        
                        <label for="inputPassword3" class="col-sm-4 col-form-label input__label">New Password</label>
                        <div class="col-sm-8">
                            <input type="password" name="npass" class="form-control input-style" id="inputPassword3"
                                placeholder="New Password"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-4 col-form-label input__label">Confirm Password</label>
                        <div class="col-sm-8">
                            <input type="password" name="cpass" class="form-control input-style" id="inputPassword3"
                                placeholder="Confirm Password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-4">
                            <button type="submit" class="btn btn-primary btn-style" name="change">Submit</button>
                        </div>
                    </div>
                </form>
                
              </div>
              <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                <img src="image/password.jpg" alt="" class="img-fluid rounded" height="400" width="400" />
              </div>
            </div>
          </div>
        </div>

        
    </div>
</div>

<?php
    include 'footer.php';
  ?>
  