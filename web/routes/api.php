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

Route::name('countries')->get('/countries', function(Request $request){
  $countries = Country::latest()->get();
  return $countries;
});

Route::name('airports')->get('/airports', function(Request $request){
  $airports = Airport::latest()->get();
  return $airports;
});

Route::name('users')->get('/users', function(Request $request){
  $users = User::latest()->get();
  return $users;
});

Route::name('bookings')->get('/bookings', function(Request $request){
  $bookings = Booking::with('passengers', 'passengers.meal', 'passengers.meal.drink',
                            'passengers.meal.food', 'aircraft', 'departure_flight',
                            'passengers.meal.food.food_type', 'passengers.meal.drink',
                            'passengers.flight_seat', 'passengers.flight_seat.travel_class',
                            'departure_flight.schedule', 'return_flight', 'return_flight.schedule')
                      ->latest()
                      ->get();
  return $bookings;
});

Route::name('bookings.mine')->get('/bookings/{user}', function(Request $request, String $user){
    $bookings = Booking::with('passengers', 'passengers.meal', 'passengers.meal.drink',
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
  $aircraft_seats = AircraftSeat::with('travel_class', 'seat_price')
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

Route::name('register')->post('/register', function(Request $request){
  $data = $request->all();
  $validator = Validator::make($data , [
      'first_name'=> 'required|string',
      // 'middle_name' => 'required|string',
      'last_name' => 'required|string',
      'id_number' => 'required|string|size:13|unique:users|correct',
      'phone' => 'required|string',
      'email' => 'required|string|unique:users',
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
      'first_name' => $request->input('first_name'),
      'middle_name' => $request->input('middle_name'),
      'last_name' => $request->input('last_name'),
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
});


Route::name('book')->post('/make-booking', function(Request $request){
    $user_id = $request->input('user_id');
    $departure_flight_id = $request->input('departure_flight_id');
    $aircraft_id = $request->input('aircraft_id');
    $passengers_str = $request->input('passengers');
    $passengersObjs = json_decode($passengers_str);

    foreach ($passengersObjs as $key => $p) {
      $flight_seat_id = $p->flight_seat_id;
      $flight_seat = FlightSeat::where('id', $flight_seat_id)->first();
      if($flight_seat->available==false){
        return response()->json([
          'code' => '500',
          'erro' => true,
          'messages' => [
            'flight_seat'=> ['seat already taken']
          ]
        ]);
      }
    }

    $validator = Validator::make($request->all(), [
        'user_id'=> 'required|string',
        'departure_flight_id' => 'required|string',
        'aircraft_id' => 'required|string',
        'passengers' => 'required|string',
    ]);

    if($validator->fails()){
      return response()->json([
        'code' => '500',
        'erro' => true,
        'messages' => $validator->messages()
      ]);
    }

    $data = [
        'user_id' => $user_id,
        'departure_flight_id' => $departure_flight_id,
        'return_flight_id' => $departure_flight_id,
        'aircraft_id' => $aircraft_id,
    ];

    $booking = Booking::create($data);
    $total = 0;

    foreach ($passengersObjs as $key => $p) {
      $passenger = Passenger::create([
        'first_name' => $p->first_name,
        'middle_name' => $p->middle_name,
        'last_name' => $p->last_name,
        'id_number' => $p->id_number,
        'date_of_birth' => $p->date_of_birth,
        'gender' => 'm',
        'booking_id' => $booking->id,
        'flight_seat_id' => $p->flight_seat_id,
      ]);

      $flight_seat_id = $passenger->flight_seat_id;

      $seat_price = FlightSeatPrice::where('flight_seat_id', $flight_seat_id)->first();
      if(is_null($seat_price)){
         return response()->json([
           'code' => '500',
           'erro' => true,
           'messages' => [$seat_price]
         ]);
       }

      $price = $seat_price->price;
      $total = $total + $price;

      $flight_seat = FlightSeat::where('id', $flight_seat_id)->first();
      $flight_seat->available = false;
      $flight_seat->save();

      if(!is_null($p->meal)){
        $meal = Meal::create([
          'passenger_id' => $passenger->id,
          'drink_id' => $p->meal->drink_id,
          'food_id' => $p->meal->food_id
        ]);
      }
    }

    $booking = Booking::with('passengers', 'passengers.meal', 'passengers.meal.drink',
                          'passengers.meal.food', 'passengers.flight_seat',
                          'passengers.meal.food.food_type', 'passengers.meal.drink',
                          'passengers.flight_seat.travel_class', 'aircraft',
                          'departure_flight', 'departure_flight.aircraft', 'departure_flight.schedule',
                          'return_flight', 'return_flight.aircraft', 'return_flight.schedule')
                    ->where('id', $booking->id)
                    ->first();
    $booking->update(["subtotal"=> $total, "total"=> $total]);
    return response()->json([
      'code' => '200',
      'erro' => false,
      'booking' => $booking
    ]);
});
