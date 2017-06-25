<?php

use Illuminate\Database\Seeder;
use App\Drink;
class DrinksTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      Drink::truncate();
      Drink::create([
        'name' => 'Coca Cola',
        'image' => '/images/drinks/coke.png',
      ]);

      Drink::create([
        'name' => 'Coca Cola Light',
        'image' => '/images/drinks/cocaLight.png',
      ]);

      Drink::create([
        'name' => 'Coca Cola Zero',
        'image' => '/images/drinks/cokeZero.png',
      ]);

      Drink::create([
        'name' => 'Tab',
        'image' => '/images/drinks/tab.png',
      ]);

      Drink::create([
        'name' => 'Fanta Orange',
        'image' => '/images/drinks/fanta.png',
      ]);

      Drink::create([
        'name' => 'Sprite',
        'image' => '/images/drinks/sprite.png',
      ]);

      Drink::create([
        'name' => 'SpriteZero',
        'image' => '/images/drinks/spriteZero.png',
      ]);

      Drink::create([
        'name' => 'Stoney',
        'image' => '/images/drinks/stoney.png',
      ]);

      Drink::create([
        'name' => 'Schweppes Dry Lemon',
        'image' => '/images/drinks/schweppesDryLemonDryLemon.png',
      ]);

      Drink::create([
        'name' => 'Spar-Letta -  Creme Soda',
        'image' => '/images/drinks/sparletta.png',
      ]);

      Drink::create([
        'name' => 'Twist Lemon',
        'image' => '/images/drinks/twistLemon.png',
      ]);

      Drink::create([
        'name' => 'Iron Brew',
        'image' => '/images/drinks/ironBrew.png',
      ]);

      Drink::create([
        'name' => 'Fuze Tea',
        'image' => '/images/drinks/fuzeTeav2.png',
      ]);

      Drink::create([
        'name' => 'Mazoe Orange',
        'image' => '/images/drinks/mazoe.png',
      ]);

      Drink::create([
        'name' => 'Just Juice Orange',
        'image' => '/images/drinks/justJuice.png',
      ]);

      Drink::create([
        'name' => 'Minute Maid',
        'image' => '/images/drinks/minutemaid.png',
      ]);

      Drink::create([
        'name' => 'Vitamin Water',
        'image' => '/images/drinks/glaceau.png',
      ]);

      Drink::create([
        'name' => 'Powerade',
        'image' => '/images/drinks/powerade.png',
      ]);

      Drink::create([
        'name' => 'Valpre Still Water',
        'image' => '/images/drinks/valpre.png',
      ]);

      Drink::create([
        'name' => 'Bonaqua Pump Water',
        'image' => '/images/drinks/bonaquaPump.png',
      ]);

      Drink::create([
        'name' => 'Bonaqua Plain Still',
        'image' => '/images/drinks/bonaquaLarge.png',
      ]);
    }
}
