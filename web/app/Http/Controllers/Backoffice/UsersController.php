<?php

namespace App\Http\Controllers\Backoffice;
use App\Http\Controllers\Controller;
use App\Schedule;
use Illuminate\Http\Request;
use App\User;
use App\Country;
use carbon\carbon;

class UsersController extends Controller
{
    public function __construct()
    {
      $this->middleware('auth');
    }

    public function index()
    {
      $users = User::with('user_type', 'country')
                   ->latest()
                   ->paginate(10);
      return view('users.index', compact('users'));
    }

    public function show(User $user)
    {
      return view('users.show', compact('user'));
    }

    public function create()
    {
      $countries = Country::all();
      return view('users.create', compact('countries'));
    }

    public function store(Request $request)
    {
      $user = User::create([
        'name' => $request->input('name'),
        'iata_user_code' => $request->input('iata_user_code'),
        'city' =>  $request->input('city'),
        'country_id' =>  $request->input('country'),
      ]);

      return redirect(route('backoffice.users.index'));
    }

    public function edit(User $user)
    {
      $countries = Country::all();
      return view('users.edit', compact('countries', 'user'));
    }

    public function update(Request $request, User $user)
    {
      $file = $request->file('image');
      $user->update($request->all());
      return redirect(route('backoffice.users.index'));
    }
}
