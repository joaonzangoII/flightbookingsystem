<?php

use Illuminate\Database\Seeder;
use App\AircraftManufacturer;
class AircraftManufacturersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      AircraftManufacturer::truncate();
      AircraftManufacturer::create([
        'name' => 'Airbus',
      ]);
    }
}
