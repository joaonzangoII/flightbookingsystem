<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Passenger extends Model
{
  protected $appends =['name'];
  protected $fillable = [
    'first_name', 'middle_name', 'last_name', 'id_number',
    'date_of_birth', 'gender', 'booking_id', 'flight_seat_id'
  ];

  public function meal(){
    return $this->hasOne('App\Meal');
  }

  public function booking(){
    return $this->belongsTo('App\Booking');
  }

  public function flight_seat(){
    return $this->hasOne('App\FlightSeat', 'id', 'flight_seat_id');
  }


  public function getNameAttribute()  {
    return $this->first_name . ' ' . $this->last_name;
    // return $this->first_name . (!is_null($this->first_name) ? $this->first_name . '') . ' ' . $this->last_name;
  }
}
