@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>
          Schedules
          <small>Control panel</small>
        </h1>
        <ol class="breadcrumb">
          <li><a href="/home"><i class="fa fa-dashboard"></i> Home</a></li>
          <li class="active">Schedules</li>
        </ol>
      </section>

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
                    <th>Ruretion</th>
                    <th style="width: 40px">Label</th>
                  </tr>
                </thead>
                <tbody>
                  @foreach($schedules as $key=>$schedule)
                    <tr>
                      <td>{{$schedule->id}}</td>
                      <td>{{$schedule->origin_airport->name}}</td>
                      <td>{{$schedule->destination_airport->name}}</td>
                      <td>{{$schedule->duration}}</td>
                      <td>
                        <div class="progress progress-xs">
                          <div class="progress-bar progress-bar-danger" style="width: 55%"></div>
                        </div>
                      </td>
                      <td><span class="badge bg-red">55%</span></td>
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