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

    if(isset($_POST['submit']))
    {
        $category = $_POST['category'];
        $description = $_POST['description'];

       
        if(mysqli_query($conn, "insert into category(category, description, status)values('$category', '$description', true)"))
        {
            echo "<script>alert('Category added successfully..!');location.href='add-category.php';</script>";
        }
        else
        {
            echo "<script>alert('Unable to add category..!');</script>";
        }
    }
 

    ?>
    
  <div class="main-content">
    <div class="container-fluid content-top-gap">

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb my-breadcrumb">
            <li class="breadcrumb-item"><a href="index.php">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Add Category</li>
            </ol>
        </nav>
        
        <div class="cards__heading">
          <h3>License Category</h3>
        </div>

        <div class="card card_border p-lg-5 p-3 mb-4">
          <div class="card-body py-3 p-0">
            <div class="row">
              <div class="col-lg-6 align-self pr-lg-4">

            <form class="row g-3" method="post" enctype="multipart/form-data">
              
              <div class="col-md-12">
                <label for="validationDefault01" class="form-label">Category</label>
                <input type="text" class="form-control input-style" name="category" id="validationDefault01" required>
              </div>

              <div class="form-group col-md-12 mt-3">
                <label for="validationDefault02" class="input__label">Description</label>
                <textarea class="form-control input-style" name="description" id="validationDefault02" required></textarea>
              </div>

              <div class="col-12">
                <button class="btn btn-primary" type="submit" name="submit">Submit form</button>
              </div>
            </form>
            </div>
              <div class="col-lg-6 pl-lg-4 mt-lg-0 mt-4">
                <img src="image/category.jpg" alt="" class="img-fluid rounded" height="300" width="300" />
              </div>
            </div>
          </div>
        </div>

        
    </div>
</div>

<?php
    include 'footer.php';
  ?>
  