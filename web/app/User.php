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
        'firstnames',
        'surname',
        'id_number',
        'phone',
        'email',
        'password',
        'country_id',
        'user_type_id'
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
      return $this->firstnames . ' ' . $this->surname;
    }

    public function bookings(){
      return $this->hasMany('App\Booking');
    }

    public function user_type(){
      return $this->hasOne('App\UserType', 'id', 'user_type_id');
    }

    public function country(){
      return $this->belongsTo('App\Country');
    }

    public function isAdmin()
    {
      $user_type_admin = \App\UserType::where('name', 'Administrator')
                                      ->first();
      return $this->user_type_id == $user_type_admin->id;
    }

    public function getShowLinkAttribute(){
      return route("backoffice.users.show", $this->id);
    }

    public function getEditLinkAttribute(){
      return route("backoffice.users.edit", $this->id);
    }

    public function routeNotificationForNexmo()
    {
      return $this->phone;
    }

    public function routeNotificationForMail()
    {
      $this->email;
    }
}
