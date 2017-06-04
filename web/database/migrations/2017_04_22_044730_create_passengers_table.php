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
            $table->string('firstnames');
            $table->string('surname');
            $table->string('id_number');
            $table->string('date_of_birth');
            $table->string('gender');
            $table->integer('booking_id')->unsigned();
            $table->foreign('booking_id')
                  ->references('id')
                  ->on('bookings')
                  ->onDelete('cascade');
            $table->integer('flight_seat_id')->unsigned();
            $table->foreign('flight_seat_id')
                  ->references('id')
                  ->on('flight_seats')
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
