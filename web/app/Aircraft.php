<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Aircraft extends Model
{
  protected $appends =['name'];
  public function getNameAttribute()  {
    return $this->aircraft_manufacturer->name . ' ' . $this->model;
  }

  public function flights(){
    return $this->hasMany('App\Flight');
  }

  public function aircraft_manufacturer(){
    return $this->hasOne('App\AircraftManufacturer', 'id', 'aircraft_manufaturer_id');
  }
}
