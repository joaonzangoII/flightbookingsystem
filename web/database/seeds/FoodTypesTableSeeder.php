<?php

use Illuminate\Database\Seeder;
use App\FoodType;
class FoodTypesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      FoodType::truncate();
      FoodType::create([
        'name' => 'normal',
      ]);

      FoodType::create([
        'name' => 'vegetarian',
      ]);
    }
}
