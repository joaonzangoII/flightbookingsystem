<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class AircraftManufacturer extends Model
{
  public function aircrafts(){
    return $this->hasMany('App\Aircraft');
  }
}
