<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Log;
use Nexmo\Laravel\Facade\Nexmo;
use App\User;
use App\Airport;
use App\Country;
use App\Meal;
use App\Food;
use App\Drink;
use App\Booking;
use App\TravelClass;
use App\Flight;
use App\FlightSeatPrice;
use App\FlightSeat;
use App\Schedule;
use App\Aircraft;
use App\AircraftSeat;
use App\FlightStatus;
use App\Passenger;

class DefaultController extends Controller
{
    public function sendSms(Request $request, String $to)
    {
      $message = $nexmo->message()->send([
          'to' => $to,
          'from' => env('NEXMO_NUMBER'),
          'text' => 'Sending SMS from Laravel. Woohoo!'
      ]);
      Log::info('sent message: ' . $message['message-id']);
    }

    public function getSchedules(){
      $schedules = Schedule::with('flight',
                                  'flight.aircraft',
                                  'flight.aircraft.aircraft_manufacturer')
                           ->get();
      return $schedules;
    }

    public function getUsers(){
      $users = User::latest()->get();
      return $users;
    }

    public function getSeatPrices(){
      $seat_prices = FlightSeatPrice::all();
      return $seat_prices;
    }

    public function getTravelClasses(){
      $travel_classes = TravelClass::all();
      return $travel_classes;
    }

    public function getMyBookings(Request $request, User $user){
      $bookings = Booking::with('passengers',
                                'passengers.booking',
                                'passengers.drink',
                                'passengers.drink.drink',
                                'passengers.food',
                                'passengers.food.food',
                                'passengers.food.food.food_type',
                                'passengers.flight_seat',
                                'passengers.flight_seat.travel_class',
                                'aircraft',
                                'departure_flight',
                                'departure_flight.aircraft',
                                'departure_flight.schedule',
                                'return_flight',
                                'return_flight.aircraft',
                                'return_flight.schedule')
                            ->where('user_id', $user->id)
                            ->latest()
                            ->get();
      return $bookings;
    }

    public function getBookings(){
      $bookings = Booking::with('passengers',
                                'passengers.booking',
                                'passengers.drink',
                                'passengers.drink.drink',
                                'passengers.food',
                                'passengers.food.food.food_type',
                                'passengers.flight_seat',
                                'passengers.flight_seat.travel_class',
                                'aircraft',
                                'departure_flight',
                                'departure_flight.aircraft',
                                'departure_flight.schedule',
                                'return_flight',
                                'return_flight.aircraft',
                                'return_flight.schedule')
                          ->latest()
                          ->get();
      return $bookings;
    }

    public function getCountries(){
      $countries = Country::latest()
                          ->get();
      return $countries;
    }

    public function getPassengers(){
      $passengers = Passenger::with('passengers.booking',
                                    'passengers.drink',
                                    'passengers.drink.drink',
                                    'passengers.food',
                                    'passengers.food.food.food_type',
                                    'passengers.flight_seat',
                                    'passengers.flight_seat.travel_class')
                             ->latest()
                             ->get();
      return $passengers;
    }

    public function getAirports(){
      $airports = Airport::latest()
                         ->get();
      return $airports;
    }

    public function getAircraftbyId(Request $request, String $aircraft_id){
        $aircrafts = Aircraft::with('flights', 'aircraft_manufacturer', 'aircraft_seats')
                             ->where('id', $aircraft_id)
                             ->get();

        return $aircrafts;
    }

    public function getAircraftSeatsByAircraft(Request $request, String $aircraft_id){
       $aircraft_seats = AircraftSeat::with('travel_class')
                                     ->where('aircraft_id', $aircraft_id)
                                     ->get();
       return $aircraft_seats;
    }

    public function getAircraftSeats(){
      $aircraft_seats = AircraftSeat::with('travel_class', 'seat_price')->get();
      return $aircraft_seats;
    }

    public function getMeals(){
      $meals = Meal::latest()->get();
      return $meals;
    }

    public function getFoods(){
      $foods = Food::with('food_type')->get();
      return $foods;
    }

    public function getDrinks(){
      $drinks = Drink::all();
      return $drinks;
    }

    public function getFlightStatuses(){
      $flight_statuses = FlightStatus::all();
      return $flight_statuses;
    }
}
