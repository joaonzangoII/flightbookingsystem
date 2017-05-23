<?php

use Illuminate\Http\Request;
use App\Country;
use App\User;
use App\UserType;
use App\Meal;
use App\Food;
use App\Drink;
use App\Airport;
use App\TravelClass;
use App\FlightStatus;
use App\Flight;
use App\Schedule;
use App\Aircraft;
use App\AircraftSeat;
use App\FlightSeat;
use App\FlightSeatPrice;
use App\Booking;
use App\Passenger;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
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

Route::name('routes')->get('/routes', function(Request $request){
  // $routes = Artisan::routeList();
  $routes =Route::getRoutes();;
  return $routes;
});

Route::name('initial.data')->get('/initial-data/{user?}', function(Request $request,
                                                                   String $user=""){
  $countries = Country::latest()->get();
  $travelClasses = TravelClass::latest()->get();
  $airports = Airport::latest()->get();
  $foods = Food::latest()->get();
  $drinks = Drink::latest()->get();
  $bookings = Booking::with('passengers', 'passengers.booking', 'passengers.meal', 'passengers.meal.drink',
                          'passengers.meal.food', 'passengers.flight_seat',
                          'passengers.meal.food.food_type', 'passengers.meal.drink',
                          'passengers.flight_seat.travel_class', 'aircraft',
                          'departure_flight', 'departure_flight.aircraft', 'departure_flight.schedule',
                          'return_flight', 'return_flight.aircraft', 'return_flight.schedule')
                          ->where('user_id', $user)
                          ->latest()
                          ->get();

  return response()->json([
       'countries' => $countries,
       'travelClasses' => $travelClasses,
       'airports' => $airports,
       'foods' => $foods,
       'drinks' => $drinks,
       'myBookings' => $bookings,
  ]);
});

Route::name('countries')->get('/countries', function(Request $request){
  $countries = Country::latest()->get();
  return $countries;
});

Route::name('airports')->get('/airports', function(Request $request){
  $airports = Airport::latest()->get();
  return $airports;
});

Route::name('meals')->get('/meals', function(Request $request){
  $meals = Meal::latest()->get();
  return $meals;
});

Route::name('users')->get('/users', function(Request $request){
  $users = User::latest()->get();
  return $users;
});

Route::name('bookings')->get('/bookings', function(Request $request){
  $bookings = Booking::with('passengers', 'passengers.booking', 'passengers.meal', 'passengers.meal.drink',
                            'passengers.meal.food', 'aircraft', 'departure_flight',
                            'passengers.meal.food.food_type', 'passengers.meal.drink',
                            'passengers.flight_seat', 'passengers.flight_seat.travel_class',
                            'departure_flight.schedule', 'return_flight', 'return_flight.schedule')
                      ->latest()
                      ->get();
  return $bookings;
});

Route::name('bookings.mine')->get('/bookings/{user}', function(Request $request, String $user){
    $bookings = Booking::with('passengers', 'passengers.booking', 'passengers.meal', 'passengers.meal.drink',
                          'passengers.meal.food', 'passengers.flight_seat',
                          'passengers.meal.food.food_type', 'passengers.meal.drink',
                          'passengers.flight_seat.travel_class', 'aircraft',
                          'departure_flight', 'departure_flight.aircraft', 'departure_flight.schedule',
                          'return_flight', 'return_flight.aircraft', 'return_flight.schedule')
                          ->where('user_id', $user)
                          ->latest()
                          ->get();
  return $bookings;
});

Route::name('travelclasses')->get('/travel-classes', function(Request $request){
  $travel_classes = TravelClass::all();
  return $travel_classes;
});

Route::name('seatprices')->get('/seat-prices', function(Request $request){
  $seat_prices = FlightSeatPrice::all();
  return $seat_prices;
});

Route::name('schedules')->get('/schedules', function(Request $request){
  $schedules = Schedule::with('flight', 'flight.aircraft', 'flight.aircraft.aircraft_manufacturer')->get();
  return $schedules;
});

Route::name('find-seats')->get('/aircraft-seats', function(Request $request){
  $aircraft_seats = AircraftSeat::with('travel_class', 'seat_price')->get();
  return $aircraft_seats;
});

