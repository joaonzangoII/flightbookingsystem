<?php

namespace App\Notifications;

use Illuminate\Bus\Queueable;
use Illuminate\Notifications\Notification;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Notifications\Messages\MailMessage;
use Illuminate\Notifications\Messages\NexmoMessage;

class FlightBooked extends Notification implements ShouldQueue
{
    use Queueable;
    var $booking;
    /**
     * Create a new notification instance.
     *
     * @return void
     */
    public function __construct($booking)
    {
        $this->booking = $booking;
    }

    /**
     * Get the notification's delivery channels.
     *
     * @param  mixed  $notifiable
     * @return array
     */
    public function via($notifiable)
    {
        return ['database'];
    }

    /**
     * Get the mail representation of the notification.
     *
     * @param  mixed  $notifiable
     * @return \Illuminate\Notifications\Messages\MailMessage
     */
    public function toMail($notifiable)
    {
      $url = url('/backofice/bookings/'. $this->booking->slug);
      return (new MailMessage)
                  ->line('A new booking with Reference ' .
                          $this->booking->booking_number .
                          ' was made on the system' )
                  ->action('View Booking', $url)
                  ->line(env("APP_NAME"));
    }

    public function toNexmo($notifiable)
    {
        return (new NexmoMessage)
                    ->content('Your booking Referrence is ' . $this->booking->booking_number)
                    ->unicode();
    }

    /**
     * Get the array representation of the notification.
     *
     * @param  mixed  $notifiable
     * @return array
     */
    public function toArray($notifiable)
    {
        return [
            // "booking_number" => $this->booking->booking_number,
            // "user_id" => $this->booking->user_id,
            // "passenger_count" => $this->booking->passengers()->count(),
        ];
    }
}
