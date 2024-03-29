<!-- sidebar menu start -->
<div class="sidebar-menu sticky-sidebar-menu">

<!-- logo start -->
<div class="logo">
  <h1><a href="index.php">Prescription</a></h1>
</div>

<div class="logo-icon text-center">
  <a href="index.php" title="logo"><img src="assets/images/logo.png" alt="logo-icon"> </a>
</div>
<!-- //logo end -->

<div class="sidebar-menu-inner">

  <!-- sidebar nav start -->
  <ul class="nav nav-pills nav-stacked custom-nav">
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/index.php'){ echo 'active';}?>"><a href="index.php"><i class="fa fa-tachometer"></i><span> Dashboard</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/index.php'){ echo 'active';}?>"><a href="search.php"><i class="fa fa-search"></i><span> Search</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/index.php'){ echo 'active';}?>"><a href="category.php"><i class="fa fa-bars"></i><span> Category</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/index.php'){ echo 'active';}?>"><a href="municipal.php"><i class="fa fa-building"></i><span> Minicipal</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/index.php'){ echo 'active';}?>"><a href="police.php"><i class="fa fa-user-plus"></i><span> police</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/profile.php'){ echo 'active';}?>"><a href="profile.php"><i class="fa fa-user"></i><span> Profile</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/change_password.php'){ echo 'active';}?>"><a href="change_password.php"><i class="lnr lnr-cog"></i><span> Settings</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/about.php'){ echo 'active';}?>"><a href="about.php"><i class="fa fa-question-circle"></i> <span>About Us</span></a></li>
    <li class="<?php if($_SERVER['REQUEST_URI'] == '/prescription/starter/contact.php'){ echo 'active';}?>"><a href="contact.php"><i class="fa fa-phone"></i> <span>Contact Us</span></a></li>
    <li><a href="logout.php"><i class="fa fa-power-off"></i> <span>Logout</span></a></li>
  </ul>
  <!-- //sidebar nav end -->
  <!-- toggle button start -->
  <a class="toggle-btn">
    <i class="fa fa-angle-double-left menu-collapsed__left"><span>Collapse Sidebar</span></i>
    <i class="fa fa-angle-double-right menu-collapsed__right"></i>
  </a>
  <!-- //toggle button end -->
</div>
</div>
<!-- //sidebar menu end -->