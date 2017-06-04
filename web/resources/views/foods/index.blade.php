@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Foods'])
      <!-- Main content -->
      <section class="content">
         <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">Foods Table</h3>
              <a class="btn btn-primary pull-right" href="{{route('backoffice.foods.create')}}">
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
                    <th>Food Type</th>
                    <th>Added At</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  @foreach($foods as $key=>$food)
                      <tr>
                        <td>{{$food->id}}</td>
                        <td>{{$food->name}}</td>
                        <td>{{$food->food_type->name}}</td>
                        <td>{{$food->created_at->format("Y-m-d")}}</td>
                        <td>
                          <a class="btn btn-success" href="{{$food->show_link}}"><i class="fa fa-eye"></i></a>
                          <a class="btn btn-primary" href="{{$food->edit_link}}"><i class="fa fa-edit"></i></a>
                        </td>
                      </tr>
                  @endforeach
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
              {{$foods->render()}}
            </div>
          </div>
      </section>
    </div>
</div>
@endsection
