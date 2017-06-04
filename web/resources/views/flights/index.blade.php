@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Flights'])
      <!-- Main content -->
      <section class="content">
       <div class="box">
          <div class="box-header with-border">
            <h3 class="box-title">Flights Table</h3>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th style="width: 10px">#</th>
                  <th>Aircraft</th>
                  <th>Departure</th>
                  <th>Arrival</th>
                  <th>Flight Status</th>
                  <th>Added At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                @foreach($flights as $key=>$flight)
                    <tr>
                      <td>{{$flight->id}}</td>
                      <td>{{$flight->aircraft->name}}</td>
                      <td>{{$flight->schedule->departure_time->format("Y-m-d @ H:m")}}</td>
                      <td>{{$flight->schedule->arrival_time->format("Y-m-d  @ H:m")}}</td>
                      <td>{{$flight->flight_status->name}}</td>
                      <td>{{$flight->created_at->format("Y-m-d")}}</td>
                      <td>
                        <a class="btn btn-success" href="{{$flight->show_link}}"><i class="fa fa-eye"></i></a>
                        <a class="btn btn-primary" href="{{$flight->edit_link}}"><i class="fa fa-edit"></i></a>
                      </td>
                    </tr>
                @endforeach
              </tbody>
            </table>
          </div>
          <!-- /.box-body -->
          <div class="box-footer clearfix">
            {{$flights->render()}}
          </div>
        </div>
      </section>
    </div>
</div>
@endsection
