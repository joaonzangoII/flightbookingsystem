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
         '2017-05-05',
         '2017-05-06',
         '2017-05-07',
         '2017-05-08',
         '2017-05-09',
         '2017-05-10',
         '2017-05-11',
         '2017-05-12',
         '2017-05-13',
         '2017-05-14',
         '2017-05-15',
         '2017-05-16'
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
