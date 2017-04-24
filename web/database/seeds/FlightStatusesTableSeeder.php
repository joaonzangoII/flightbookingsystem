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
      FlightStatus::create([
        'name' => 'On-Time',
      ]);

      FlightStatus::create([
        'name' => 'On-Course',
      ]);

      FlightStatus::create([
        'name' => 'Delayed',
      ]);

      FlightStatus::create([
        'name' => 'Pending',
      ]);

      FlightStatus::create([
        'name' => 'Arrived',
      ]);

      FlightStatus::create([
        'name' => 'Desembarking',
      ]);

      FlightStatus::create([
        'name' => 'Embarking',
      ]);
    }
}
