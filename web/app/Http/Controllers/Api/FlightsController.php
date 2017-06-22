<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

use App\User;
use App\Passenger;
use App\Meal;
use App\Flight;
use App\AircraftSeat;
use App\FlightSeat;
use App\FlightSeatPrice;
use App\BookingSequence;
use App\Booking;
use App\Country;
use App\TravelClass;
use App\Airport;
use App\Food;
use App\Drink;
use App\Schedule;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Nexmo\Laravel\Facade\Nexmo;
use App\Notifications\FlightBooked;
use App\Notifications\UserBookedFlight;

class FlightsController extends Controller
{
  public function index(){
    $flights = Flight::with('flight_status', 'aircraft')->get();
    return $flights;
  }

  public function storeBooking(Request $request)
  {
    $user_id = $request->input('user_id');
    $departure_flight_id = $request->input('departure_flight_id');
    $aircraft_id = $request->input('aircraft_id');
    $passengers_str = $request->input('passengers');
    $passengersList = json_decode($passengers_str);

    $user = User::find($user_id);
    foreach ($passengersList as $key => $p) {
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

    // foreach ($passengersList as $key => $p)
    // {
    //
    // }

    $data = [
        'user_id' => $user_id,
        'departure_flight_id' => $departure_flight_id,
        'return_flight_id' => $departure_flight_id,
        'aircraft_id' => $aircraft_id,
    ];

    $data["booking_number"] = $this->booking_number();
    $booking = Booking::create($data);
    $total = 0;
    $subtotal = 0;

    foreach ($passengersList as $key => $p)
    {
      $passenger = Passenger::create([
        'firstnames' => $p->firstnames,
        'surname' => $p->surname,
        'id_number' => $p->id_number,
        'date_of_birth' => $p->date_of_birth,
        'gender' => 'm',
        'booking_id' => $booking->id,
        'flight_seat_id' => $p->flight_seat_id,
      ]);

      $flight_seat_id = $passenger->flight_seat_id;
      $seat_price = FlightSeatPrice::where('flight_seat_id', $flight_seat_id)
                                   ->first();
      if(is_null($seat_price))
      {
         return response()->json([
           'code' => '500',
           'erro' => true,
           'messages' => [$seat_price]
         ]);
      }

      $price = $seat_price->price;
      $total = $total + $price;
      $subtotal = $total;
      $flight_seat = FlightSeat::where('id', $flight_seat_id)->first();
      $flight_seat->available = false;
      $flight_seat->save();

      if(isset($p->drink_id))
      {
        if(!is_null($p->drink_id))
        {
          $drink = \App\MealDrink::create([
            'passenger_id' => $passenger->id,
            'drink_id' => $p->drink_id,
          ]);
        }
      }else{
        $total = $total - ($price * 0.1);
      }

      if(isset($p->food_id))
      {
        if(!is_null($p->food_id))
        {
          $food = \App\MealFood::create([
            'passenger_id' => $passenger->id,
            'food_id' => $p->food_id,
          ]);
        }
      }else{
        $total = $total - ($price * 0.1);
      }
    }

    $booking = Booking::with('passengers',
                             'passengers.booking',
                             'passengers.drink',
                             'passengers.food',
                             'passengers.food.food',
                             'passengers.food.food.food_type',
                             'passengers.flight_seat',
                             'passengers.flight_seat.travel_class',
                             'aircraft','departure_flight',
                             'departure_flight.aircraft',
                             'departure_flight.schedule',
                             'return_flight',
                             'return_flight.aircraft',
                             'return_flight.schedule')
                    ->where('id', $booking->id)
                    ->first();

    $booking->update(["subtotal"=> $subtotal, "total"=> $total]);

    // $booking->notify(new FlightBooked($booking));
    $user->notify(new UserBookedFlight($booking));

    return response()->json([
      'code' => '200',
      'erro' => false,
      'booking' => $booking
    ]);
  }

  public function booking_number()
  {
    $date = date('Y-m-d');
    $sequences = BookingSequence::where('date', $date);
    if(count($sequences)==0){
      $sequence = 0;
    }else{
      $sequence = \DB::table('booking_sequences')
                      ->where('date', date("Y-m-d"))
                      ->latest()
                      ->pluck('id')
                      ->first();
    }

    $sequence = $sequence + 1;
    $code = "BK";
    $b_number = $code . date("ymd") . str_pad($sequence, 4, '0', STR_PAD_LEFT);
    BookingSequence::create(["date" => $date]);
    return $b_number;
  }

  public function addOrUpdateDrink(Request $request)
  {
      $passenger_id = $request->input('passenger_id');
      $drink_id = $request->input('task_id');
      $action = $request->input('action');
      $passenger = Passenger::with('drink', 'flight_seat', 'food', 'food.food', 'food.food.food_type')
                            ->find($passenger_id);

      $booking = $this->getBooking($passenger->booking_id);
      $booking_pass = $booking->passengers()->where('id', $passenger_id)->first();
      if($action==="delete")
      {
        $booking_pass->drink()->delete();
        $total = $booking->total - ($booking->total * 0.1);
        $booking->update(["total"=> $total]);
      }
      else{
        if(is_null($booking_pass->drink))
        {
           $drink = \App\MealDrink::create([
             'passenger_id' => $booking_pass->id,
             'drink_id' => $drink_id,
           ]);

           $price = $booking_pass->flight_seat->flight_seat_price->price;
           $total = $booking->total + ($price * 0.1);
           $booking->update(["total"=> $total]);
        }
        else{
           $meal_drink = \App\MealDrink::where("passenger_id", $booking_pass->id)
                                       ->first();
           $meal_drink->update(["drink_id"=> $drink_id]);
        }
      }

      return response()->json([
        'code' => '200',
        'erro' => false,
        'booking' => $this->getBooking($passenger->booking_id)
      ]);
  }

  public function addOrUpdateFood(Request $request)
  {
      $passenger_id = $request->input('passenger_id');
      $food_id = $request->input('task_id');
      $action = $request->input('action');
      $passenger = Passenger::with('drink', 'food.food', 'food.food.food_type')
                            ->find($passenger_id);

      $booking = $this->getBooking($passenger->booking_id);
      $booking_pass = $booking->passengers()->where('id', $passenger_id)->first();
      if($action==="delete")
      {
        $booking_pass->food()->delete();
        $total = $booking->total - ($booking->total * 0.1);
        $booking->update(["total"=> $total]);
      }
      else{
        if(is_null($booking_pass->food))
        {
           $food = \App\MealFood::create([
             'passenger_id' => $booking_pass->id,
             'food_id' => $food_id,
           ]);

           $price = $booking_pass->flight_seat->flight_seat_price->price;
           $total = $booking->total + ($price * 0.1);
           $booking->update(["total"=> $total]);
        }
        else{
           $meal_food = \App\MealFood::where("passenger_id", $booking_pass->id)
                                       ->first();
           $meal_food->update(["food_id"=> $food_id]);
        }
      }

      return response()->json([
        'code' => '200',
        'erro' => false,
        'booking' => $this->getBooking($passenger->booking_id)
      ]);
  }

  public function getBooking(String $passenger_id)
  {
    return Booking::with('passengers',
                             'passengers.booking',
                             'passengers.drink',
                             'passengers.drink.drink',
                             'passengers.food.food',
                             'passengers.food.food.food_type',
                             'passengers.flight_seat',
                             'passengers.flight_seat.travel_class',
                             'aircraft',
                             'departure_flight',
                             'departure_flight.aircraft',
                             'departure_flight.schedule',
                             'return_flight',
                             'return_flight.aircraft',
                             'return_flight.schedule')
                ->where('id', $passenger_id)
                ->first();
  }

  public function getFlights(Request $request){
    $flight_departure_date = $request->input('departure_date');
    $flight_return_date = $request->input('return_date');
    $flight_origin_airport_id = $request->input('origin_airport_id');
    $flight_destination_airport_id = $request->input('destination_airport_id');

    $schedules = Schedule::with('flight',
                                'flight.aircraft',
                                'flight.aircraft.aircraft_manufacturer')
                         ->where('origin_airport_id', $flight_origin_airport_id)
                         ->where('destination_airport_id', $flight_destination_airport_id)
                         ->where('date', $flight_departure_date)
                         ->bookable()
                         ->get();
    return $schedules;
  }

  public function getFlightDetail(Request $request, String $flight){
    $flight = Flight::with('flight_status',
                             'aircraft',
                             'aircraft.aircraft_manufacturer',
                             'flight_seat_prices',
                             'schedule')
                         ->where('id', $flight)
                         ->get();
    return $flight;
  }

  public function getFlightsTimetable(Request $request){
    $flight_origin_airport_id = $request->input('origin_airport_id');
    $flight_destination_airport_id = $request->input('destination_airport_id');
    $flight_departure_date = $request->input('departure_date');
    $schedules = Schedule::with('flight',
                                'flight.aircraft',
                                'flight.aircraft.aircraft_manufacturer')
                         ->where('origin_airport_id', $flight_origin_airport_id)
                         ->where('destination_airport_id', $flight_destination_airport_id)
                         ->where('date', $flight_departure_date)
                         ->bookable()
                         ->get();
      return $schedules;
  }

  public function getInitialData(Request $request, User $user= null){
    $countries = Country::latest()->get();
    $travelClasses = TravelClass::latest()->get();
    $airports = Airport::latest()->get();
    $foods =    Food::latest()->get();
    $drinks =   Drink::latest()->get();
    $bookings = Booking::with('passengers',
                             'passengers.booking',
                             'passengers.drink',
                             'passengers.food',
                             'passengers.food.food',
                             'passengers.food.food.food_type',
                             'passengers.flight_seat',
                             'passengers.flight_seat.travel_class',
                             'aircraft',
                             'departure_flight',
                             'departure_flight.aircraft',
                             'departure_flight.schedule',
                             'return_flight',
                             'return_flight.aircraft',
                             'return_flight.schedule')
                            ->where('user_id', $user->id)
                            ->latest()
                            ->get();
    $schedules = Schedule::with('flight', 'flight.flight_seat_prices',
                                'flight.flight_seat_prices.flight_seat', 'flight.flight_seat_prices.flight_seat.travel_class',
                                'flight.aircraft', 'flight.aircraft.aircraft_manufacturer')
                         ->bookable()
                         ->latest()
                         ->get()
                         ->take(4);

    return response()->json([
         'countries' => $countries,
         'travelClasses' => $travelClasses,
         'airports' => $airports,
         'foods' => $foods,
         'drinks' => $drinks,
         'myBookings' => $bookings,
         'schedules' => $schedules,
    ]);
  }
}