Route::name('find-seats')->get('/aircraft-seats/{aircraft_id}',
                                function(Request $request,
                                         String $aircraft_id){
  $aircraft_seats = AircraftSeat::with('travel_class')
                       ->where('aircraft_id', $aircraft_id)
                       ->get();
  return $aircraft_seats;
});

Route::name('aircrafts')->get('/aircrafts/{aircraft_id}',
                                function(Request $request,
                                         String $aircraft_id){
  $aircrafts = Aircraft::with('flights', 'aircraft_manufacturer', 'aircraft_seats')
                       ->where('id', $aircraft_id)
                       ->get();

  return $aircrafts;
});

Route::name('find-seats')->get('/aircraft-seats/{aircraft_id}/{travel_class_id}',
                                function(Request $request,
                                         String $aircraft_id,
                                         String $travel_class_id){
  $aircraft_seats = AircraftSeat::with('travel_class', 'seat_price')
                       ->where('aircraft_id', $aircraft_id)
                       ->where('travel_class_id', $travel_class_id)
                       ->get();
  return $aircraft_seats;
});

Route::name('flights')->get('/flights', function(Request $request){
    $flights = Flight::with('flight_status', 'aircraft')->get();
    return $flights;
});

Route::name('flight-seats')->get('/flight-seats/{flight_id}/{travel_class_id}',
                                function(Request $request,
                                         String $flight_id,
                                         String $travel_class_id){
  $flight_seats = FlightSeat::with('travel_class', 'flight_seat_price')
                       ->where('flight_id', $flight_id)
                       ->where('available', true)
                       ->where('travel_class_id', $travel_class_id)
                       ->get();

  return $flight_seats;
});

Route::name('flight-seats-booked')->get('/booked-flight-seats/{flight_id}/{travel_class_id}',
                                function(Request $request,
                                         String $flight_id,
                                         String $travel_class_id){
  $flight_seats = FlightSeat::with('travel_class', 'flight_seat_price')
                       ->where('flight_id', $flight_id)
                       ->where('available', false)
                       ->where('travel_class_id', $travel_class_id)
                       ->get();
  return $flight_seats;
});

Route::name('flight-seats-booked-all')->get('/booked-flight-seats/{flight_id}',
                                function(Request $request,
                                         String $flight_id){
  $flight_seats = FlightSeat::with('travel_class', 'flight_seat_price')
                       ->where('flight_id', $flight_id)
                       ->where('available', false)
                       ->get();
  return $flight_seats;
});

Route::name('flight-seat-prices')->get('/flight-seat-prices/{flight_id}',
                                function(Request $request,
                                         String $flight_id){
  $flight_seats = FlightSeatPrice::with('flight', 'flight_seat')
                       ->where('flight_id', $flight_id)
                       ->get();

  return $flight_seats;
});

Route::name('flight-seats-all')->get('/flight-seats/{flight_id}',
                                function(Request $request,
                                         String $flight_id){
  $flight_seats = FlightSeat::with('travel_class', 'flight_seat_price')
                       ->where('flight_id', $flight_id)
                       ->get();

  return $flight_seats;
});

Route::name('food')->get('/foods', function(Request $request){
    $foods = Food::with('food_type')->get();
    return $foods;
});

Route::name('drink')->get('/drinks', function(Request $request){
    $drinks = Drink::all();
    return $drinks;
});
Route::name('flight_status')->get('/flight-statuses', function(Request $request){
    $flight_statuses = FlightStatus::all();
    return $flight_statuses;
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

Route::name('login')->post('/login', 'Api\AuthController@login');
Route::name('register')->post('/register', 'Api\AuthController@register');
Route::name('booking')->post('/make-booking', 'Api\FlightsController@store_booking');
Route::name('booking.update.meal')->post('/update-meal', 'Api\FlightsController@update_meal');
Route::name('booking.delete.meal')->post('/delete-meal', 'Api\FlightsController@delete_meal');
Route::name('sms')->get('/sms/send/{to}', 'Api\DefaultController@send_sms');
