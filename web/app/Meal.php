<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Meal extends Model
{
  protected $fillable=[
    'passenger_id', 'drink_id', 'food_id'
  ];

  public function passenger(){
    return $this->belongsTo('App\Passenger', 'id', 'passenger_id');
  }

  public function food(){
    return $this->hasOne('App\Food', 'id', 'food_id');
  }

  public function drink(){
    return $this->hasOne('App\Drink', 'id', 'drink_id');
  }
}
