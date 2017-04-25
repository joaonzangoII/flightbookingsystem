<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Food extends Model
{
  public function food_type(){
    return $this->hasOne('App\FoodType', 'id', 'food_type_id');
  }
}
