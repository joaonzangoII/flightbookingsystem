<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */

    protected $appends =['name'];

    protected $fillable = [
        'first_name', 'middle_name', 'last_name', 'email', 'password',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];

    public function getNameAttribute()  {
      return $this->first_name . ' ' . $this->last_name;
    }

    public function bookings(){
      return $this->hasMany('App\Booking');
    }

    public function country(){
      return $this->belongsTo('App\Country');
    }
}
