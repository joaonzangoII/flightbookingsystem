<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;
use Illuminate\Support\Facades\DB;

class CreateBookingsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bookings', function (Blueprint $table) {
            $table->increments('id');
            $table->string('booking_number');
            $table->decimal('subtotal')->default(0.0);
            $table->decimal('total')->default(0.0);
            $table->boolean('return')->default(true);
            $table->integer('user_id')->unsigned();
            $table->foreign('user_id')
                  ->references('id')
                  ->on('users')
                  ->onDelete('cascade');
            $table->integer('departure_flight_id')->unsigned();
            $table->foreign('departure_flight_id')
                  ->references('id')
                  ->on('flights')
                  ->onDelete('cascade');
            $table->integer('return_flight_id')->unsigned();
            $table->foreign('return_flight_id')
                  ->references('id')
                  ->on('flights')
                  ->onDelete('cascade');
            $table->integer('aircraft_id')->unsigned();
            $table->foreign('aircraft_id')
                  ->references('id')
                  ->on('aircrafts')
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
        Schema::dropIfExists('bookings');
    }
}
