@extends('layouts.auth')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Reset Password</div>
                <div class="panel-body">
                    @if (session('status'))
                        <div class="alert alert-success">
                            {{ session('status') }}
                        </div>
                    @endif
                    <div class="login-box">
                      <div class="login-logo">
                        <a href="/"><b>Small Gate</b></br>Flight Booking System</a>
                      </div>
                      <!-- /.login-logo -->
                      <div class="login-box-body">
                        <p class="login-box-msg">Reset your password to start your session</p>
                        <form class="form-horizontal" role="form" method="POST" action="{{ route('password.email') }}">
                            {{ csrf_field() }}
                            <div class="form-group has-feedback {{ $errors->has('email') ? ' has-error' : '' }}">
                                {{-- <label for="email" class="col-md-4 control-label">E-Mail Address</label> --}}
                                <input id="email" type="email" class="form-control" name="email" value="{{ old('email') }}">
                                @if ($errors->has('email'))
                                    <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                @endif
                            </div>

                            <div class="form-group">
                              <button type="submit" class="btn btn-primary">
                                  Send Password Reset Link
                              </button>
                            </div>
                        </form>
                      </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
