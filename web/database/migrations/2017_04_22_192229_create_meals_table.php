<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMealsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('meals', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('passenger_id')->unsigned();
            $table->foreign('passenger_id')
                  ->references('id')
                  ->on('passengers')
                  ->onDelete('cascade');
            $table->integer('drink_id')->unsigned();
            $table->foreign('drink_id')
                  ->references('id')
                  ->on('drinks')
                  ->onDelete('cascade');
            $table->integer('food_id')->unsigned();
            $table->foreign('food_id')
                  ->references('id')
                  ->on('foods')
                  ->onDelete('cascade');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('meals');
    }
}
