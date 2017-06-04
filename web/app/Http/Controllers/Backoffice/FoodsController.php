<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\Food;
use App\FoodType;
use carbon\carbon;

class FoodsController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
      $foods = Food::with('food_type')
                           ->latest()
                           ->paginate(10);
      return view('foods.index', compact('foods'));
    }

    public function show(Food $food)
    {
      return view('foods.show', compact('food'));
    }

    public function create()
    {
      $foodTypes = FoodType::all();
      return view('foods.create', compact('foodTypes'));
    }

    public function store(Request $request)
    {
      $file = $request->file('image');
      $food = Food::create([
        'name' => $request->input('name'),
        'description' => $request->input('description'),
        'image' => uploadImage($file, true),
        'food_type_id' => $request->input('food_type')
      ]);

      return redirect(route('backoffice.foods.index'));
    }

    public function edit(Food $food)
    {
      $foodTypes = FoodType::all();
      return view('foods.edit', compact('food', 'foodTypes'));
    }

    public function update(Request $request, Food $food)
    {
      dd($food);
      $file = $request->file('image');
      $food->update($request->all());
      return redirect(route('backoffice.foods.index'));
    }
}
