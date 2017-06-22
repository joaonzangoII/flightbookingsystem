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
          'number_of_seats' => 150,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A319-100',
          'number_of_seats' => 120,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A340-300',
          'number_of_seats' => 100,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A330-300',
          'number_of_seats' => 90,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A330-200',
          'number_of_seats' => 85,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'A320-200',
          'number_of_seats' => 90,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ],
        [
          'model' => 'B737-800',
          'number_of_seats' => 110,
          'aircraft_manufaturer_id' => $airport_manufacturer,
        ]
      ];

      foreach ($aircrafts as $key => $air) {
        $aircraft = Aircraft::create([
          'model' => $air['model'],
          'number_of_seats' => $air['number_of_seats'],
          'aircraft_manufaturer_id' => $air['aircraft_manufaturer_id'],
        ]);

        $subtotal_seat_number = 0;
        $first=[];
        $business=[];
        $economy=[];
        $number_of_seats = $aircraft->number_of_seats;
        $seats_num = 12;
        for ($x=1; $x <= $seats_num; $x++){
          $number = $x;
          $first[]= $number;
          AircraftSeat::create([
            'aircraft_id' => $aircraft->id,
            'number' => $number,
            'travel_class_id' => $first_travel_class->id,
          ]);
        }

        $seats_num = $seats_num + 16;
        for ($x=1; $x <= $seats_num; $x++){
          $number = $first[count($first)-1] + $x;
          $business[]= $number;
          AircraftSeat::create([
            'aircraft_id' => $aircraft->id,
            'number' => $number,
            'travel_class_id' => $business_travel_class->id,
          ]);
        }

        $subtotal_seat_number = count($first) + count($business);
        $seats_num = $number_of_seats - $subtotal_seat_number;
        for ($x= 1 ; $x <= $seats_num ; $x++){
          $number= $business[count($business)-1] + $x;
          $economy[]=  $number;
            AircraftSeat::create([
              'aircraft_id' => $aircraft->id,
              'number' => $number,
              'travel_class_id' => $economy_travel_class->id,
            ]);
        }
      }
    }
}
