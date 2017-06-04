@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Create Airport'])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">Add  Airport</h3>
              </div>
              <!-- /.box-header -->
              <!-- form start -->
              <form role="form"
                    action="{{route('backoffice.airports.store')}}"
                    method="POST"
                    enctype="multipart/form-data">
                {{csrf_field()}}
                <div class="box-body">
                  <div class="form-group">
                    <label>Name:</label>
                    <div class="input-groupp">
                      <input id="name" name="name" type="text" class="form-control"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Iata Code:</label>
                    <div class="input-groupp">
                      <input id="iata_airport_code" name="iata_airport_code" type="text" class="form-control"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>City:</label>
                    <div class="input-groupp">
                      <input id="city" name="city" type="text" class="form-control"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Country</label>
                    <select name="country" class="form-control">
                      <option value="0">Select Country</option>
                      @foreach($countries as $ckey=>$country)
                        <option value="{{$country->id}}">{{$country->name}}</option>
                      @endforeach
                    </select>
                  </div>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                  <button type="submit" class="btn btn-primary">Submit</button>
                </div>
              </form>
            </div>
            <!-- /.box -->
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
