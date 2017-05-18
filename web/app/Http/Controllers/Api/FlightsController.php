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
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
use Nexmo\Laravel\Facade\Nexmo;

class FlightsController extends Controller
{

  public function store_booking(Request $request)
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

    foreach ($passengersList as $key => $p) {
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

      if(isset($p->meal)){
        if(!is_null($p->meal)){
          $meal = Meal::create([
            'passenger_id' => $passenger->id,
            'drink_id' => $p->meal->drink_id,
            'food_id' => $p->meal->food_id
          ]);
        }
      }else{
        $subtotal = $total;
        $total = $total * 0.9;
      }
    }

    $booking = Booking::with('passengers', 'passengers.booking', 'passengers.meal', 'passengers.meal.drink',
                          'passengers.meal.food', 'passengers.flight_seat',
                          'passengers.meal.food.food_type', 'passengers.meal.drink',
                          'passengers.flight_seat.travel_class', 'aircraft',
                          'departure_flight', 'departure_flight.aircraft', 'departure_flight.schedule',
                          'return_flight', 'return_flight.aircraft', 'return_flight.schedule')
                    ->where('id', $booking->id)
                    ->first();

    $booking->update(["subtotal"=> $subtotal, "total"=> $total]);

    try{
      $message = Nexmo::message()->send([
        'to' => $user->phone,
        'from' => env('NEXMO_NUMBER'),
        'text' => 'Your booking number is ' . $booking->booking_number
      ]);
    }
    catch (\Exception $e) {
      return response()->json([
            'code' => '500',
            'erro' => true,
            'messages' => ['Error sending sms message, your booking nunber is ' . $booking->booking_number]
          ]);
    } 

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

  public function update_booking(Request $request)
  {
      $user_id = $request->input('user_id');
      $passengerIn = json_decode($request->input('passenger'));
      $passenger = Passenger::with('meal', 'meal.food', 'meal.drink')
                            ->find($passengerIn->id);

      $booking = Booking::with('passengers', 'passengers.booking', 'passengers.meal', 'passengers.meal.drink',
                        'passengers.meal.food', 'passengers.flight_seat',
                        'passengers.meal.food.food_type', 'passengers.meal.drink',
                        'passengers.flight_seat.travel_class', 'aircraft',
                        'departure_flight', 'departure_flight.aircraft', 'departure_flight.schedule',
                        'return_flight', 'return_flight.aircraft', 'return_flight.schedule')
                  ->where('id', $passenger->booking_id)
                  ->first();

      foreach($booking->passengers as $key=>$pass){
        if($passengerIn->id == $pass->id){
          if(is_null($pass->meal)){
            $meal = Meal::create([
              'passenger_id' => $pass->id,
              'drink_id' => $passengerIn->meal->drink_id,
              'food_id' =>  $passengerIn->meal->food_id
            ]);

            $seat_price = FlightSeatPrice::where('flight_seat_id', $pass->flight_seat_id)
                                          ->first();
            $total = $booking->total + ($seat_price->price * 0.1);
            $booking->update(["total"=> $total]);
          }else{
            $meal= Meal::where('passenger_id', $pass->id)->first();
            $meal->drink_id = $passengerIn->meal->drink_id;
            $meal->food_id = $passengerIn->meal->food_id;
            $meal->save();
          }
          $pass->save();
        }
      }

       return response()->json([
          'code' => '200',
          'erro' => false,
          'booking' => $booking
       ]);
  }
}
