<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Booking extends Model
{
    protected $fillable = [
      'booking_number', 'return', 'user_id','departure_flight_id',
      'status','return_flight_id','aircraft_id', 'subtotal', 'total'
    ];

    public function user(){
      return $this->hasOne('App\User', 'id', 'user_id');
    }

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

    public function getShowLinkAttribute(){
      return route("backoffice.bookings.show", $this->id);
    }

    public function getEditLinkAttribute(){
      return route("backoffice.bookings.edit", $this->id);
    }
}
