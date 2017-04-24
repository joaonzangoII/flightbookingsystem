<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSchedulesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('schedules', function (Blueprint $table) {
            $table->increments('id');
            $table->timestamp('departure_time')->useCurrent();
            $table->timestamp('arrival_time')->useCurrent();
            // $table->date('date')->default(DB::raw('NOW()'));
            $table->date('date')->default(date("Y-m-d"));
            $table->integer('origin_airport_id')->unsigned();
            $table->foreign('origin_airport_id')
                  ->references('id')
                  ->on('airports')
                  ->onDelete('cascade');
            $table->integer('destination_airport_id')->unsigned();
            $table->foreign('destination_airport_id')
                  ->references('id')
                  ->on('airports')
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
        Schema::dropIfExists('schedules');
    }
}
