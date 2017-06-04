<?php

use Illuminate\Database\Seeder;
use App\User;
use App\UserType;
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
      $user_type_admin = UserType::where('name', 'Administrator')->first();
      $user_type_normal = UserType::where('name', 'Customer')->first();
      $faker = Faker\Factory::create();
      $user = User::create([
        'firstnames' => 'System',
        'surname' => 'Admin',
        'phone' => '27769622448',
        'id_number' => '0000000000000',
        'email' => 'admin@flightbookingsystem.com',
        'country_id' => $country_id,
        'user_type_id' => $user_type_admin->id,
        'password' => $password ?: $password = bcrypt('secret'),
        'remember_token' => str_random(10)
      ]);

      $user = User::create([
        'firstnames' => 'Oscar',
        'surname' => 'Mahlompopane',
        'phone' => '27769622448',
        'id_number' => '1234567890123',
        'email' => 'mahlompopaneo@gmail.com',
        'country_id' => $country_id,
        'user_type_id' => $user_type_normal->id,
        'password' => $password ?: $password = bcrypt('secret'),
        'remember_token' => str_random(10)
      ]);
    }
}
