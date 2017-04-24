<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Flight extends Model
{
  public function flight_status(){
    return $this->hasOne('App\FlightStatus', 'id', 'flight_status_id');
  }

  public function aircraft(){
    return $this->hasOne('App\Aircraft', 'id', 'aircraft_id');
  }
}
