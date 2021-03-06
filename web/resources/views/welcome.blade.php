<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>{{ config('app.name', 'Laravel') }}</title>
    <!-- Bootstrap Core CSS -->
    <link href="{{asset('client/vendor/bootstrap/css/bootstrap.min.css')}}" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="{{asset('client/vendor/font-awesome/css/font-awesome.min.css')}}" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <!-- Theme CSS -->
    <link href="{{asset('client/css/grayscale.min.css')}}" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style media="screen">
    .intro {
        background: url(/client/img/tablemountain.png) bottom center no-repeat #000;
        background-repeat:no-repeat;
        -webkit-background-size:cover;
        -moz-background-size:cover;
        -o-background-size:cover;
        background-size:cover;
        background-position:center;
      }

      .download-section {
          width: 100%;
          padding: 50px 0;
          color: #fff;
          background: url(/client/img/playstore.jpg) center center no-repeat #000;
          -webkit-background-size: cover;
          -moz-background-size: cover;
          background-size: cover;
          -o-background-size: cover;
      }
    </style>
</head>
<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

    <!-- Navigation -->
    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                Menu <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand page-scroll" href="#page-top">
                <i class="fa fa-play-circle"></i>
                <span class="light">SmallGate</span> Flight Booking System
            </a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
          <ul class="nav navbar-nav">
            <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
            <li class="hidden">
                <a href="#page-top"></a>
            </li>
            <li>
                <a class="page-scroll" href="#about">About</a>
            </li>
            <li>
                <a class="page-scroll" href="#download">Download</a>
            </li>
            <li>
                <a class="page-scroll" href="#contact">Contact</a>
            </li>
            @if (Auth::check())
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                      {{ Auth::user()->name }} <span class="caret"></span>
                  </a>

                  <ul class="dropdown-menu" role="menu">
                      <li><a href="{{ route('backoffice.home') }}">Dashboard</a></li>
                      <li>
                          <a href="{{ route('logout') }}"
                              onclick="event.preventDefault();
                                       document.getElementById('logout-form').submit();">
                              Logout
                          </a>

                          <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                              {{ csrf_field() }}
                          </form>
                      </li>
                  </ul>
              </li>
            @else
                <li><a href="{{ url('/login') }}">Login</a></li>
            @endif
          </ul>
        </div>
        <!-- /.navbar-collapse -->
      </div>
      <!-- /.container -->
    </nav>
    <!-- Intro Header -->
    <header class="intro">
      <div class="intro-body">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <h1 class="brand-heading">{{ config('app.name', 'Laravel')}}</h1>
                    <p class="intro-text">The best way to book your flights.
                        <br>Created by team TUT.</p>
                    <a href="#about" class="btn btn-circle page-scroll">
                        <i class="fa fa-angle-double-down animated"></i>
                    </a>
                </div>
            </div>
        </div>
      </div>
    </header>

    <!-- About Section -->
    <section id="about" class="container content-section text-center">
      <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2>About {{ config('app.name', 'Laravel')}}</h2>
            <p>The Small Gate Flight Booking System project is an implementation
              of a flight ticketing booking system, that will help the customers
              to search for availability and prices of flight tickets,
              along with the different packages available for booking &
              reservations.</p>
        </div>
      </div>
    </section>
    <!-- Download Section -->
    <section id="download" class="content-section text-center">
      <div class="download-section">
        <div class="container">
            <div class="col-lg-8 col-lg-offset-2">
                <h2>Download Android Application</h2>
                <p>You can download The Android Application for free on the following link.</p>
                <a href="{{asset('apk/system.apk')}}"
                   class="btn btn-default btn-lg">Download</a>
            </div>
        </div>
      </div>
    </section>
    <!-- Contact Section -->
    <section id="contact" class="container content-section text-center">
      <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2>Contact {{ config('app.name', 'Laravel')}}</h2>
            <p>Feel free to email us to provide some feedback on our templates,
              give us suggestions for new templates and themes, or to just say hello!</p>
            <p><a href="mailto:feedback@startbootstrap.com">feedback@startbootstrap.com</a>  </p>
            <ul class="list-inline banner-social-buttons">
              <li>
                  <a href="https://github.com/joaonzangoII/flightbookingsystem" class="btn btn-default btn-lg">
                    <i class="fa fa-github fa-fw"></i>
                    <span class="network-name">Github</span>
                  </a>
              </li>
            </ul>
        </div>
      </div>
    </section>
    <!-- Map Section -->
    <div id="map"></div>
    <!-- Footer -->
    <footer>
      <div class="container text-center">
          <p>Copyright &copy; {{ config('app.name', 'Laravel') . " ".  date("Y") }}</p>
      </div>
    </footer>
    <!-- jQuery -->
    <script src="{{asset('client/vendor/jquery/jquery.js')}}"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="{{asset('client/vendor/bootstrap/js/bootstrap.min.js')}}"></script>
    <!-- Plugin JavaScript -->
    <script src="{{asset('client/vendor/jquery.easing/jquery.easing.min.js')}}"></script>
    <!-- Google Maps API Key - Use your own API key to enable the map feature. More information on the Google Maps API can be found at https://developers.google.com/maps/ -->
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRngKslUGJTlibkQ3FkfTxj3Xss1UlZDA&sensor=false">
    </script>
    <!-- Theme JavaScript -->
    <script src="{{asset('client/js/grayscale.min.js')}}"></script>
</body>

</html>
