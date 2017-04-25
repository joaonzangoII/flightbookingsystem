package tut.flightbookingsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.Passenger;

public class BookingConfirmationActivity extends AppCompatActivity {
    private Booking mBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<Booking>() {
        }.getType();

        mBooking = (Booking) gson.fromJson(getIntent().getStringExtra(Constant.BOOKING), type);

        ((TextView) findViewById(R.id.flight))
                .setText(String.format("Flight: %1$s", mBooking.departure_flight.aircraft.name));
        ((TextView) findViewById(R.id.flight_date))
                .setText(String.format("Date: %1$s", mBooking.departure_flight.schedule.date));

        ((TextView) findViewById(R.id.origin_airport))
                .setText(String.format("From: %1$s", mBooking.departure_flight.schedule.origin_airport.name));
        ((TextView) findViewById(R.id.destination_airport))
                .setText(String.format("To: %1$s", mBooking.departure_flight.schedule.destination_airport.name));
        ((TextView) findViewById(R.id.departure_time))
                .setText(String.format("Departure Time: %1$s", mBooking.departure_flight.schedule.departure_time));
        ((TextView) findViewById(R.id.arrival_time))
                .setText(String.format("Arrival Time: %1$s", mBooking.departure_flight.schedule.arrival_time));
        ((TextView) findViewById(R.id.duration))
                .setText(String.format("Duration: %1$s", mBooking.departure_flight.schedule.duration));

        final LinearLayout passengerLayout = (LinearLayout) findViewById(R.id.passengerLayout);

        final TextView textView = (TextView) findViewById(R.id.json);
        textView.setText(getIntent().getStringExtra(Constant.BOOKING));

        passengerLayout.removeAllViewsInLayout();
        passengerLayout.removeAllViews();
        for (Passenger passenger : mBooking.passengers) {
            final LinearLayout ll = (LinearLayout) getInflater()
                    .inflate(R.layout.passenger_confirmation_layout, null)
                    .findViewById(R.id.layout);
            final TextView name = (TextView) ll.findViewById(R.id.name);
            final TextView travelClass = (TextView) ll.findViewById(R.id.travel_class);
            final TextView foodType = (TextView) ll.findViewById(R.id.foodType);

            name.setText(String.format("Name: %1$s", passenger.name));
            travelClass.setText(String.format("Travel Class: %1$s", passenger.aircraft_seat.travel_class.name));
            foodType.setText(String.format("Food Type: %1$s", passenger.meal.food.food_type.name));
            passengerLayout.addView(ll);
        }

        final AppCompatButton btnDone = (AppCompatButton) findViewById(R.id.done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(BookingConfirmationActivity.this, NavigationDrawerActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    public LayoutInflater getInflater() {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
