<?php

use Illuminate\Database\Seeder;
use App\UserType;
class UserTypesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      UserType::truncate();
      UserType::create([
        'name' => 'Administrator',
        'description' => 'System Administrator'
      ]);

      UserType::create([
        'name' => 'Customer',
        'description' => 'Normal User of the system'
      ]);
    }
}
