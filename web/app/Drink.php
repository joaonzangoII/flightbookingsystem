<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Drink extends Model
{
    protected $fillable = ['name', 'description', 'image'];

    public function getShowLinkAttribute(){
      return route("backoffice.drinks.show", $this->id);
    }

    public function getEditLinkAttribute(){
      return route("backoffice.drinks.edit", $this->id);
    }
}
