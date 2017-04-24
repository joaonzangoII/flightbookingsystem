<!DOCTYPE html>
<html lang="{{ config('app.locale') }}">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>  SmallGate Flhight Booking System</title>
        <!-- Fonts -->
        {{-- <link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" type="text/css"> --}}
        <!-- Styles -->
        <style>
            html, body {
                background-color: #fff;
                color: #636b6f;
                font-family: 'Raleway', sans-serif;
                font-weight: 100;
                height: 100vh;
                margin: 0;
            }

            .full-height {
                height: 100vh;
            }

            .flex-center {
                align-items: center;
                display: flex;
                justify-content: center;
            }

            .position-ref {
                position: relative;
            }

            .top-right {
                position: absolute;
                right: 10px;
                top: 18px;
            }

            .content {
                text-align: center;
            }

            .title {
                font-size: 84px;
                color: #FF0000;
            }

            .links > a {
                color: #00FF00;
                padding: 0 25px;
                font-size: 12px;
                font-weight: 600;
                letter-spacing: .1rem;
                text-decoration: none;
                text-transform: uppercase;
            }

            #middle-links a{
              font-size: 18px;
              color: #0000FF;
            }

            .m-b-md {
                margin-bottom: 30px;
            }

            #background{
              background-image:url('https://www.frankfurt-airport.com/content/dam/airport/IK_Airlines/SA/SA-banner.png/_jcr_content/renditions/cq5dam.web.1280.1280.png./cq5dam.web.1280.1280.png');
              background-repeat:no-repeat;
              -webkit-background-size:cover;
              -moz-background-size:cover;
              -o-background-size:cover;
              background-size:cover;
              background-position:center;
            }
        </style>
    </head>
    <body id="background">
        <div class="flex-center position-ref full-height">
            @if (Route::has('login'))
                <div class="top-right links">
                    @if (Auth::check())
                        <a href="{{ url('/home') }}">Home</a>
                    @else
                        <a href="{{ url('/login') }}">Login</a>
                    @endif
                </div>
            @endif

            <div class="content">
                <div class="title m-b-md">
                    SmallGate Flhight Booking System
                </div>

                <div id="middle-links" class="links">
                    @if (Auth::check())
                        <a href="{{ url('/home') }}">Home</a>
                    @else
                        <a href="{{ url('/login') }}">Login</a>
                    @endif
                    {{-- <a href="https://laracasts.com">Laracasts</a>
                    <a href="https://laravel-news.com">News</a>
                    <a href="https://forge.laravel.com">Forge</a>
                    <a href="https://github.com/laravel/laravel">GitHub</a> --}}
                </div>
            </div>
        </div>
    </body>
</html>
