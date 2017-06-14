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
         '2017-06-10',
         '2017-06-11',
         '2017-06-12',
         '2017-06-13',
         '2017-06-14',
         '2017-06-15',
         '2017-06-16',
         '2017-06-17',
         '2017-06-18',
         '2017-06-19',
         '2017-06-20',
         '2017-06-20',
         '2017-06-21',
         '2017-06-22',
         '2017-06-23',
         '2017-06-23',
         '2017-06-24',
         '2017-06-24',
         '2017-06-25',
         '2017-06-26',
         '2017-06-26',
         '2017-06-27',
         '2017-06-27'
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
