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
              <!-- /.box-header -->
              <form role="form"
                    action="{{route('backoffice.foods.update', $food->id)}}"
                    method="PATCH"
                    enctype="multipart/form-data">
                {{csrf_field()}}
                <div class="box-body">
                  <div class="form-group">
                    <label>Name:</label>
                    <div class="input-groupp">
                      <input id="name" name="name" type="text" class="form-control" value="{{$food->name}}"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Description:</label>
                    <div class="input-groupg">
                    <textarea name="description"  class="form-control" rows="5">{{$food->description}}</textarea>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label class="">Image: </label>
                    <div class="">
                      <div class="upload-section">
                        <label class="upload-image" for="upload">
                          <input type="file" id="upload" name="image">
                        </label>
                      </div>
                    </div>
                  </div>
                  <!-- select -->
                  <div class="form-group">
                    <label>Food Type</label>
                    <select name="food_type" class="form-control">
                      <option value="0">Select Food Type</option>
                      @foreach($foodTypes as $airpkey=>$food_type)
                        <option {{$food_type->id==$food->food_type_id ? 'selected':''}} value="{{$food_type->id}}">{{$food_type->name}}</option>
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
