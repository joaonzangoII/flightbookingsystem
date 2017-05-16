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
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Meal;
import tut.flightbookingsystem.model.Passenger;

public class MyBookingsDetailActivity extends AppCompatActivity {

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
            setTitle(mBooking.booking_number);

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
                  passengerLayout.addView(setupPassenger(passenger));
            }
        }
    }

    public CardView setupPassenger(final Passenger passenger) {
        final CardView cardview = (CardView) getInflater()
                .inflate(R.layout.passenger_confirmation_layout, null)
                .findViewById(R.id.layout);
        final TextView firstName = (TextView) cardview.findViewById(R.id.first_name);
        final TextView lastName = (TextView) cardview.findViewById(R.id.last_name);
        final TextView travelClass = (TextView) cardview.findViewById(R.id.travel_class);
        final TextView seatNumber = (TextView) cardview.findViewById(R.id.seat_number);
        final TextView foodType = (TextView) cardview.findViewById(R.id.food_type);

        firstName.setText(String.format("First Name: %1$s", passenger.first_name));
        lastName.setText(String.format("Last Name: %1$s", passenger.last_name));
        //name.setText(String.format("Name: %1$s", passenger.name));

        final FlightSeat flight_seat = passenger.flight_seat;
        if (flight_seat != null) {
            seatNumber.setText(String.format("Seat Number: %1$s", passenger.flight_seat.number));
            if (flight_seat.travel_class != null) {
                if (flight_seat.travel_class.name != null) {
                    travelClass.setText(String.format("Travel Class: %1$s", flight_seat.travel_class.name));
                }
            }
        }

        final Meal meal = passenger.meal;
        if (meal != null) {
            if (meal.food != null) {
                if (meal.food.food_type != null) {
                    foodType.setText(String.format("Food Type:  %1$s", passenger.meal.food.food_type.name));
                }else{
                    foodType.setText("No Meal Added yet");
                }
            }
        }

        return cardview;
    }

    public LayoutInflater getInflater() {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
