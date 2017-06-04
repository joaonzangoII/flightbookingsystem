<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Schedule extends Model
{
    protected $fillable=[
      'departure_time',
      'arrival_time',
      'origin_airport_id',
      'destination_airport_id',
      'date',
      'flight_id'
    ];

    protected $dates=['departure_time', 'arrival_time'];

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
      $departure_time= strtotime( $this->departure_time);
      $arrival_time =  strtotime( $this->arrival_time);
      $diff = $arrival_time - $departure_time;
      $years = floor($diff / (365*60*60*24));
      $months = floor(($diff - $years * 365*60*60*24) / (30*60*60*24));
      $days = floor(($diff - $years * 365*60*60*24 - $months*30*60*60*24)/ (60*60*24));
      $hours = floor(($diff/(60*60)%24));
      if($hours > 0 && $hours < 24){
        return $hours . ' hours';
      }else{
        return $days . ' days';
      }
      return printf("%d months, %d days, %d hours\n", $months, $days, $hours);
    }
}
