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
              <!-- /.box-header -->
              <form role="form"
                    action="{{route('backoffice.drinks.update', $drink->id)}}"
                    method="PATCH"
                    enctype="multipart/form-data">
                {{csrf_field()}}
                <div class="box-body">
                  <div class="form-group">
                    <label>Name:</label>
                    <div class="input-groupp">
                      <input id="name" name="name" type="text" class="form-control" value="{{$drink->name}}"/>
                    </div>
                    <!-- /.input group -->
                  </div>
                  <div class="form-group">
                    <label>Description:</label>
                    <div class="input-groupg">
                    <textarea name="description"  class="form-control" rows="5">{{$drink->description}}</textarea>
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
