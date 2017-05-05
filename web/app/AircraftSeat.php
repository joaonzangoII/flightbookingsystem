<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class AircraftSeat extends Model
{
  public function travel_class(){
    return $this->hasOne('App\TravelClass', 'id', 'travel_class_id');
  }
}
