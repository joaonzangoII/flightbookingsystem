<?php

namespace App\Http\Controllers;
use App\Booking;
use App\Schedule;
use App\User;
use App\Flight;

use Illuminate\Http\Request;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $users = User::all();
        $bookings = Booking::all();
        $schedules = Schedule::all();
        $flights = Flight::all();
        return view('home', compact('bookings', 'schedules', 'users', 'flights'));
    }
}
