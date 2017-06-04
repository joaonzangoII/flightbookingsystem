<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateFlightSeatPricesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('flight_seat_prices', function (Blueprint $table) {
            $table->increments('id');
            $table->decimal('price')->default(500.00);
            $table->integer('flight_id')->unsigned();
            $table->foreign('flight_id')
                  ->references('id')
                  ->on('flights')
                  ->onDelete('cascade');
            $table->integer('aircraft_id')->unsigned();
            $table->foreign('aircraft_id')
                  ->references('id')
                  ->on('aircrafts')
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
        Schema::dropIfExists('flight_seat_prices');
    }
}
