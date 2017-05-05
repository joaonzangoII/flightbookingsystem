<?php
use Illuminate\Database\Seeder;
use App\FlightSeatPrice;
use App\FlightSeat;
use App\Aircraft;
use App\Flight;
use App\TravelClass;
class FlightSeatPricesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      FlightSeatPrice::truncate();
      $first_travel_class = TravelClass::where('name', 'First')->first();
      $business_travel_class = TravelClass::where('name', 'Business')->first();
      $economy_travel_class = TravelClass::where('name', 'Economy')->first();
      $flights = Flight::with('flight_status', 'aircraft', 'aircraft.aircraft_seats')->get();
      foreach ($flights as $key=> $flight) {
        $aircraft = Aircraft::with('aircraft_seats')->findOrFail($flight->aircraft_id);
        foreach ($aircraft->aircraft_seats as $key => $aircraft_seat) {
          $flight_seat  = FlightSeat::create([
            'aircraft_id' => $aircraft_seat->aircraft_id,
            'aircraft_seat_id' => $aircraft_seat->id,
            'number' =>  $aircraft_seat->number,
            'travel_class_id' => $aircraft_seat->travel_class_id,
            'flight_id' => $flight->id,
          ]);

          if($aircraft_seat->travel_class_id == $first_travel_class->id){
            $price = 3000;
          }elseif($aircraft_seat->travel_class_id == $business_travel_class->id){
            $price = 1500;
          }else{
            $price = 500;
          }
          $seat_price = FlightSeatPrice::create([
            'price' => $price,
            'flight_id' => $flight->id,
            'aircraft_id' => $aircraft_seat->aircraft_id,
            'flight_seat_id' => $flight_seat->id
          ]);
        }
      }
    }
}
