<?php

namespace App\Http\Controllers;
use App\Schedule;
use Illuminate\Http\Request;

class SchedulesController extends Controller
{
   public function index()
    {
        $schedules = Schedule::latest()->get();
        return view('schedules.index', compact('schedules'));
    }

    public function create()
    {
        return view('schedules.create');
    }
}
