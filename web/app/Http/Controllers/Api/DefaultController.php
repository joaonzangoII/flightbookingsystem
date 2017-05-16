<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Log;
use Nexmo\Laravel\Facade\Nexmo;

class DefaultController extends Controller
{
    public function send_sms(Request $request, String $to)
    {
      $message = $nexmo->message()->send([
          'to' => $to,
          'from' => env('NEXMO_NUMBER'),
          'text' => 'Sending SMS from Laravel. Woohoo!'
      ]);
      Log::info('sent message: ' . $message['message-id']);
    }
}
