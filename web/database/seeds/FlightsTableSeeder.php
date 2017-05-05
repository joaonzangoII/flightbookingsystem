<?php

use Illuminate\Database\Seeder;
use App\Aircraft;
use App\FlightStatus;
use App\Flight;
class FlightsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      $aircrafts = Aircraft::all();
      $flight_status = FlightStatus::where('name', 'Pending')->first();
      Flight::truncate();
      foreach($aircrafts as $key=>$aircraft){
        Flight::create([
          'aircraft_id' => $aircraft->id,
          'flight_status_id' => $flight_status->id
        ]);
      }
    }
}
