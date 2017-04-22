<?php

use Illuminate\Http\Request;
use App\Schedule;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::name('checkavailability')->get('/checkavailability', function(Request $request){
    //$schedules = Schedule::where();
    $schedules = Schedule::all();
    return $schedules;
});
