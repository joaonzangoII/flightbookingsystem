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
      $aircrafts = Aircraft::pluck('id')->random();
      $flight_status = FlightStatus::where('name', 'Pending')->first();
      Flight::truncate();
      Flight::create([
        'aircraft_id' => $aircrafts,
        'flight_status_id' => $flight_status->id
      ]);

      $aircrafts = Aircraft::pluck('id')->random();
      Flight::create([
        'aircraft_id' => $aircrafts,
        'flight_status_id' => $flight_status->id
      ]);

      $aircrafts = Aircraft::pluck('id')->random();
      Flight::create([
        'aircraft_id' => $aircrafts,
        'flight_status_id' => $flight_status->id
      ]);

      $aircrafts = Aircraft::pluck('id')->random();
      Flight::create([
        'aircraft_id' => $aircrafts,
        'flight_status_id' => $flight_status->id
      ]);

      $aircrafts = Aircraft::pluck('id')->random();
      Flight::create([
        'aircraft_id' => $aircrafts,
        'flight_status_id' => $flight_status->id
      ]);
    }
}
