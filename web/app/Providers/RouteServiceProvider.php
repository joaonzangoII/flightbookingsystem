<?php

namespace App\Providers;

use Illuminate\Support\Facades\Route;
use Illuminate\Foundation\Support\Providers\RouteServiceProvider as ServiceProvider;
use App\User;
use App\Airport;
use App\Booking;

class RouteServiceProvider extends ServiceProvider
{
    /**
     * This namespace is applied to your controller routes.
     *
     * In addition, it is set as the URL generator's root namespace.
     *
     * @var string
     */
    protected $namespace = 'App\Http\Controllers';

    /**
     * Define your route model bindings, pattern filters, etc.
     *
     * @return void
     */
    public function boot()
    {
      parent::boot();
      Route::model('airport', Airport::class);
      Route::bind('airport', function ($value) {
        return Airport::with('country')
                      ->whereId($value)
                      ->first();

      });

      Route::model('user', User::class);
      Route::bind('user', function ($value) {
        return User::with('user_type', 'country')
                      ->whereId($value)
                      ->first();
      });
      Route::model('booking', Booking::class);
      Route::bind('booking', function ($value) {
        return Booking::with('passengers',
                                 'passengers.booking',
                                 'passengers.drink',
                                 'passengers.food',
                                 'passengers.food.food',
                                 'passengers.food.food.food_type',
                                 'passengers.flight_seat',
                                 'passengers.flight_seat.travel_class',
                                 'aircraft','departure_flight',
                                 'departure_flight.aircraft',
                                 'departure_flight.schedule',
                                 'return_flight',
                                 'return_flight.aircraft',
                                 'return_flight.schedule')
                      ->whereId($value)
                      ->first();

      });
   }

    /**
     * Define the routes for the application.
     *
     * @return void
     */
    public function map()
    {
        $this->mapAdminRoutes();
        $this->mapApiRoutes();
        $this->mapWebRoutes();
    }

    /**
     * Define the "web" routes for the application.
     *
     * These routes all receive session state, CSRF protection, etc.
     *
     * @return void
     */
    protected function mapWebRoutes()
    {
        Route::middleware('web')
             ->namespace($this->namespace)
             ->group(base_path('routes/web.php'));
    }

    /**
     * Define the "admin" routes for the application.
     *
     * These routes are typically stateless.
     *
     * @return void
     */
    protected function mapAdminRoutes()
    {
        Route::prefix('backoffice')
             ->middleware(['web'])
             ->namespace($this->namespace)
             ->name('backoffice.')
             ->group(base_path('routes/backoffice.php'));
    }

    /**
     * Define the "api" routes for the application.
     *
     * These routes are typically stateless.
     *
     * @return void
     */
    protected function mapApiRoutes()
    {
        Route::prefix('api')
             ->middleware('api')
             ->namespace($this->namespace)
             ->name('api.')
             ->group(base_path('routes/api.php'));
    }
}
