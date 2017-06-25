<?php

use Illuminate\Http\Request;
use App\Country;
use App\User;
use App\UserType;
use App\Meal;
use App\Food;
use App\Drink;
use App\Airport;
use App\TravelClass;
use App\FlightStatus;
use App\Flight;
use App\Schedule;
use App\Aircraft;
use App\AircraftSeat;
use App\FlightSeat;
use App\FlightSeatPrice;
use App\Booking;
use App\Passenger;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Log;
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

Route::name('initial.data')->get('/initial-data/{user?}', 'Api\FlightsController@getInitialData');
Route::name('countries')->get('/countries', 'Api\DefaultController@getCountries');
Route::name('airports')->get('/airports', 'Api\DefaultController@getAirports');
Route::name('meals')->get('/meals', 'Api\DefaultController@getMeals');
Route::name('users')->get('/users', 'Api\DefaultController@getUsers');
Route::name('bookings')->get('/bookings', 'Api\DefaultController@getBookings');
Route::name('bookings.mine')->get('/bookings/{user}', 'Api\DefaultController@getMyBookings');
Route::name('travelclasses')->get('/travel-classes', 'Api\DefaultController@getTravelClasses');
Route::name('seatprices')->get('/seat-prices', 'Api\DefaultController@getSeatPrices');
Route::name('schedules')->get('/schedules', 'Api\DefaultController@getSchedules');
Route::name('findseats')->get('/aircraft-seats', 'Api\DefaultController@getAircraftSeats');
Route::name('findseatsbyaircraftid')->get('/aircraft-seats/{aircraft_id}', 'Api\DefaultController@getAircraftSeatsByAircraft');
Route::name('findaircraftbyid')->get('/aircrafts/{aircraft_id}', 'Api\DefaultController@getAircraftbyId');
Route::name('food')->get('/foods', 'Api\DefaultController@getFoods');
Route::name('drink')->get('/drinks', 'Api\DefaultController@getDrinks');
Route::name('flightstatus')->get('/flight-statuses', 'Api\DefaultController@getFlightStatuses');
Route::name('passengers')->get('/passengers', 'Api\DefaultController@getPassengers');

Route::name('flights')->get('/flights', 'Api\FlightsController@index');
Route::name('flight-seats-all')->get('/flight-seats', 'Api\FlightsController@findFlightSeats');
Route::name('flight-seats-flight-id')->get('/flight-seats/{flight_id}', 'Api\FlightsController@findFlightSeatsByFlightId');
Route::name('flight-seats-availability')->get('/flight-seats/{flight_id}/{travel_class_id}/{available?}', 'Api\FlightsController@findFlightSeatsByTravelClass');
Route::name('flight-seats-prices')->get('/flight-seats-prices/{flight_id}', 'Api\FlightsController@findFlightSeatsPrices');
Route::name('flight-statuses')->get('/flight-statuses', 'Api\FlightsController@flightStatus');
Route::name('aircraft-seats')->get('/aircraft-seats/{aircraft_id}/{travel_class_id}', 'Api\FlightsController@findAircraftSeatsByTravelClass');
Route::name('flights.one')->get('/flights/{flight}', 'Api\FlightsController@getFlightDetail');
Route::name('find_flights')->post('/find-flights', 'Api\FlightsController@getFlights');
Route::name('timetable')->post('/flights-timetable', 'Api\FlightsController@getFlightsTimetable');
Route::name('login')->post('/login', 'Api\AuthController@login');
Route::name('register')->post('/register', 'Api\AuthController@register');
Route::name('booking')->post('/make-booking', 'Api\FlightsController@storeBooking');
Route::name('booking.update.meal')->post('/update-meal', 'Api\FlightsController@updateMeal');
Route::name('booking.delete.meal')->post('/delete-meal', 'Api\FlightsController@deleteMeal');
Route::name('booking.update.delete.drink')->post('/add-update-drink', 'Api\FlightsController@addOrUpdateDrink');
Route::name('booking.update.delete.food')->post('/add-update-food', 'Api\FlightsController@addOrUpdateFood');
Route::name('sms')->get('/sms/send/{to}', 'Api\DefaultController@send_sms');
