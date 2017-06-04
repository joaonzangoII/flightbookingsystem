<header class="main-header">
     <!-- Logo -->
     <a href="{{route('backoffice.home')}}" class="logo">
       <!-- mini logo for sidebar mini 50x50 pixels -->
       <span class="logo-mini"><b>SG</b>FBS</span>
       <!-- logo for regular state and mobile devices -->
       <span class="logo-lg">SmallGate</span>
     </a>
     <!-- Header Navbar: style can be found in header.less -->
     <nav class="navbar navbar-static-top">
       <!-- Sidebar toggle button-->
       <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
         <span class="sr-only">Toggle navigation</span>
       </a>

       <div class="navbar-custom-menu">
         <ul class="nav navbar-nav">
           <!-- Messages: style can be found in dropdown.less-->
           <!-- Notifications: style can be found in dropdown.less -->
           <li class="dropdown notifications-menu">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">
               <i class="fa fa-bell-o"></i>
               <span class="label label-warning">10</span>
             </a>
             <ul class="dropdown-menu">
               <li class="header">You have 10 notifications</li>
               <li>
                 <!-- inner menu: contains the actual data -->
                 <ul class="menu">
                   <li>
                     <a href="#">
                       <i class="fa fa-users text-aqua"></i> 5 new members joined today
                     </a>
                   </li>
                   <li>
                     <a href="#">
                       <i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the
                       page and may cause design problems
                     </a>
                   </li>
                   <li>
                     <a href="#">
                       <i class="fa fa-users text-red"></i> 5 new members joined
                     </a>
                   </li>
                   <li>
                     <a href="#">
                       <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                     </a>
                   </li>
                   <li>
                     <a href="#">
                       <i class="fa fa-user text-red"></i> You changed your username
                     </a>
                   </li>
                 </ul>
               </li>
               <li class="footer"><a href="#">View all</a></li>
             </ul>
           </li>
           <!-- Tasks: style can be found in dropdown.less -->
           <!-- User Account: style can be found in dropdown.less -->
           {{-- {{dd(\Auth::guard(null)->user())}} --}}
           @if(Auth::check())
             <li class="dropdown user user-menu">
               <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                 <img src="/admin/img/user2-160x160.jpg" class="user-image" alt="User Image">
                 <span class="hidden-xs">{{Auth::user()->name}}</span>
               </a>
               <ul class="dropdown-menu">
                 <!-- User image -->
                 <li class="user-header">
                   <img src="/admin/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                   <p>
                     {{Auth::user()->name}}
                     <small>Member since {{Auth::user()->created_at->format('Y')}}</small>
                   </p>
                 </li>

                 <!-- Menu Footer-->
                 <li class="user-footer">
                   <div class="pull-left">
                     <a href="#" class="btn btn-default btn-flat">Profile</a>
                   </div>
                   <div class="pull-right">
                     <a class="btn btn-default btn-flat" href="{{ route('logout') }}"
                         onclick="event.preventDefault();
                                  document.getElementById('logout-form').submit();">
                         Sign out
                     </a>

                     <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                         {{ csrf_field() }}
                     </form>
                   </div>
                 </li>
               </ul>
             </li>
           @endif
         </ul>
       </div>
     </nav>
   </header>
