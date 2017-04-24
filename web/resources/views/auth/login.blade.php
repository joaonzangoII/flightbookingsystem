@extends('layouts.auth')
@section('stylesheets')
@endsection
@section('content')
  <div class="container">
      <div class="row">
          <div class="col-md-8 col-md-offset-2">
              <div class="panel panel-default">
                  <div class="panel-heading">Login</div>
                  <div class="panel-body">
                    <div class="login-box">
                      <div class="login-logo">
                        <a href="/"><b>Small Gate</b></br>Flight Booking System</a>
                      </div>
                      <!-- /.login-logo -->
                      <div class="login-box-body">
                        <p class="login-box-msg">Sign in to start your session</p>
                        <form method="POST" action="{{ route('login') }}">
                          {{ csrf_field() }}
                          <div class="form-group  has-feedback {{ $errors->has('email') ? ' has-error' : ''}}">
                            <input type="email" name="email" class="form-control" placeholder="Email">
                            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                            @if ($errors->has('email'))
                                <span class="help-block">
                                    <strong>{{ $errors->first('email') }}</strong>
                                </span>
                            @endif
                          </div>
                          <div class="form-group has-feedback {{ $errors->has('password') ? ' has-error' : ''}}">
                            <input type="password" name="password" class="form-control" placeholder="Password">
                            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                            @if ($errors->has('password'))
                                <span class="help-block">
                                    <strong>{{ $errors->first('password') }}</strong>
                                </span>
                            @endif
                          </div>
                          <div class="row">
                            <div class="col-xs-8">
                              <div class="checkbox icheck">
                                <label>
                                  <input type="checkbox" name="remember" {{ old('remember') ? 'checked' : '' }}> Remember Me
                                </label>
                              </div>
                            </div>
                            <!-- /.col -->
                            <div class="col-xs-4">
                              <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
                            </div>
                            <!-- /.col -->
                          </div>
                        </form>

                        {{-- <div class="social-auth-links text-center">
                          <p>- OR -</p>
                          <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
                            Facebook</a>
                          <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using
                            Google+</a>
                        </div> --}}
                        <!-- /.social-auth-links -->
                        <a class="text-cente" href="{{ route('password.request') }}">
                            I forgot my password
                        </a><br>
                        {{-- <a class="text-center" href="{{ route('register') }}">
                            Register a new membership
                        </a> --}}
                      </div>
                      <!-- /.login-box-body -->
                    </div>
                  </div>
              </div>
          </div>
      </div>
  </div>
@endsection
@section('scripts')
@endsection
