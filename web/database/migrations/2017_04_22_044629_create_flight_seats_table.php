<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateFlightSeatsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('flight_seats', function (Blueprint $table) {
            $table->increments('id');
            $table->string('number');
            $table->boolean('available')->default(true);
            $table->integer('aircraft_id')->unsigned();
            $table->foreign('aircraft_id')
                  ->references('id')
                  ->on('aircrafts')
                  ->onDelete('cascade');
            $table->integer('aircraft_seat_id')->unsigned();
            $table->foreign('aircraft_seat_id')
                  ->references('id')
                  ->on('aircraft_seats')
                  ->onDelete('cascade');
            $table->integer('travel_class_id')->unsigned();
            $table->foreign('travel_class_id')
                  ->references('id')
                  ->on('travel_classes')
                  ->onDelete('cascade');
            $table->integer('flight_id')->unsigned();
            $table->foreign('flight_id')
                  ->references('id')
                  ->on('flights')
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
        Schema::dropIfExists('flight_seats');
    }
}
