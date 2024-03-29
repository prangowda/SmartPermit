<!-- header-starts -->
<div class="header sticky-header">

<!-- notification menu start -->
<div class="menu-right">
  <div class="navbar user-panel-top">
    <div class="user-dropdown-details d-flex">
      <div class="profile_details_left">
        <ul class="nofitications-dropdown">
          <li>
            <a href="index.php"><i class="fa fa-home"></i></span></a>
          </li>
          <li>
            <a href="logout.php"><i class="fa fa-power-off"></i></span></a>
          </li>
          
        </ul>
      </div>
      <div class="profile_details">
        <ul>
          <li class="dropdown profile_details_drop">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" id="dropdownMenu3" aria-haspopup="true"
              aria-expanded="false">
              <div class="profile_img">
                <img src="image/admin_image.jpg" class="rounded-circle" alt="" />
                <div class="user-active">
                  <span></span>
                </div>
              </div>
            </a>
            <ul class="dropdown-menu drp-mnu" aria-labelledby="dropdownMenu3">
              <li class="user-info">
                <h5 class="user-name"><?php echo $_SESSION['name']; ?></h5>
                <span class="status ml-2">Online</span>
              </li>
              <li> <a href="profile.php"><i class="lnr lnr-user"></i>My Profile</a> </li>
              <li> <a href="change_password.php"><i class="lnr lnr-cog"></i>Setting</a> </li>
              <li class="logout"> <a href="logout.php"><i class="fa fa-power-off"></i> Logout</a> </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
<!--notification menu end -->
</div>
<!-- //header-ends -->