<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class FlightSeatPrice extends Model
{
    protected $fillable = [
      'price',
      'flight_id',
      'aircraft_id',
      'flight_seat_id'
    ];
    public function flight(){
      return $this->hasOne('App\Flight', 'id', 'flight_seat_id');
    }

    public function flight_seat(){
      return $this->hasOne('App\FlightSeat', 'id', 'flight_seat_id');
    }
}
