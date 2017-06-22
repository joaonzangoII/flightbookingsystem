<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class MealDrink extends Model
{

  protected $fillable=[
    'passenger_id', 'drink_id', 'meal_id'
  ];

  public function passenger(){
    return $this->belongsTo('App\Passenger', 'id', 'passenger_id');
  }
  public function drink(){
    return $this->hasOne('App\Drink', 'id', 'drink_id');
  }
}
