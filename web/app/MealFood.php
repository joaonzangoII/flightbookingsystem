<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class MealFood extends Model
{

  protected $fillable=[
    'passenger_id', 'food_id', 'meal_id'
  ];

  public function passenger(){
    return $this->belongsTo('App\Passenger', 'id', 'passenger_id');
  }

  public function food(){
    return $this->hasOne('App\Food', 'id', 'food_id');
  }
}
