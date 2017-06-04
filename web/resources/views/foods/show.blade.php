@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' => $food->name])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">{{$food->name}}</h3>
              </div>
              <div class="box-body">
                <img class="img-responsive" src="{{$food->image}}" alt="{{$food->name}}" />
                <hr>
                <strong><i class="fa fa-book margin-r-5"></i> Description</strong>
                <p>
                  {{$food->description}}
                </p>
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
