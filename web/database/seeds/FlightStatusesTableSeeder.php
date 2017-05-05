<?php

use Illuminate\Database\Seeder;
use App\FlightStatus;
class FlightStatusesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      FlightStatus::truncate();
      $statuses = [
        'On-Time',
        'On-Course',
        'Delayed',
        'Pending',
        'Arrived',
        'Desembarking',
        'Embarking'
      ];
      for($x=0; $x< count($statuses); $x++){
        FlightStatus::create([
          'name' => $statuses[$x]
        ]);
      }
    }
}
