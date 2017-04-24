<?php

use Illuminate\Database\Seeder;
use App\Aircraft;
use App\AircraftManufacturer;
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
      Aircraft::truncate();
      Aircraft::create([
        'model' => 'A340-600',
        'number_of_seats' => 317,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);

      Aircraft::create([
        'model' => 'A340-300',
        'number_of_seats' => 253,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);

      Aircraft::create([
        'model' => 'A330-300',
        'number_of_seats' => 249,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);

      Aircraft::create([
        'model' => 'A330-200',
        'number_of_seats' => 222,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);

      Aircraft::create([
        'model' => 'B737-800',
        'number_of_seats' => 157,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);

      Aircraft::create([
        'model' => 'A320-200',
        'number_of_seats' => 138,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);

      Aircraft::create([
        'model' => 'A319-100',
        'number_of_seats' => 120,
        'aircraft_manufaturer_id' => $airport_manufacturer,
      ]);
    }
}
