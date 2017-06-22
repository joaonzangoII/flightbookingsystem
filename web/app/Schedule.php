<?php

namespace App;
use carbon\carbon;

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
      return  !is_null($this->origin_airport)
      ? $this->origin_airport->name
      : null;
    }

    public function getDestinationAirportNameAttribute($icone){
      return  !is_null($this->destination_airport)
      ? $this->destination_airport->name
      : null;
    }

    /**
    * Scope a query to only include active users.
    *
    * @param \Illuminate\Database\Eloquent\Builder $query
    * @return \Illuminate\Database\Eloquent\Builder
    */
    public function scopeBookable($query)
    {
      return $query->where('departure_time',">=", Carbon::now());
    }

    public function getDurationAttribute($icone){
      $departure_time=  $this->departure_time;
      $arrival_time =   $this->arrival_time;
      $years  =  $arrival_time->diffInYears($departure_time);
      $months = $arrival_time->diffInMonths($departure_time);
      $days   =   $arrival_time->diffInDays($departure_time);
      $hours  =  $arrival_time->diffInHours($departure_time);
      if($hours > 0 && $hours < 24){
        return $hours . ' hours';
      }else{
        return $days . ' days';
      }
      return printf("%d months, %d days, %d hours\n", $months, $days, $hours);
    }
}
