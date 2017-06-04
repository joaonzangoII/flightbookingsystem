@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' => $airport->name])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">{{$airport->name}}</h3>
              </div>
              <div class="box-body">
                {{-- <img class="img-responsive" src="{{$airport->image}}" alt="{{$airport->name}}" /> --}}
                <p>
                  {{$airport->iata_airport_code}}
                </p>
                <p>
                  {{$airport->city}}
                </p>
                <p>
                  {{$airport->country->name}}
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
