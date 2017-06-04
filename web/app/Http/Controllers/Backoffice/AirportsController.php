<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\Airport;
use App\Country;
use carbon\carbon;

class AirportsController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
      $airports = Airport::latest()
                        ->paginate(10);
      return view('airports.index', compact('airports'));
    }

    public function show(Airport $airport)
    {
      return view('airports.show', compact('airport'));
    }

    public function create()
    {
      $countries = Country::all();
      return view('airports.create', compact('countries'));
    }

    public function store(Request $request)
    {
      $airport = Airport::create([
        'name' => $request->input('name'),
        'iata_airport_code' => $request->input('iata_airport_code'),
        'city' =>  $request->input('city'),
        'country_id' =>  $request->input('country'),
      ]);

      return redirect(route('backoffice.airports.index'));
    }

    public function edit(Airport $airport)
    {
      $countries = Country::all();
      return view('airports.edit', compact('countries', 'airport'));
    }

    public function update(Request $request, Airport $airport)
    {
      $file = $request->file('image');
      $airport->update($request->all());
      return redirect(route('backoffice.airports.index'));
    }
}
