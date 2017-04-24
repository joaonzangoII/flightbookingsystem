package tut.flightbookingsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import tut.flightbookingsystem.adapter.PassengersAdapter;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.model.Schedule;

public class BookingActivity extends AppCompatActivity {
    private SessionManager session;
    private RecyclerView recyclerView;
    private PassengersAdapter passengersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        session = new SessionManager(this);
        LinearLayout passengersLayout = (LinearLayout) findViewById(R.id.passengersLayout);
        final Schedule schedule = session.getSchedule();
        ((TextView) findViewById(R.id.flight))
                .setText(String.format("Flight: %1$s", schedule.flight.aircraft.name));
        ((TextView) findViewById(R.id.flight_date))
                .setText(String.format("Date: %1$s", schedule.date));

        ((TextView) findViewById(R.id.origin_airport))
                .setText(String.format("From: %1$s", schedule.origin_airport.name));
        ((TextView) findViewById(R.id.destination_airport))
                .setText(String.format("To: %1$s", schedule.destination_airport.name));
        ((TextView) findViewById(R.id.departure_time))
                .setText(String.format("Departure Time: %1$s", schedule.departure_time));
        ((TextView) findViewById(R.id.arrival_time))
                .setText(String.format("Arrival Time: %1$s", schedule.arrival_time));
        ((TextView) findViewById(R.id.duration))
                .setText(String.format("Duration: %1$s", schedule.duration));

        final int numPeople = getIntent().getIntExtra(Constant.NUM_PEOPLE, 0);
        final Passenger[] passengersList = new Passenger[numPeople];


        //        passengersLayout.removeAllViews();
        //        passengersLayout.removeAllViewsInLayout();
        //        final int passengerCount = passengersList.length;
        //        for (int x = 0; x < passengerCount; x++) {
        //            final View view = LayoutInflater.from(this).inflate(
        //                    R.layout.passengers_item_layout,
        //                    null);
        //            ((TextView) view.findViewById(R.id.passengerNumber))
        //                    .setText(String.format("Passenger: %1$s", (x + 1)));
        //            passengersLayout.addView(view);
        //        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        passengersAdapter = new PassengersAdapter();
        passengersAdapter.setItems(Arrays.asList(passengersList));
        //passengersAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(passengersAdapter);
    }
}
