<?php

use Illuminate\Http\Request;
use App\User;
use App\Food;
use App\Drink;
use App\Airport;
use App\TravelClass;
use App\FlightStatus;
use App\Flight;
use App\Schedule;
use Illuminate\Support\Facades\Auth;
use Validator;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::name('airports')->get('/airports', function(Request $request){
  $airports = Airport::all();
  return $airports;
});

Route::name('travelclasses')->get('/travel-classes', function(Request $request){
  $travel_classes = TravelClass::all();
  return $travel_classes;
});

Route::name('schedules')->get('/schedules', function(Request $request){
  $schedules = Schedule::with('flight', 'flight.aircraft', 'flight.aircraft.aircraft_manufacturer')->get();
  return $schedules;
});

Route::name('find_flights')->post('/find-flights', function(Request $request){
    $flight_departure_date = $request->input('departure_date');
    $flight_return_date = $request->input('return_date');
    $flight_origin_airport_id = $request->input('origin_airport_id');
    $flight_destination_airport_id = $request->input('destination_airport_id');
    $schedules = Schedule::with('flight', 'flight.aircraft', 'flight.aircraft.aircraft_manufacturer')
                         ->where('origin_airport_id', $flight_origin_airport_id)
                         ->where('destination_airport_id', $flight_destination_airport_id)
                         ->where('date', $flight_departure_date)
                        //  ->where('date', $flight_return_date)
                        ->get();
      return $schedules;
});

Route::name('timetable')->post('/flights-timetable', function(Request $request){
    $flight_departure_date = $request->input('departure_date');
    $flight_origin_airport_id = $request->input('origin_airport_id');
    $flight_destination_airport_id = $request->input('destination_airport_id');
    $schedules = Schedule::with('flight', 'flight.aircraft', 'flight.aircraft.aircraft_manufacturer')
                         ->where('origin_airport_id', $flight_origin_airport_id)
                         ->where('destination_airport_id', $flight_destination_airport_id)
                         ->where('date', $flight_departure_date)->get();
      return $schedules;
});

Route::name('flight_status')->get('/flight-statuses', function(Request $request){
    $flight_statuses = FlightStatus::all();
    return $flight_statuses;
});

Route::name('flights')->get('/flights', function(Request $request){
    $flights = Flight::with('flight_status', 'aircraft')->get();
    return $flights;
});

Route::name('food')->get('/foods', function(Request $request){
    $foods = Food::all();
    return $foods;
});

Route::name('drink')->get('/drinks', function(Request $request){
    $drinks = Drink::all();
    return $drinks;
});

Route::name('login')->post('/login', function(Request $request){
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
              // ->where('password', '=', bcrypt($password))
              ->first();
  return response()->json([
    'code' => '200',
    'erro' => false,
    'user' => $user
  ]);
});
