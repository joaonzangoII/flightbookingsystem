<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class AircraftSeat extends Model
{
  public function travel_class(){
    return $this->hasOne('App\TravelClass', 'id', 'travel_class_id');
  }

  public function seat_price(){
    return $this->hasOne('App\SeatPrice', 'id', 'aircraft_seat_id');
  }
}
