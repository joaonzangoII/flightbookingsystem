<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Airport extends Model
{
    protected $fillable =[
      'name',
      'iata_airport_code',
      'city',
      'country_id'
    ];

    public function country(){
      return $this->belongsTo('App\Country');
    }

    public function getShowLinkAttribute(){
      return route("backoffice.airports.show", $this->id);
    }

    public function getEditLinkAttribute(){
      return route("backoffice.airports.edit", $this->id);
    }

    public function getImageAttribute($image){
      return asset($image);
    }
}
