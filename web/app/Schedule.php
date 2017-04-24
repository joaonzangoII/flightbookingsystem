<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Schedule extends Model
{
    protected $appends = ['origin_airport_name', 'destination_airport_name', 'duration'];
    public function flight(){
      return $this->belongsTo('App\Flight');
    }

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

    public function getDurationAttribute($icone){
      $departure_time=strtotime( $this->departure_time);
      $arrival_time = strtotime( $this->arrival_time);
      $diff = $arrival_time - $departure_time;
      return ($diff/(60*60)%24) . ' hours';

      // $departure_time= new \DateTime( $this->departure_time, new \DateTimeZone('Africa/Johannesburg'));
      // $arrival_time = new \DateTime( $this->arrival_time, new \DateTimeZone('Africa/Johannesburg'));
      // return  $departure_time->diff($arrival_time) ;
    }
}
