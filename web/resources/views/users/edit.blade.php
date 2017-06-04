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
              <!-- /.box-header -->
              <form role="form"
                    action="{{route('backoffice.airports.update', $airport->id)}}"
                    method="PATCH"
                    enctype="multipart/form-data">
                {{csrf_field()}}
                <div class="box-body">
                  <div class="form-group">
                    <label>Name:</label>
                    <div class="input-groupp">
                      <input id="name" name="name" type="text" class="form-control" value="{{$airport->name}}"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Iata Code:</label>
                    <div class="input-groupp">
                      <input id="iata_airport_code" name="iata_airport_code" type="text" class="form-control"  value="{{$airport->iata_airport_code}}"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>City:</label>
                    <div class="input-groupp">
                      <input id="city" name="city" type="text" class="form-control"  value="{{$airport->city}}"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Country</label>
                    <select name="food_type" class="form-control">
                      <option value="0">Select Food Type</option>
                      @foreach($countries as $cpkey=>$country)
                        <option {{$country->id==$airport->country_id ? 'selected':''}} value="{{$country->id}}">{{$country->name}}</option>
                      @endforeach
                    </select>
                  </div>
                  {{-- <div class="form-group">
                    <label>Description:</label>
                    <div class="input-groupg">
                    <textarea name="description"  class="form-control" rows="5">{{$airport->description}}</textarea>
                    </div>
                    <!-- /.input group -->
                  </div> --}}
                  {{-- <div class="form-group">
                    <label class="">Image: </label>
                    <div class="">
                      <div class="upload-section">
                        <label class="upload-image" for="upload">
                          <input type="file" id="upload" name="image">
                        </label>
                      </div>
                    </div>
                  </div> --}}
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
