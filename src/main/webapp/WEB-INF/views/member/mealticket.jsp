<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>DOTO:mealticket</title>
  <meta content="" name="description">
  <meta content="" name="keywords">
  
  <script src="https://js.tosspayments.com/v1/payment"></script>
  <!-- Jquery -->
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
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

  <!-- =======================================================
  * Template Name: Eterna - v4.10.0
  * Template URL: https://bootstrapmade.com/eterna-free-multipurpose-bootstrap-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>
<sec:authentication property="principal" var="principal"/>
   <!-- ======= Header ======= -->
  <c:import url="/WEB-INF/views/common/top.jsp" />
  <!-- End Header -->

  <main id="main">

    <!-- ======= Breadcrumbs ======= -->
    <section id="breadcrumbs" class="breadcrumbs">
      <div class="container">

        <ol>
          <li><a href="/">Home</a></li>
          <li>????????????</li>
        </ol>
        <h2>????????????</h2>
      </div>
    </section><!-- End Breadcrumbs -->

    <!-- ======= Pricing Section ======= -->
    <section id="pricing" class="pricing">
      <div class="container">

        <div class="row no-gutters" style="margin-top:10px;">

          <div class="col-lg-4 box">
            <h3>1???</h3>
            <h4 class="price">??? 6000<span>????????? ??? ?????? 1??? ???</span></h4>
            <a href="#" class="buy-btn" id="6000">????????????</a>
          </div>

          <div class="col-lg-4 box">
            <h3>10???</h3>
            <h4 class="price">??? 60000<span>????????? ??? ?????? 10??? ???</span></h4>
            <a href="#" class="buy-btn" id="60000">????????????</a>
          </div>

          <div class="col-lg-4 box">
            <h3>30???</h3>
            <h4 class="price">??? 180000<span>????????? ??? ?????? 30??? ???</span></h4>
            <a href="#" class="buy-btn" id="180000">????????????</a>
          </div>

        </div>

      </div>
    </section><!-- End Pricing Section -->

  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
	<c:import url="/WEB-INF/views/common/footer.jsp" />
  <!-- End Footer -->


  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

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
  
  <script type="text/javascript">
  
  $('.buy-btn').click(function(e){
	  console.log("??????!!")
	  var username = "${principal.name}"
	  console.log(username)
    var meal = e.target.id;
    console.log(meal)
	  test(meal, username)
  })
  function test(meal, username){
	  console.log("test ??????")
	  let memberid = "${principal.memberId}";
      let successurl =  "http://localhost:8090/payments?memberid="+memberid;
      let failurl ="";
      let price = meal; // ??? css??? ??????????????????... ?????????..?????????????????????~~~ ??????????????????????????????????????????!~!~!!!~~~~!~!!~~!
          var clientKey = 'test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq'
          var tossPayments = TossPayments(clientKey) // ??????????????? ?????? ???????????????
          tossPayments.requestPayment('??????', { // ?????? ??????
			// ?????? ??????
			amount: price,
			orderId: 'QTIk82kxDPefXZC8MLFj0',
			orderName: "?????? ??????",
			customerName: username,
			successUrl: successurl,
			failUrl: "https://www.musinsa.com/app/",	
			flowMode: 'D',
			easyPay: '????????????'
			})
	}
  	
  </script>

</body>

</html>