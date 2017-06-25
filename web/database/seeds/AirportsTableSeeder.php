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
      $description=  'O.R. Tambo International Airport (ORTIA) in Johannesburg ';
      $description.= 'is the air transport hub of Southern Africa, catering for ';
      $description.= 'more than 20 million passengers each year. With more than ';
      $description.= '18,000 people employed by various companies at ORTIA, the ';
      $description.= 'airport plays a vital role in the city’s and Gauteng ';
      $description.= 'province’s economy, and boasts an impressive infrastructure';
      $description.= 'that has expanded by thousands of square metres from its ';
      $description.= 'modest origins.';

      Airport::create([
        'name' => 'O.R. Tambo International Airport',
        'iata_airport_code' => 'JNB',
        'city' => 'Johannesburg',
        'image' => '/uploads/images/jnb.jpg',
        'description' => $description,
        'country_id' => $country_id
      ]);

      $description=  'Cape Town International Airport is the 2nd largest ';
      $description.= 'airport in the ACSA network and Africa’s 3rd largest airport. It is ';
      $description.= 'also Africa’s premier tourist and VIP destination and has established a ';
      $description.= 'reputation as Africa’s premier international award-winning airport, ';
      $description.= 'consistently performing among the best in the world for service';
      $description.= 'in its category.';

      Airport::create([
        'name' => 'Cape Town International Airport',
        'iata_airport_code' => 'CPT',
        'city' => 'Cape Town',
        'image' => '/uploads/images/cpt.jpg',
        'description' => $description,
        'country_id' => $country_id
      ]);

      $description = "";
      Airport::create([
        'name' => 'King Shaka International Airport',
        'iata_airport_code' => 'DUR',
        'city' => 'Durban',
        'image' => '/uploads/images/dur.JPG',
        'description' => $description,
        'country_id' => $country_id
      ]);
    }
}
