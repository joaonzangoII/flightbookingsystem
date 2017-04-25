<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Booking extends Model
{
    protected $fillable = [
      'return', 'user_id','departure_flight_id',
      'return_flight_id','aircraft_id'
    ];

    public function departure_flight(){
      return $this->hasOne('App\Flight', 'id', 'departure_flight_id');
    }

    public function return_flight(){
      return $this->hasOne('App\Flight', 'id', 'return_flight_id');
    }

    public function passengers(){
      return $this->hasMany('App\Passenger');
    }

    public function aircraft(){
      return $this->hasOne('App\Aircraft', 'id', 'aircraft_id');
    }
}
