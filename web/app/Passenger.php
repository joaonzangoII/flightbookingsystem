<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Passenger extends Model
{
  protected $appends =[
                       'name',
                       'drink_id',
                       'food_id',
                       'food_and_drink'
                     ];
  protected $fillable = [
    'firstnames',
    'surname',
    'last_name',
    'id_number',
    'date_of_birth',
    'gender',
    'booking_id',
    'flight_seat_id'
  ];

  public function drink()
  {
    return $this->hasOne('App\MealDrink');
  }

  public function food()
  {
    return $this->hasOne('App\MealFood');
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
  }

  public function getFoodAndDrinkAttribute()
  {
    return "hello";
    return is_null($this->meal)
    ? null
    : $this->food->food->name
    . ' with '
    . $this->drink->drink->name;
  }

  public function getDrinkIdAttribute()
  {
    if(is_null($this->drink));
    {
      return null;
    }

    return $this->drink->drink->id;
  }

  public function getFoodIdAttribute()
  {
    if(is_null($this->food));
    {
      return null;
    }

    return $this->food->food->id;
  }
}
