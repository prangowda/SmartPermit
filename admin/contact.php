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
            <li class="breadcrumb-item active" aria-current="page">Contact Us</li>
            </ol>
        </nav>

        <div class="cards__heading">
          <h3>Contact Us</h3>
        </div>
        
        <div class="w3l-about1 card card_border p-lg-5 p-3 mb-4">
      <div class="card-body py-3 p-0">
        <h3 class="block__title mb-lg-4">Get in touch with us.</h3>
        <div class="row cwp23-content">
          <div class="col-md-6 cwp23-text">
            <div class="row cwp23-text-cols">
              <div class="col-md-12 column mt-4">
                <span class="fa fa-map-marker" aria-hidden="true"></span>
                <a href="#">Address</a>
                <p>Accolade Tech Solutions, Premium Enclave, 3rd Floor, Shop No.22 and 23, Light house hill Road, Hampankatta Mangalore- 575001. </p>
              </div>
              <div class="col-md-12 column mt-4">
                <span class="fa fa-phone" aria-hidden="true"></span>
                <a href="#">Phone</a>
                <p>9856985698</p>
              </div>
              <div class="col-md-12 column mt-4">
                <span class="fa fa-envelope" aria-hidden="true"></span>
                <a href="#url">Email</a>
                <p>degital.municipal@gmail.com</p>
              </div>
            </div>
          </div>
          <div class="col-md-6 mt-md-0 mt-5 cwp23-img">
            <img src="image/contact.jpg" class="img-fluid rounded" alt="">
          </div>
        </div>
      </div>
    </div>
    </div>
</div>

<?php
    include 'footer.php';
  ?>
  