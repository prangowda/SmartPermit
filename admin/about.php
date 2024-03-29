<?php
    session_start();
    if(!isset($_SESSION['login']))
    {
        header("Location: ../index.php");
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
            <li class="breadcrumb-item active" aria-current="page">About Us</li>
            </ol>
        </nav>

        <div class="cards__heading">
          <h3>About Us</h3>
        </div>
        
        <div class="card card_border p-lg-5 p-3 mb-4">
          <div class="card-body py-3 p-0">
            <div class="row">
              <div class="col-lg-6 align-self pr-lg-4">
                <h3 class="block__title mb-lg-4">About Content Block</h3>
                <p class="mb-3">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Accusa ntium corrupti
                  neque sunt labore veritatis. </p>
                <p class="mb-lg-5 mb-3">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Accusa ntium corrupti
                  neque sunt
                  praesentium aut, labore veritatis. Eaque, similique aspernatur. Perferendis doloremque ut
                  praesentium vel voluptatum quasi dolor explicabo nobis ex?</p>
              </div>
              <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                <img src="image/about.jpg" alt="" class="img-fluid rounded" />
              </div>
            </div>
          </div>
        </div>
    </div>
</div>

<?php
    include 'footer.php';
  ?>
  