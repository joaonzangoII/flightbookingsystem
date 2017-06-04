@extends('layouts.app')
@section('content')
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      @include('shared/_breadcrumbs', ['title' =>'Create Schedules'])
      <!-- Main content -->
      <section class="content">
        <div class="row">
          <div class="col-md-6">
              <!-- general form elements -->
            <div class="box box-primary">
              <div class="box-header with-border">
                <h3 class="box-title">Add  Schedule</h3>
              </div>
              <!-- /.box-header -->
              <!-- form start -->
              <form role="form" action="{{route('backoffice.schedules.store')}}" method="POST">
                {{csrf_field()}}
                <div class="box-body">
                  <div class="form-group">
                    <label>Departure Time:</label>
                    <div class="input-group date">
                      <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                      </div>
                      <input id="departure_time" name="departure_time" type="text" class="form-control pull-right datepicker">
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Arrival Time:</label>
                    <div class="input-group date">
                      <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                      </div>
                      <input id="arrival_time" name="arrival_time" type="text" class="form-control pull-right datepicker">
                    </div>
                  </div>
                  <!-- select -->
                  <div class="form-group">
                    <label>Aircraft</label>
                    <select name="aircraft" class="form-control">
                      <option value="0">Select Aircraft</option>
                      @foreach($aircrafts as $airkey=>$aircraft)
                        <option value="{{$aircraft->id}}">{{$aircraft->name}}</option>
                      @endforeach
                    </select>
                  </div>
                  <div class="row">
                    <div class="form-group col-md-4">
                      <label>First Class Price:</label>
                      <div class="input-groupp">
                        <input id="first_class_price"
                               name="first_class_price"
                               type="text"
                               class="form-control pull-right">
                      </div>
                      <!-- /.input group -->
                    </div>
                    <div class="form-group col-md-4">
                      <label>Business Class Price:</label>
                      <div class="input-groupp">
                        <input id="business_class_price"
                               name="business_class_price"
                               type="text"
                               class="form-control pull-right">
                      </div>
                      <!-- /.input group -->
                    </div>
                    <div class="form-group col-md-4">
                      <label>Economy Class Price:</label>
                      <div class="input-groupp">
                        <input id="economy_class_price"
                               name="economy_class_price"
                               type="text"
                               class="form-control pull-right">
                      </div>
                      <!-- /.input group -->
                    </div>
                  </div>
                  <div class="form-group">
                    <label>Origin Airport</label>
                    <select name="origin_airport" class="form-control">
                      <option value="0">Select Origin</option>
                      @foreach($airports as $airpkey=>$airport)
                        <option value="{{$airport->id}}">{{$airport->name}}</option>
                      @endforeach
                    </select>
                  </div>
                  <div class="form-group">
                    <label>Destination Airport</label>
                    <select name="destination_airport" class="form-control">
                      <option value="0">Select Destination</option>
                      @foreach($airports as $airpkey=>$airport)
                        <option value="{{$airport->id}}">{{$airport->name}}</option>
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
    var format = "YYYY-MM-DD H:mm:ss";
      //Date picker
    $('#departure_time').datetimepicker({
      inline: true,
      sideBySide: true,
      format: format
    });

    $('#arrival_time').datetimepicker({
      inline: true,
      sideBySide: true,
      format: format
    });
  </script>
@endsection
