<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Food extends Model
{
  protected $fillable = ['name', 'description', 'food_type_id', 'image'];
  public function food_type(){
    return $this->hasOne('App\FoodType', 'id', 'food_type_id');
  }

  public function getShowLinkAttribute(){
    return route("backoffice.foods.show", $this->id);
  }

  public function getEditLinkAttribute(){
    return route("backoffice.foods.edit", $this->id);
  }

  public function getImageAttribute($image){
    return asset($image);
  }
}
