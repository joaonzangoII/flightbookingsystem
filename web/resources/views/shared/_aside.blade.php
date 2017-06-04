<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">
    <!-- Sidebar user panel -->
    <div class="user-panel">
      <div class="pull-left image">
        <img src="/admin/img/user2-160x160.jpg" class="img-circle" alt="User Image">
      </div>
      @if(Auth::guard()->check())
        <div class="pull-left info">
          <p>{{Auth::user()->name}}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      @endif
    </div>
    <!-- search form -->
    {{-- <form action="#" method="get" class="sidebar-form">
      <div class="input-group">
        <input type="text" name="q" class="form-control" placeholder="Search...">
            <span class="input-group-btn">
              <button type="submit"
                      name="search" id="search-btn"
                      class="btn btn-flat">
                      <i class="fa fa-search"></i>
              </button>
            </span>
      </div>
    </form> --}}
    <!-- /.search form -->
    <!-- sidebar menu: : style can be found in sidebar.less -->
    <ul class="sidebar-menu">
      <li class="header">MAIN NAVIGATION</li>
      <li class="treeview active">
        <a href="{{route('backoffice.home')}}">
          <i class="fa fa-dashboard"></i>
          <span>Dashboard</span>
        </a>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Users</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.users.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Bookings</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.bookings.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Flights</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.flights.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Schedules</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.schedules.create')}}"><i class="fa fa-circle-o"></i> Create</a></li>
          <li><a href="{{route('backoffice.schedules.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Foods</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.foods.create')}}"><i class="fa fa-circle-o"></i> Create</a></li>
          <li><a href="{{route('backoffice.foods.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Drinks</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.drinks.create')}}"><i class="fa fa-circle-o"></i> Create</a></li>
          <li><a href="{{route('backoffice.drinks.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
      <li class="treeview">
        <a href="#">
          <i class="fa fa-files-o"></i>
          <span>Airports</span>
        </a>
        <ul class="treeview-menu">
          <li><a href="{{route('backoffice.airports.create')}}"><i class="fa fa-circle-o"></i> Create</a></li>
          <li><a href="{{route('backoffice.airports.index')}}"><i class="fa fa-circle-o"></i> View All</a></li>
        </ul>
      </li>
    </ul>
  </section>
  <!-- /.sidebar -->
</aside>
