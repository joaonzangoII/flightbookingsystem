<?php

use Illuminate\Database\Seeder;
use App\Schedule;
use App\Flight;

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
      $date = '2017-04-22';
      $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      // $arrival_date = date("Y-m-d H:i:s", strtotime('+2 hours +10 minutes', ($departure_date)));
      Schedule::create([
        'departure_time' => $departure_date,
        'arrival_time' =>   $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 2,
        'date' => $date,
        'flight_id' => 1
      ]);

      $date = '2017-04-25';
      $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 1*60*60);
      Schedule::create([
        'departure_time' => $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 3,
        'date' => $date,
        'flight_id' => 2,
      ]);

      $date = '2017-04-26';
      $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      Schedule::create([
        'departure_time' =>  $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 2,
        'destination_airport_id' => 3,
        'date' => $date,
        'flight_id' => 3,
      ]);

      $date = '2017-05-04';
      $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      Schedule::create([
        'departure_time' =>  $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 2,
        'date' => $date,
        'flight_id' => 4,
      ]);

      $date = '2017-05-05';
      $departure_date = new DateTime("2017-05-05", new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      Schedule::create([
        'departure_time' =>  $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 2,
        'date' => $date,
        'flight_id' => 5,
      ]);

      $date = '2017-05-04';
      $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
      $departure_date = $departure_date->format('Y-m-d H:i:s');
      $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + 2*60*60);
      Schedule::create([
        'departure_time' =>  $departure_date,
        'arrival_time' =>  $arrival_date,
        'origin_airport_id' => 1,
        'destination_airport_id' => 2,
        'date' => $date,
        'flight_id' => 6,
      ]);
    }
}
