<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\AuthenticatesUsers;
use App\User;
use App\UserType;

class LoginController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles authenticating users for the application and
    | redirecting them to your home screen. The controller uses a trait
    | to conveniently provide its functionality to your applications.
    |
    */

    use AuthenticatesUsers;

    /**
     * Where to redirect users after login.
     *
     * @var string
     */
    protected $redirectTo = '/home';

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest')->except('logout');
    }

    public function showLoginForm(){
      $bodyClass = "hold-transition login-page";
      return view("auth.login", compact('bodyClass'));
    }

    // protected function authenticated(Request $request, $user)
    // {
    //     dd($user);
    //     $user = User::with('bookings', 'country')
    //                 ->where('email', '=',  $email)
    //                 ->first();
    //     $user_type_admin = UserType::where('name', 'Administrator')->first();
    //     if($user->user_type_id == $user_type_admin->id){
    //     return response()->json([
    //         'code' => '500',
    //         'erro' => true,
    //         'messages' => [
    //         'email' => ['Only non Admin users can use the mobile app']
    //         ]
    //     ]);
    //     }
    // }
}
