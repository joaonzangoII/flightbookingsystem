@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Users'])
      <!-- Main content -->
      <section class="content">
       <div class="box">
          <div class="box-header with-border">
            <h3 class="box-title">Users Table</h3>
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
                  <th>Firstnames</th>
                  <th>Surname</th>
                  <th>Id Number</th>
                  <th>User Type</th>
                  <th>Country</th>
                  <th>Added At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                @foreach($users as $key=>$user)
                    <tr>
                      <td>{{$user->id}}</td>
                      <td>{{$user->firstnames}}</td>
                      <td>{{$user->surname}}</td>
                      <td>{{$user->id_number}}</td>
                      <td>{{$user->email}}</td>
                      <td>{{$user->user_type->id}}</td>
                      <td>{{$user->country->id}}</td>
                      <td>{{$user->created_at->format("Y-m-d")}}</td>
                      <td>
                        <a class="btn btn-success" href="{{$user->show_link}}"><i class="fa fa-eye"></i></a>
                        {{-- <a class="btn btn-primary" href="{{$user->edit_link}}"><i class="fa fa-edit"></i></a> --}}
                      </td>
                    </tr>
                @endforeach
              </tbody>
            </table>
          </div>
          <!-- /.box-body -->
          <div class="box-footer clearfix">
            {{$users->render()}}
          </div>
        </div>
      </section>
    </div>
</div>
@endsection
