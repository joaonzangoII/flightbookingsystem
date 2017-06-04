<?php

use Illuminate\Database\Seeder;
use App\Airport;
use App\Country;

class AirportsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      $country_id = Country::pluck('id')->random();
      Airport::truncate();
      Airport::create([
        'name' => 'O.R. Tambo International Airport',
        'iata_airport_code' => 'JNB',
        'city' => 'Johannesburg',
        'country_id' => $country_id
      ]);

      Airport::create([
        'name' => 'Cape Town International Airport',
        'iata_airport_code' => 'CPT',
        'city' => 'Cape Town',
        'country_id' => $country_id
      ]);

      Airport::create([
        'name' => 'Durban International Airport',
        'iata_airport_code' => 'DUR',
        'city' => 'DURBAN',
        'country_id' => $country_id
      ]);
    }
}
