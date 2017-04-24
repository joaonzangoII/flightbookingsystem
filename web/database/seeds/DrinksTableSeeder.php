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
        'image' => 'http://www.coca-cola.co.za/images/brands/coke.png',
      ]);

      Drink::create([
        'name' => 'Coca Cola Light',
        'image' => 'http://www.coca-cola.co.za/images/brands/cocaLight.png',
      ]);

      Drink::create([
        'name' => 'Coca Cola Zero',
        'image' => 'http://www.coca-cola.co.za/images/brands/cokeZero.png',
      ]);

      Drink::create([
        'name' => 'Tab',
        'image' => 'http://www.coca-cola.co.za/images/brands/tab.png',
      ]);

      Drink::create([
        'name' => 'Fanta Orange',
        'image' => 'http://www.coca-cola.co.za/images/brands/fanta.png',
      ]);

      Drink::create([
        'name' => 'Sprite',
        'image' => 'http://www.coca-cola.co.za/images/brands/sprite.png',
      ]);

      Drink::create([
        'name' => 'SpriteZero',
        'image' => 'http://www.coca-cola.co.za/images/brands/spriteZero.png',
      ]);

      Drink::create([
        'name' => 'Stoney',
        'image' => 'http://www.coca-cola.co.za/images/brands/stoney.png',
      ]);

      Drink::create([
        'name' => 'Schweppes Dry Lemon',
        'image' => 'http://www.coca-cola.co.za/images/brands/schweppesDryLemonDryLemon.png',
      ]);

      Drink::create([
        'name' => 'Spar-Letta -  Creme Soda',
        'image' => 'http://www.coca-cola.co.za/images/brands/sparletta.png',
      ]);

      Drink::create([
        'name' => 'Twist Lemon',
        'image' => 'http://www.coca-cola.co.za/images/brands/twistLemon.png',
      ]);

      Drink::create([
        'name' => 'Iron Brew',
        'image' => 'http://www.coca-cola.co.za/images/brands/ironBrew.png',
      ]);

      Drink::create([
        'name' => 'Fuze Tea',
        'image' => 'http://www.coca-cola.co.za/images/brands/fuzeTeav2.png',
      ]);

      Drink::create([
        'name' => 'Mazoe Orange',
        'image' => 'http://www.coca-cola.co.za/images/brands/mazoe.png',
      ]);

      Drink::create([
        'name' => 'Just Juice Orange',
        'image' => 'http://www.coca-cola.co.za/images/brands/justJuice.png',
      ]);

      Drink::create([
        'name' => 'Minute Maid',
        'image' => 'http://www.coca-cola.co.za/images/brands/minutemaid.png',
      ]);

      Drink::create([
        'name' => 'Vitamin Water',
        'image' => 'http://www.coca-cola.co.za/images/brands/glaceau.png',
      ]);

      Drink::create([
        'name' => 'Powerade',
        'image' => 'http://www.coca-cola.co.za/images/brands/powerade.png',
      ]);

      Drink::create([
        'name' => 'Valpre Still Water',
        'image' => 'http://www.coca-cola.co.za/images/brands/valpre.png',
      ]);

      Drink::create([
        'name' => 'Bonaqua Pump Water',
        'image' => 'http://www.coca-cola.co.za/images/brands/bonaquaPump.png',
      ]);

      Drink::create([
        'name' => 'Bonaqua Plain Still',
        'image' => 'http://www.coca-cola.co.za/images/brands/bonaquaLarge.png',
      ]);
    }
}
