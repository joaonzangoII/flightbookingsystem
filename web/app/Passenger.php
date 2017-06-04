<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Passenger extends Model
{
  protected $appends =['name', 'food_and_drink'];
  protected $fillable = [
    'firstnames', 'surname', 'last_name', 'id_number',
    'date_of_birth', 'gender', 'booking_id', 'flight_seat_id'
  ];

  public function meal()
  {
    return $this->hasOne('App\Meal');
  }

  public function booking()
  {
    return $this->belongsTo('App\Booking');
  }

  public function flight_seat()
  {
    return $this->hasOne('App\FlightSeat', 'id', 'flight_seat_id');
  }


  public function getNameAttribute()
  {
    return $this->firstnames . ' ' . $this->surname;
    // return $this->first_name . (!is_null($this->first_name) ? $this->first_name . '') . ' ' . $this->last_name;
  }

  public function getFoodAndDrinkAttribute()
  {
    return is_null($this->meal) ? null : $this->meal->food->name . ' with ' . $this->meal->drink->name;
  }
}
