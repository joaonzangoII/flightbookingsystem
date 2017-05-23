<?php

namespace App\Http\Controllers;
use App\Schedule;
use Illuminate\Http\Request;

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

    public function create()
    {
        return view('schedules.create');
    }
}
