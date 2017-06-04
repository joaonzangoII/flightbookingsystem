<?php

use Illuminate\Database\Seeder;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {

        Model::unguard();
        DB::statement("SET FOREIGN_KEY_CHECKS = 0;");
        $this->call(CountriesTableSeeder::class);
        $this->call(UserTypesTableSeeder::class);
        $this->call(UsersTableSeeder::class);
        $this->call(AirportsTableSeeder::class);
        $this->call(FlightStatusesTableSeeder::class);
        $this->call(TravelClassesTableSeeder::class);
        $this->call(AircraftManufacturersTableSeeder::class);
        $this->call(AircraftsTableSeeder::class);
        $this->call(DrinksTableSeeder::class);
        $this->call(FoodTypesTableSeeder::class);
        $this->call(FoodsTableSeeder::class);
        $this->call(FlightsTableSeeder::class);
        $this->call(SchedulesTableSeeder::class);
        $this->call(FlightSeatPricesTableSeeder::class);
        DB::statement("SET FOREIGN_KEY_CHECKS = 1;");
        Model::reguard();
    }
}
