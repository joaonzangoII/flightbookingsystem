<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Booking;
use App\Schedule;
use App\User;
use App\Flight;
use Auth;
use Illuminate\Http\Request;

class AdminController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
      $this->middleware(['auth', 'admin'])->except('info');
    }

    public function info()
    {
      $users = User::all();
      $bookings = Booking::all();
      $schedules = Schedule::all();
      $flights = Flight::all();
      return view('info', compact('bookings', 'schedules', 'users', 'flights'));
    }

    public function index()
    {
      $users = User::all();
      $bookings = Booking::all();
      $schedules = Schedule::all();
      $flights = Flight::all();
      return view('home', compact('bookings', 'schedules', 'users', 'flights'));
    }
}
