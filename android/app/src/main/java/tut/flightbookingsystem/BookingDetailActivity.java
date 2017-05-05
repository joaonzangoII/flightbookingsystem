package tut.flightbookingsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.Passenger;

public class BookingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        final Bundle args = getIntent().getBundleExtra(Constant.DATA);
        final String strBooking = args.getString(Constant.BOOKING);
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<Booking>() {
        }.getType();


        final Booking mBooking = gson.fromJson(strBooking, type);
        if (mBooking != null) {
            setTitle(mBooking.created_at);
            ((TextView) findViewById(R.id.booking_date))
                    .setText(String.format("%1$s", mBooking.created_at));
            ((TextView) findViewById(R.id.total))
                    .setText(String.format("R%1$s", mBooking.total));
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
            passengerLayout.removeAllViewsInLayout();
            passengerLayout.removeAllViews();
            for (Passenger passenger : mBooking.passengers) {
                final CardView cardview = (CardView) getInflater()
                        .inflate(R.layout.passenger_confirmation_layout, null)
                        .findViewById(R.id.layout);
                final TextView name = (TextView) cardview.findViewById(R.id.name);
                final TextView travelClass = (TextView) cardview.findViewById(R.id.travel_class);
                final TextView foodType = (TextView) cardview.findViewById(R.id.foodType);

                name.setText(String.format("Name: %1$s", passenger.name));
                travelClass.setText(String.format("Travel Class: %1$s", passenger.flight_seat.travel_class.name));
                if (passenger.meal != null) {
                    if (passenger.meal.food != null) {
                        if (passenger.meal.food.food_type != null) {
                            foodType.setText(String.format("Food Type: %1$s", passenger.meal.food.food_type.name));
                        }
                    }
                }

                if (passenger.flight_seat != null) {
                    foodType.setText(String.format("Seat Number: %1$s", passenger.flight_seat.number));
                }
                passengerLayout.addView(cardview);
            }
        }
    }

    public LayoutInflater getInflater() {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
