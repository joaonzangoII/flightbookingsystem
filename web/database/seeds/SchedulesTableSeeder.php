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
      $dates = [
         '2017-05-20',
         '2017-05-21',
         '2017-05-22',
         '2017-05-23',
         '2017-05-23',
         '2017-05-24',
         '2017-05-24',
         '2017-05-25',
         '2017-05-26',
         '2017-05-26',
         '2017-05-27',
         '2017-05-27'
      ];

      $flights = Flight::with('flight_status', 'aircraft', 'aircraft.aircraft_seats')->get();
      foreach ($flights as $key=> $flight) {
        $date =  $dates[random_int(0, count($dates)-1)];
        $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
        $departure_date = $departure_date->format('Y-m-d H:i:s');
        $hours = random_int(1,4);
        $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + $hours * 3600);
        Schedule::create([
          'departure_time' => $departure_date,
          'arrival_time' =>   $arrival_date,
          'origin_airport_id' => 1,
          'destination_airport_id' => 2,
          'date' => $date,
          'flight_id' => $flight->id
        ]);
      }
    }
}
