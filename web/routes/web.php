<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::name('home')->get('/', 'HomeController@index');
// // Registration Routes...
// Route::name('register')->get('register', 'Auth\RegisterController@showRegistrationForm');
// Route::post('register.register', 'Auth\RegisterController@register');
Auth::routes();
// Route::name('routes')->get('/routes', function(Request $request){
//   // $routes = Artisan::routeList();
//   $routes =Route::getRoutes();;
//   return $routes;
// });
