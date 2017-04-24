<?php

use Illuminate\Database\Seeder;
use App\User;
use App\Country;
class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      User::truncate();
      // factory(User::class, 3)->create();
      static $password;
      $country_id = Country::pluck('id')->random();
      $faker = Faker\Factory::create();
      $user = User::create([
        'first_name' => 'Lulu',
        'middle_name' => '',
        'last_name' => 'Nthombu',
        'phone' => '0000000000',
        'id_number' => '',
        'email' => 'blah@gmail.com',
        'country_id' => $country_id,
        'user_type_id' => 1,
        'password' => $password ?: $password = bcrypt('secret'),
        'remember_token' => str_random(10)
      ]);
    }
}
