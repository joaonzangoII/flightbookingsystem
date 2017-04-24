<?php

use Illuminate\Database\Seeder;
use App\Schedule;

class SchedulesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      Schedule::truncate();
      // Oliver Reginald Tambo International Airport
      // Cape Town International Airport
      // Durban International Airport
      $departure_date = new DateTime("2017-04-22", new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      // $arrival_date = date("Y-m-d H:i:s", strtotime('+2 hours +10 minutes', ($departure_date)));
      Schedule::create([
        'departure_time' => $departure_date,
        'arrival_time' =>   $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 2,
        'date' => "2017-04-22",
        'flight_id' => 1
      ]);

      $departure_date = new DateTime("2017-04-25", new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 1*60*60);
      Schedule::create([
        'departure_time' => $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 3,
        'date' => "2017-04-25",
        'flight_id' => 1,
      ]);

      $departure_date = new DateTime("2017-04-26", new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      Schedule::create([
        'departure_time' =>  $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 2,
        'destination_airport_id' => 3,
        'date' => "2017-04-26",
        'flight_id' => 1,
      ]);
    }
}
