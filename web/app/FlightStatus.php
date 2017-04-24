<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class FlightStatus extends Model
{
  public function flights(){
    return $this->hasMAny('App\Flight');
  }
}
