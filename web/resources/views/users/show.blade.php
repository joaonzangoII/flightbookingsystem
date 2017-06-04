@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' => $user->name])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">{{$user->name}}</h3>
              </div>
              <div class="box-body">
                <!-- /.box -->
                {{-- <img class="img-responsive" src="{{$user->image}}" alt="{{$user->name}}" /> --}}
                <strong><i class="fa fa-book margin-r-5"></i> FirstNames</strong>
                <p>
                  {{$user->firstnames}}
                </p>
                <strong><i class="fa fa-book margin-r-5"></i> Surname</strong>
                <p>
                  {{$user->surname}}
                </p>
                <strong><i class="fa fa-book margin-r-5"></i> User Type</strong>
                <p>
                  {{$user->user_type->name}}
                </p>
                <strong><i class="fa fa-book margin-r-5"></i> Country</strong>
                <p>
                  {{$user->country->name}}
                </p>
              </div>
            </div>
          <!-- /.box-header -->
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
