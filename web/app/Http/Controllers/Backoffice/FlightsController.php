<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\Flight;
use App\Country;
use carbon\carbon;

class FlightsController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
      $flights = Flight::latest()
                        ->paginate(10);
      return view('flights.index', compact('flights'));
    }

    public function show(Flight $airport)
    {
      return view('flights.show', compact('airport'));
    }

    public function create()
    {
      $countries = Country::all();
      return view('flights.create', compact('countries'));
    }

    public function store(Request $request)
    {
      $airport = Flight::create([
        'name' => $request->input('name'),
        'iata_airport_code' => $request->input('iata_airport_code'),
        'city' =>  $request->input('city'),
        'country_id' =>  $request->input('country'),
      ]);

      return redirect(route('backoffice.flights.index'));
    }

    public function edit(Flight $airport)
    {
      $countries = Country::all();
      return view('flights.edit', compact('countries', 'airport'));
    }

    public function update(Request $request, Flight $airport)
    {
      $file = $request->file('image');
      $airport->update($request->all());
      return redirect(route('backoffice.flights.index'));
    }
}
