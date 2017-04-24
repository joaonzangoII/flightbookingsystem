<?php

use Illuminate\Database\Seeder;
use App\TravelClass;
class TravelClassesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      TravelClass::truncate();
      TravelClass::create([
        'name' => 'First',
        'description' => 'First'
      ]);

      TravelClass::create([
        'name' => 'Business',
        'description' => 'Business'
      ]);

      TravelClass::create([
        'name' => 'Economy',
        'description' => 'Economy'
      ]);
    }
}
