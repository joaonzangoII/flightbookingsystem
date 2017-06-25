@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' => $booking->booking_number])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">{{$booking->booking_number}}</h3>
              </div>
              <div class="box-body">
                <!-- /.box -->
                <img class="img-responsive" src="{{$booking->image}}" alt="{{$booking->booking_number}}" />
                <strong><i class="fa fa-book margin-r-5"></i> User</strong>
                <p>{{$booking->user->name}}</p>
                <strong><i class="fa fa-book margin-r-5"></i> Aircraft</strong>
                <p>{{$booking->aircraft->name}}</p>
                <strong><i class="fa fa-book margin-r-5"></i> Origin</strong>
                <p>{{$booking->departure_flight->schedule->origin_airport->name}}</p>
                <strong><i class="fa fa-book margin-r-5"></i> Destination</strong>
                <p>{{$booking->return_flight->schedule->destination_airport->name}}</p>
                <strong><i class="fa fa-clock margin-r-5"></i> Duration</strong>
                <p>{{$booking->duration}}</p>
                <hr>
                <h3>Passengers</h3>
                <table class="table table-hover">
                  <thead>
                   <tr>
                     <td>Name</td>
                     <td>ID Number</td>
                     <td>Date of Birth</td>
                     <td>Meal</td>
                     <td>Drink</td>
                     <td>Food</td>
                     <td>Meal Type</td>
                   </tr>
                  </thead>
                  <tbody>
                    @foreach($booking->passengers as $key => $passenger)
                      <tr>
                        <td>{{$passenger->name}}</td>
                        <td>{{$passenger->id_number}}</td>
                        <td>{{$passenger->date_of_birth}}</td>
                        <td>{{$passenger->food_and_drink}}</td>
                        <td>{{is_null($passenger->drink) ? '' : ucFirst($passenger->drink->drink->name)}}</td>
                        <td>{{is_null($passenger->food) ? '' : ucFirst($passenger->food->food->name)}}</td>
                        <td>{{is_null($passenger->food) ? '' : ucFirst($passenger->food->food->food_type->name)}}</td>
                      </tr>
                    @endforeach
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
@endsection
@section("scripts")
  <script type="text/javascript">
  </script>
@endsection
