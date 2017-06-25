<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Flight extends Model
{
  protected $fillable=['aircraft_id', 'flight_status_id'];

  public function flight_status(){
    return $this->hasOne('App\FlightStatus', 'id', 'flight_status_id');
  }

  public function aircraft(){
    return $this->hasOne('App\Aircraft', 'id', 'aircraft_id');
  }

  public function schedule(){
    return $this->hasOne('App\Schedule');
  }

  public function flight_seat(){
    return $this->hasMany('App\FlightSeat');
  }

  public function flight_seat_prices(){
    return $this->hasMany('App\FlightSeatPrice');
  }

}
