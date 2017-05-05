<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class FlightSeatPrice extends Model
{
    protected $fillable = [
      'price',
      'flight_id',
      'aircraft_id',
      'aircraft_seat_id'
    ];
}
