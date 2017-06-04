@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Bookings'])
      <!-- Main content -->
      <section class="content">
       <div class="box">
          <div class="box-header with-border">
            <h3 class="box-title">Bookings Table</h3>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <table class="table table-hover">
              <thead>
                <tr>
                  {{-- public function user(){
                    return $this->hasOne('App\User', 'id', 'user_id');
                  }

                  public function departure_flight(){
                    return $this->hasOne('App\Flight', 'id', 'departure_flight_id');
                  }

                  public function return_flight(){
                    return $this->hasOne('App\Flight', 'id', 'return_flight_id');
                  }

                  public function passengers(){
                    return $this->hasMany('App\Passenger');
                  }

                  public function aircraft(){
                    return $this->hasOne('App\Aircraft', 'id', 'aircraft_id');
                  } --}}
                  <th style="width: 10px">#</th>
                  <th>Name</th>
                  <th>Aircraft</th>
                  <th>Origin</th>
                  <th>Destination</th>
                  <th>Passengers #</th>
                  <th>Added At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                @foreach($bookings as $key=>$booking)
                    <tr>
                      <td>{{$booking->id}}</td>
                      <td>{{$booking->user->name}}</td>
                      <td>{{$booking->aircraft->name}}</td>
                      <td>{{$booking->departure_flight->schedule->origin_airport->name}}</td>
                      <td>{{$booking->return_flight->schedule->destination_airport->name}}</td>
                      <td>{{count($booking->passengers)}}</td>
                      <td>{{$booking->created_at->format("Y-m-d")}}</td>
                      <td>
                        <a class="btn btn-success" href="{{$booking->show_link}}"><i class="fa fa-eye"></i></a>
                        <a class="btn btn-primary" href="{{$booking->edit_link}}"><i class="fa fa-edit"></i></a>
                      </td>
                    </tr>
                @endforeach
              </tbody>
            </table>
          </div>
          <!-- /.box-body -->
          <div class="box-footer clearfix">
            {{$bookings->render()}}
          </div>
        </div>
      </section>
    </div>
</div>
@endsection
