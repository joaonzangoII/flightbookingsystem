<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Passenger extends Model
{
  protected $appends =['name'];
  protected $fillable = [
    'first_name', 'middle_name', 'last_name', 'id_number',
    'date_of_birth', 'gender', 'booking_id', 'aircraft_seat_id'
  ];

  public function meal(){
    return $this->hasOne('App\Meal');
  }

  public function bookings(){
    return $this->hasMany('App\Booking');
  }

  public function aircraft_seat(){
    return $this->hasOne('App\AircraftSeat', 'id', 'aircraft_seat_id');
  }


  public function getNameAttribute()  {
    return $this->first_name . ' ' . $this->last_name;
  }
}
