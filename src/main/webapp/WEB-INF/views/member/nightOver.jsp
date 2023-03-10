<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>DOTO:mealticket</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="resources/assets/img/favicon.png" rel="icon">
  <link href="resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="resources/assets/vendor/animate.css/animate.min.css" rel="stylesheet">
  <link href="resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="resources/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="resources/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="resources/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="resources/assets/css/style.css" rel="stylesheet">
  
  <!-- Jquery -->
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>


  <link href="resources/assets/css/nightOver.css" rel="stylesheet">

  <!-- =======================================================
  * Template Name: Eterna - v4.10.0
  * Template URL: https://bootstrapmade.com/eterna-free-multipurpose-bootstrap-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>

  <!-- ======= Header ======= -->
  <c:import url="/WEB-INF/views/common/top.jsp" />
  <!-- End Header -->
  
  <main id="main">
  <div id="container">
   <section id="breadcrumbs" class="breadcrumbs">
      <div class="container">

        <ol>
          <li><a href="/">Home</a></li>
          <li>????????????</li>
        </ol>
        <h2>????????????</h2>

      </div>
    </section>
  
  	<div id="box">
      <form action="" method="POST" enctype="multipart/form-data">
        <div id="date">
          <label class="label" for="start">?????? ??????</label>
          <label class="label" for="end">?????? ??????</label>
          <label class="label" for="end">?????? ??????</label>
          <label class="label" for="end">?????? ??????</label>
        </div>
        <div id="select">
        <input class="form-select1 datepick" type="date" id="start" name="startDate" value="2000-10-04">
        <input class="form-select1 datepick" type="date" id="end" name="endDate" value="2000-10-04">
        <select class="form-select1" aria-label="Default select example" name="status">
          <option value="11" selected>??????</option>
          <option value="12">????????????</option>
        </select>
        <label class="form-select1" for="file" style="background-color:#FFC107; border-radius: 9px; cursor: pointer; justify-content: center;">?????? ?????????</label>
        <input class="file" id="file" name="file" type="file" style="display:none;">
        </div>
        <div id="reason">
          <div class="form-floating">
          <textarea class="form-control" name="sleepOverReason" placeholder="?????? ????????? ???????????????." id="floatingTextarea2" style="height: 100px"></textarea>
          <label for="floatingTextarea2">?????? ????????? ???????????????.</label>
        </div>
        </div>
        <button type="submit" class="btn btn-warning">????????????</button>
      </form>
      </div>
  </div>
  
  </main>
  <!-- ======= Footer ======= -->
	<c:import url="/WEB-INF/views/common/footer.jsp" />
  <!-- End Footer -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

</body>

<!-- Vendor JS Files -->
  <script src="resources/assets/vendor/purecounter/purecounter_vanilla.js"></script>
  <script src="resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="resources/assets/vendor/glightbox/js/glightbox.min.js"></script>
  <script src="resources/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
  <script src="resources/assets/vendor/swiper/swiper-bundle.min.js"></script>
  <script src="resources/assets/vendor/waypoints/noframework.waypoints.js"></script>
  <script src="resources/assets/vendor/php-email-form/validate.js"></script>

  <!-- Template Main JS File -->
  <script src="resources/assets/js/main.js"></script>
  
  <!-- bootstrap5 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
  
<script>
  $(document).ready(function(){
    setDate();
  })
  
  function setDate(){
    let today = new Date();
    // ?????? getFullYear()
    let year = today.getFullYear(); 
    // ??? getMonth() (0~11??? 1?????? 0?????? ???????????? ????????? + 1??? ???????????? ????????? ?????? ?????? ??? ??????.)
    let month = today.getMonth() + 1
    // ??? getDate()
    let date = today.getDate(); // ???

    if(month<10){
      month = "0" + month;
    }
    if(date<10){
      date = "0" + date;
    }

    let todayFormat = year + "-" + month + "-" + date;
    console.log(todayFormat);
    $(".datepick").val(todayFormat);
  }
</script>

</html>