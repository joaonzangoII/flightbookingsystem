<?php
use Illuminate\Database\Seeder;
use App\SeatPrice;
use App\Flight;
use App\TravelClass;
class SeatPricesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      SeatPrice::truncate();
      $first_travel_class = TravelClass::where('name', 'First')->first();
      $business_travel_class = TravelClass::where('name', 'Business')->first();
      $economy_travel_class = TravelClass::where('name', 'Economy')->first();
      $flights = Flight::with('flight_status', 'aircraft', 'aircraft.aircraft_seats')->get();
      foreach ($flights as $key=> $flight) {
        foreach ($flight->aircraft->aircraft_seats as $keySeat=> $aircraft_seat) {
          if($aircraft_seat->travel_class_id == $first_travel_class->id){
            $price = 3000;
          }elseif($aircraft_seat->travel_class_id == $business_travel_class->id){
            $price = 1500;
          }else{
            $price = 500;
          }

          $seat_price = SeatPrice::create([
            'price' => $price,
            'flight_id' => $flight->id,
            'aircraft_id' => $aircraft_seat->aircraft_id,
            'aircraft_seat_id' => $aircraft_seat->id
          ]);
        }
      }
    }
}
