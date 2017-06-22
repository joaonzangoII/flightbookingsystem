<?php

use Illuminate\Database\Seeder;
use App\Schedule;
use App\Flight;
use App\Airport;

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
         '2017-06-23',
         '2017-06-23',
         '2017-06-24',
         '2017-06-24',
         '2017-06-25',
         '2017-06-26',
         '2017-06-26',
         '2017-06-27',
         '2017-06-27',
         '2017-06-28',
         '2017-06-29',
         '2017-06-30'
      ];

      $airports_count = Airport::get()->count();
      $flights = Flight::with('flight_status',
                              'aircraft',
                              'aircraft.aircraft_seats')
                       ->get();
      foreach ($flights as $key=> $flight) {
        $date =  $dates[random_int(0, count($dates)-1)];
        $departure_date = new DateTime($date, new DateTimeZone('Africa/Johannesburg'));
        $departure_date = $departure_date->format('Y-m-d H:i:s');
        $hours = random_int(1,4);
        $arrival_date =  date("Y-m-d H:i:s", strtotime($departure_date) + $hours * 3600);

        $r  = random_int(1, $airports_count);
        $r2 = random_int(1, $airports_count);
        $r2 = $r!==$r2 ? $r2 : (($r || $r === $airports_count)
        ? $r==1  ?  $r + 1  : $r-1
        : $r==$airports_count ?$r-1 : $r+1);
        Schedule::create([
          'departure_time'         => $departure_date,
          'arrival_time'           => $arrival_date,
          'origin_airport_id'      => $r,
          'destination_airport_id' => $r2,
          'date'                   => $date,
          'flight_id'              => $flight->id
        ]);
      }
    }
}
