<?php
Route::name("home")->get('dashboard', 'Backoffice\AdminController@index');
Route::name("info")->get('info', 'Backoffice\AdminController@info');
Route::resource('bookings', 'Backoffice\BookingsController');
Route::resource('foods', 'Backoffice\FoodsController');
Route::resource('drinks', 'Backoffice\DrinksController');
Route::resource('schedules', 'Backoffice\SchedulesController');
Route::resource('airports', 'Backoffice\AirportsController');
Route::resource('users', 'Backoffice\UsersController');
Route::resource('flights', 'Backoffice\FlightsController');
