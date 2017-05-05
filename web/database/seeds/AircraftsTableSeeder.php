<?php

use Illuminate\Database\Seeder;
use App\Aircraft;
use App\AircraftManufacturer;
use App\AircraftSeat;
use App\TravelClass;
class AircraftsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      $airport_manufacturer = AircraftManufacturer::pluck('id')->random();
      AircraftSeat::truncate();
      Aircraft::truncate();
      $first_travel_class = TravelClass::where('name', 'First')->first();
      $business_travel_class = TravelClass::where('name', 'Business')->first();
      $economy_travel_class = TravelClass::where('name', 'Economy')->first();
      $aircrafts = [
        [
          'model' => 'A340-600',
          'number_of_seats' => 317,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A319-100',
          'number_of_seats' => 120,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A340-300',
          'number_of_seats' => 253,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A330-300',
          'number_of_seats' => 249,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A330-200',
          'number_of_seats' => 222,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A320-200',
          'number_of_seats' => 138,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'B737-800',
          'number_of_seats' => 157,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ]
      ];

      foreach ($aircrafts as $key => $air) {
        $aircraft = Aircraft::create([
          'model' => $air['model'],
          'number_of_seats' => $air['number_of_seats'],
          'aircraft_manufaturer_id' => $air['aircraft_manufaturer_id'],
        ]);

        $first_class_number = 10;
        for ($x=1; $x <= $first_class_number; $x++){
          AircraftSeat::create([
            'aircraft_id' => $aircraft->id,
            'number' =>  $x . '-' . $aircraft->model,
            'travel_class_id' => $first_travel_class->id,
          ]);
        }

        $business_class_number = $first_class_number + 10;
        for ($x=11; $x <= $business_class_number; $x++){
          AircraftSeat::create([
            'aircraft_id' => $aircraft->id,
            'number' =>  $x . '-' . $aircraft->model,
            'travel_class_id' => $business_travel_class->id,
          ]);
        }

        for ($x= 21; $x <= $aircraft->number_of_seats ; $x++){
          AircraftSeat::create([
            'aircraft_id' => $aircraft->id,
            'number' =>  $x . '-' . $aircraft->model,
            'travel_class_id' => $economy_travel_class->id,
          ]);
        }
      }
    }
}
