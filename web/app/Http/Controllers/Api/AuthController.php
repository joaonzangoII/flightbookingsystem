<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Passenger;
use App\Meal;
use Auth;
use App\User;
use App\UserType;
use App\Flight;
use App\AircraftSeat;
use App\FlightSeat;
use App\FlightSeatPrice;
use App\Booking;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Nexmo\Laravel\Facade\Nexmo;

class AuthController extends Controller
{
  public function login(Request $request)
  {
    $email = $request->input('email');
    $password = $request->input('password');
    $credentials = $request->only('email', 'password');
    $validator = Validator::make($credentials , [
        'email'=> 'required|string',
        'password' => 'required|string',
    ]);

    if($validator->fails()){
      return response()->json([
        'code' => '500',
        'erro' => true,
        'messages' => $validator->messages()
      ]);
    }

    if(!Auth::guard()->attempt($credentials, $request->has('remember'))){
      return response()->json([
        'code' => '500',
        'erro' => true,
        'messages' => [
          'email' => ['invalid credentials']
        ]
      ]);
    }

    $user = User::with('bookings', 'country')
                ->where('email', '=',  $email)
                ->first();

    if($user->isAdmin()){
      return response()->json([
        'code' => '500',
        'erro' => true,
        'messages' => [
          'email' => ['Only non Admin users can use the mobile app']
        ]
      ]);
    }

    return response()->json([
      'code' => '200',
      'erro' => false,
      'user' => $user
    ]);
  }

  public function register(Request $request)
  {
    $data = $request->all();
    $validator = Validator::make($data , [
        'firstnames'=> 'required|string',
        'surname' => 'required|string',
        'id_number' => 'required|string|size:13|unique:users|correct',
        // 'id_number' => 'required|string|size:13|unique:users',
        'phone' => 'required|string',
        'email' => 'required|email|string|unique:users',
        'password' => 'required|string|strength',
        'country_id' => 'required|string',
    ]);

    if($validator->fails()){
      return response()->json([
        'code' => '500',
        'erro' => true,
        'messages' => $validator->messages()
      ]);
    }

    $user_type = UserType::where('name', 'Customer')->first();
    $user = User::create([
        'firstnames' => $request->input('firstnames'),
        'surname' => $request->input('surname'),
        'id_number' => $request->input('id_number'),
        'phone' => $request->input('phone'),
        'email' => $request->input('email'),
        'password' => bcrypt($request->input('password')),
        'country_id' => $request->input('country_id'),
        'user_type_id' => $user_type->id,
    ]);

    $user = User::with('bookings', 'country')
                ->where('email', '=',  $request->input('email'))
                ->first();

    return response()->json([
      'code' => '200',
      'erro' => false,
      'user' => $user
    ]);
  }
}
