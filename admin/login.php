
<?php 
    session_start();
    include '../connection.php';
    if(isset($_SESSION['login']))
    {
      header ("Location: index.php");
    } 
    else if(isset($_POST['submit']))
    {
      $username = $_POST['uname'];
      $password = $_POST['password'];
      
      $res = mysqli_query($conn, "select id, name from admin where username = '$username' and password = '$password' and status = true");
      if(mysqli_num_rows($res)>0){
        $row = mysqli_fetch_assoc($res);
        $_SESSION['login'] = true;
        $_SESSION['name'] = $row['name'];
        $_SESSION['id'] = $row['id'];
        header("Location: index.php");
      }
      else {
        echo "<script>alert('Invalid Credentials..');</script>";
      }
    }

    include 'link.php';
?>
<div class="row mt-5">
  <div class="col-lg-4"></div>
  <div class="col-lg-4 card shadow p-5 mt-5">
    <form action="#" method="post">
      <h2 class="text-center">Log in</h2>       
      <div class="form-group mt-3">
          <input type="text" class="form-control" placeholder="Username" required="required" name="uname">
      </div>
      <div class="form-group">
          <input type="password" class="form-control" placeholder="Password" required="required" name="password">
      </div>
      <div class="form-group">
          <button type="submit" class="btn btn-primary btn-block" name="submit">Log in</button>
      </div>       
    </form>
  </div>
  <div class="col-lg-4"></div>
</div>
<?php
    include 'footer.php';
?>

