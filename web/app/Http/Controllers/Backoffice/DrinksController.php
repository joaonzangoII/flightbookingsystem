<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\Drink;
use carbon\carbon;

class DrinksController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
      $drinks = Drink::latest()
                     ->paginate(10);
      return view('drinks.index', compact('drinks'));
    }

    public function show(Drink $drink)
    {
      return view('drinks.show', compact('drink'));
    }

    public function create()
    {
      return view('drinks.create');
    }

    public function store(Request $request)
    {
      $file = $request->file('image');
      $drink = Drink::create([
        'name' => $request->input('name'),
        'description' => $request->input('description'),
        'image' => uploadImage($file, true)
      ]);

      return redirect(route('backoffice.drinks.index'));
    }

    public function edit(Drink $drink)
    {
      return view('drinks.edit', compact('drink'));
    }

    public function update(Request $request, Drink $drink)
    {
      $file = $request->file('image');
      $drink->update($request->all());

      return redirect(route('backoffice.drinks.index'));
    }
}
