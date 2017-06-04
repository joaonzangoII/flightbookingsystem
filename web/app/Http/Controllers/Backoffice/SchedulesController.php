<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\FlightStatus;
use App\Flight;
use App\Aircraft;
use App\FlightSeat;
use App\FlightSeatPrice;
use App\TravelClass;
use App\Airport;
use carbon\carbon;

class SchedulesController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
        $schedules = Schedule::with('origin_airport', 'destination_airport')
                             ->latest()
                             ->paginate(10);
        return view('schedules.index', compact('schedules'));
    }

    public function show()
    {

    }

    public function create()
    {
      $aircrafts = Aircraft::all();
      $airports = Airport::all();
      return view('schedules.create', compact('airports','aircrafts'));
    }



    public function store(Request $request)
    {
      $flight_status_id = FlightStatus::where('name', 'Pending')
                                    ->first()
                                    ->id;
      $flight = Flight::create([
                                'aircraft_id' => $request->input('aircraft'),
                                'flight_status_id' => $flight_status_id]);

      $schedule = Schedule::create([
        'departure_time' => $request->input('departure_time'),
        'arrival_time' =>   $request->input('arrival_time'),
        'origin_airport_id' => $request->input('origin_airport'),
        'destination_airport_id' => $request->input('destination_airport'),
        'date' => $request->input('departure_time').substr(0, 10),
        'flight_id' => $flight->id
      ]);

      $first_travel_class    = TravelClass::where('name', 'First')
                                          ->first();
      $business_travel_class = TravelClass::where('name', 'Business')
                                          ->first();
      $economy_travel_class  = TravelClass::where('name', 'Economy')
                                          ->first();
      // $flight = Flight::with('flight_status', 'aircraft', 'aircraft.aircraft_seats')
      //                  ->findOrFail();
      $aircraft = Aircraft::with('aircraft_seats')
                          ->findOrFail($flight->aircraft_id);
      foreach ($aircraft->aircraft_seats as $key => $aircraft_seat) {
        $flight_seat  = FlightSeat::create([
          'aircraft_id' => $aircraft_seat->aircraft_id,
          'aircraft_seat_id' => $aircraft_seat->id,
          'number' =>  $aircraft_seat->number,
          'travel_class_id' => $aircraft_seat->travel_class_id,
          'flight_id' => $flight->id,
        ]);

        if($aircraft_seat->travel_class_id == $first_travel_class->id){
          $price = $request->input('first_class_price');
        }elseif($aircraft_seat->travel_class_id == $business_travel_class->id){
          $price = $request->input('business_class_price');
        }else{
          $price = $request->input('economy_class_price');
        }

        $seat_price = FlightSeatPrice::create([
          'price' => $price,
          'flight_id' => $flight->id,
          'aircraft_id' => $aircraft_seat->aircraft_id,
          'flight_seat_id' => $flight_seat->id
        ]);
      }
      return redirect(route('backoffice.schedules.index'));
    }
}
