<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Schedule extends Model
{
    protected $appends = ['origin_airport_name', 'destination_airport_name'];
    public function airport(){
      return $this->belongsTo('App\Airport');
    }

    public function origin_airport(){
      return $this->belongsTo('App\Airport');
    }

    public function destination_airport(){
      return $this->belongsTo('App\Airport');
    }

    public function getOriginAirportNameAttribute($icone){
      return $this->origin_airport->name;
    }

    public function getDestinationAirportNameAttribute($icone){
      return $this->destination_airport->name;
    }
}
