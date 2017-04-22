<?php

use Illuminate\Database\Seeder;
use App\Country;
class CountriesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        Country::truncate();

        Country::create([
          'name' => 'South Africa',
          'iata_country_code' => 'ZA'
        ]);
    }
}
