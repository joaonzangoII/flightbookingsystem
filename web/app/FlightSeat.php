<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class FlightSeat extends Model
{
  public function travel_class(){
    return $this->hasOne('App\TravelClass', 'id', 'travel_class_id');
  }

  public function flight_seat_price(){
    return $this->hasOne('App\FlightSeatPrice', 'id', 'id');
  }

  public function getAvailableAttribute($value)
  {
    return (bool) $value;
  }
}
