@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' => $drink->name])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">{{$drink->name}}</h3>
              </div>
              <!-- /.box -->
              <img class="img-responsive" src="{{$drink->image}}" alt="{{$drink->name}}" />
              <p>
                {{$drink->description}}
              </p>
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
