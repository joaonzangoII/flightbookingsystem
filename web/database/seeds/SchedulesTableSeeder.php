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
      Schedule::create([
        'origin_airport_id' => 1,
        'destination_airport_id' => 2,
        'departure_time' => new \DateTime(),
        'arrival_time' => new \DateTime()
      ]);

      Schedule::create([
        'origin_airport_id' => 1,
        'destination_airport_id' => 3,
        'departure_time' => new \DateTime(),
        'arrival_time' => new \DateTime()
      ]);


      Schedule::create([
        'origin_airport_id' => 2,
        'destination_airport_id' => 3,
        'departure_time' => new \DateTime(),
        'arrival_time' => new \DateTime()
      ]);
    }
}
