@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Schedules'])

      <!-- Main content -->
      <section class="content">
         <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">Bordered Table</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th style="width: 10px">#</th>
                    <th>Origin</th>
                    <th>Destination</th>
                    <th>Departure Date</th>
                    <th>Arrival Date</th>
                    <th>Date</th>
                    <th>Duration</th>
                    <th style="width: 40px">Label</th>
                  </tr>
                </thead>
                <tbody>
                  @foreach($schedules as $key=>$schedule)
                    <tr>
                      <td>{{$schedule->id}}</td>
                      <td>{{$schedule->origin_airport->name}}</td>
                      <td>{{$schedule->destination_airport->name}}</td>
                      <td>{{$schedule->departure_time}}</td>
                      <td>{{$schedule->arrival_time}}</td>
                      <td>{{$schedule->date}}</td>
                      <td>{{$schedule->duration}}</td>
                      <td>
                        {{$schedule->flight->flight_status->name}}
                      </td>
                      {{-- <td><span class="badge bg-red">55%</span></td> --}}
                    </tr>
                  @endforeach
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
              {{$schedules->render()}}
            </div>
          </div>
      </section>
    </div>
</div>
@endsection
