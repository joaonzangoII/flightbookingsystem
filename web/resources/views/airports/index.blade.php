@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Airports'])
      <!-- Main content -->
      <section class="content">
       <div class="box">
          <div class="box-header with-border">
            <h3 class="box-title">Airports Table</h3>
            <a class="btn btn-primary pull-right" href="{{route('backoffice.airports.create')}}">
              <i class="fa fa-plus"></i>
            </a>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th style="width: 10px">#</th>
                  <th>Name</th>
                  <th>Added At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                @foreach($airports as $key=>$airport)
                    <tr>
                      <td>{{$airport->id}}</td>
                      <td>{{$airport->name}}</td>
                      <td>{{$airport->created_at->format("Y-m-d")}}</td>
                      <td>
                        <a class="btn btn-success" href="{{$airport->show_link}}"><i class="fa fa-eye"></i></a>
                        <a class="btn btn-primary" href="{{$airport->edit_link}}"><i class="fa fa-edit"></i></a>
                      </td>
                    </tr>
                @endforeach
              </tbody>
            </table>
          </div>
          <!-- /.box-body -->
          <div class="box-footer clearfix">
            {{$airports->render()}}
          </div>
        </div>
      </section>
    </div>
</div>
@endsection
