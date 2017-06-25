<?php

use Illuminate\Database\Seeder;
use App\Food;
use App\FoodType;
class FoodsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
      $normal_food = FoodType::where('name', 'normal')->first();
      $vegetarian_food_id = FoodType::where('name', 'vegetarian')->first();
      Food::truncate();
      $description  = "Traditional Pap & Vleis is a combination of Mzantsi’s Boerewors and Mealie meal.";
      $description .= "This is the perfect diversity meal as the ingredients used in this dish represent all the South African cultures.";
      $description .= "Mealie Meal also well known as “Pap” in many parts of Mzantsi and is a traditional porridge made from ground maize.";
      $description .= "Boerewors is a traditional Mzantsi sausage. The name Boerewors comes from the Afrikaans words Boer (farmer) Wors (sausage).";
      Food::create([
        'name' => 'Pap n Vleis',
        'image' => '/images/foods/cape-town-menu.jpg',
        'description' => $description,
        'food_type_id' => $normal_food->id
      ]);

      $desciption   = "This meal, but rather tender lamb, mutton, chicken or vegetables, in a spicy gravy.";
      $description .= "The bunny chow is a firm favourite in Durban (east coast of South Africa).";
      $description .= "Visit Durban’s famous Blue Lagoon and pop in to Danny’s House of Curries (three-time winner of the Coca-Cola Bunny Chow Barometer Challenge) to give your taste buds a bit of a tingle.";

      Food::create([
        'name' => 'Bunny chow',
        'image' => '/images/foods/Image_1_Bunny_chow_640_480_80auto_s.jpg',
        'description' => $description,
        'food_type_id' => $normal_food->id
      ]);

      $description =  "Whenever you’re at a braai (barbecue for our foreign friends), you have to try this simple yet tasty meal.";
      $description .= "Boerewors is an Afrikaans (one of South Africa\'s many languages) term used describe a sausage, and this dish is accompanied with";
      $description .= "traditional relish or chakalaka as we refer to it.";
      $description .= "The relish consists of various ingredients including chopped peppers, tomatoes,onions and most importantly - curry powder.";
      Food::create([
        'name' => 'Boerewors with chakalaka',
        'image' => '/images/foods/Image_2_Borewors_and_chakalaka_640_853_80auto_s.jpg',
        'description' => $description,
        'food_type_id' => $normal_food->id
      ]);

      $description  = "Potjiekos (a word derived from Afrikaans) is a small pot (made from cast iron) of food.";
      $description .= "It’s a stew cooked over coals and there’s always an excuse to make a potjie - be it a chilly winter’s evening or a warm afternoon.";
      $description .= "It’s important you have the perfect size pot to ensure all your guests have a decent serving.";
      $description .= "Chances are they’ll want a second helping - it’s that good!";
      Food::create([
        'name' => 'Potjiekos',
        'image' => '/images/foods/image_3_potjie_kos_640_427_80auto_s.JPG',
        'description' => $description,
        'food_type_id' => $normal_food->id
      ]);

      Food::create([
        'name' => 'Cape Malay curry',
        'image' => '/images/foods/Image_6_Malay_curry_640_338_80auto_s.jpg',
        'description' => "A dish popular in the Western Cape, this curry is rich in aroma, spices (including turmeric and saffron) and is enjoyed by locals including the Muslim community.",
        'food_type_id' => $normal_food->id
      ]);

      $description  = "More of a snack than a meal, biltong is dried and cured meat.";
      $description .= "From beef and game meat, such as kudu and ostrich, to the less popular chicken option, biltong is enjoyed at braais (shisanyama) and sporting events.";
      Food::create([
        'name' => 'Biltong',
        'image' => '/images/foods/Image_7_Biltong_640_453_80auto_s.jpg',
        'description' => $description,
        'food_type_id' => $normal_food->id
      ]);
    }
}
