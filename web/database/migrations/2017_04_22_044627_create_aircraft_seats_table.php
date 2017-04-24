<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAircraftSeatsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('aircraft_seats', function (Blueprint $table) {
            $table->increments('id');
            $table->string('number');
            $table->integer('travel_class_id')->unsigned();
            $table->foreign('travel_class_id')
                  ->references('id')
                  ->on('travel_classes')
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
        Schema::dropIfExists('aircraft_seats');
    }
}
