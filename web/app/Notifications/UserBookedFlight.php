<?php

namespace App\Notifications;

use Illuminate\Bus\Queueable;
use Illuminate\Notifications\Notification;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Notifications\Messages\MailMessage;
use Illuminate\Notifications\Messages\NexmoMessage;

class UserBookedFlight extends Notification
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
        return ['mail', 'nexmo', 'database'];
    }

    /**
     * Get the mail representation of the notification.
     *
     * @param  mixed  $notifiable
     * @return \Illuminate\Notifications\Messages\MailMessage
     */
    public function toMail($notifiable)
    {
        return (new MailMessage)
                    ->line('Your booking Reference is '
                    . $this->booking->booking_number
                    . ' you booked for '
                    . $this->booking->passengers()->count() . ' passengers'
                    )
                    ->line('Thank you for using our application, Please call again!');
    }

    public function toNexmo($notifiable)
    {
        return (new NexmoMessage)
                    ->content('Your booking Reference is '
                    . $this->booking->booking_number
                    . ' you booked for '
                    . $this->booking->passengers()->count() . ' passengers'
                    )
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
            "booking_number" => $this->booking->booking_number,
            "user_id" => $this->booking->user_id,
            "passenger_count" => $this->booking->passengers()->count(),
        ];
    }
}
