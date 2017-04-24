<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePassengersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('passengers', function (Blueprint $table) {
            $table->increments('id');
            $table->string('first_name');
            $table->string('middle_name')->nullable();
            $table->string('last_name');
            $table->string('id_number');
            $table->string('date_of_birth');
            $table->string('gender');
            $table->integer('booking_id')->unsigned();
            $table->foreign('booking_id')
                  ->references('id')
                  ->on('bookings')
                  ->onDelete('cascade');
            $table->integer('aircraft_seat_id')->unsigned();
            $table->foreign('aircraft_seat_id')
                  ->references('id')
                  ->on('aircraft_seats')
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
        Schema::dropIfExists('passengers');
    }
}
