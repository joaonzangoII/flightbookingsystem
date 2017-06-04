<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\Booking;
use App\Country;
use carbon\carbon;

class BookingsController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
      $bookings = Booking::latest()
                         ->paginate(10);
      return view('bookings.index', compact('bookings'));
    }

    public function show(Booking $booking)
    {
      return view('bookings.show', compact('booking'));
    }

    public function create()
    {
      $countries = Country::all();
      return view('bookings.create', compact('countries'));
    }

    public function store(Request $request)
    {
      $file = $request->file('image');
      $booking = Booking::create([
        'name' => $request->input('name'),
        'iata_airport_code' => $request->input('iata_airport_code'),
        'city' =>  $request->input('city'),
        'country_id' =>  $request->input('country'),
      ]);

      return redirect(route('backoffice.bookings.index'));
    }

    public function edit(Booking $booking)
    {
      $countries = Country::all();
      return view('bookings.edit', compact('countries'));
    }

    public function update(Request $request, Booking $booking)
    {
      $file = $request->file('image');
      $booking->update($request->all());
      return redirect(route('backoffice.bookings.index'));
    }
}
