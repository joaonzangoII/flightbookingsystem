package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.PassengersAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.listener.OnSeatSelected;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.model.PassengerHeader;
import tut.flightbookingsystem.model.Schedule;
import tut.flightbookingsystem.util.LocalDate;

public class BookingActivity extends BaseActivity implements OnSeatSelected {
    private SessionManager session;
    private RecyclerView recyclerView;
    private PassengersAdapter passengersAdapter;
    private Schedule schedule;
    private List<Passenger> passengersList = new ArrayList<>();

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                if (data.getBoolean(Constant.IS_BOOKING)) {
                    final Bundle args = new Bundle();
                    args.putString(Constant.BOOKING, data.getString(Constant.BOOKING));
                    goToActivity(BookingConfirmationActivity.class, args);
                    session.setBookingPassengers("[]");
                    finish();
                } else {

                }
            }
            return false;
        }
    });
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback =
            new RecyclerClickListener.OnItemClickCallback() {
                @Override
                public void onItemClicked(final View view,
                                          final int parentPosition,
                                          final int childPosition) {
                }

                @Override
                public void onItemClicked(final View view,
                                          int position) {

                    if (view instanceof Button) {
                        final Button button = ((Button) view);
                        if (position >= 0) {
                            final Passenger passenger = passengersList.get(position);
                        }

                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setTitle("Book a Flight");
        session = new SessionManager(this);
        schedule = session.getSchedule();
        final Bundle args = getIntent().getBundleExtra(Constant.DATA);
        final int travel_class_id = args.getInt(Constant.TRAVEL_CLASS_ID, 0);
        final int numPeople = args.getInt(Constant.NUM_PEOPLE, 0);

        RequestManager.getAirports(session, requestHandler);
        RequestManager.getFlightSeats(session, schedule.flight_id, travel_class_id, requestHandler);

        ((TextView) findViewById(R.id.flight))
                .setText(String.format("Flight: %1$s", schedule.flight.aircraft.name));
        ((TextView) findViewById(R.id.flight_date))
                .setText(String.format("Date: %1$s", schedule.date));
        ((TextView) findViewById(R.id.origin_airport))
                .setText(String.format("From: %1$s", schedule.origin_airport.name));
        ((TextView) findViewById(R.id.destination_airport))
                .setText(String.format("To: %1$s", schedule.destination_airport.name));
        ((TextView) findViewById(R.id.departure_date)).setText(String.format("%1$s",
                LocalDate.formatDate(schedule.departure_time)));
        ((TextView) findViewById(R.id.departure_time)).setText(String.format("%1$s",
                LocalDate.getTime(schedule.departure_time)));
        ((TextView) findViewById(R.id.arrival_date)).setText(String.format("%1$s",
                LocalDate.formatDate(schedule.arrival_time)));
        ((TextView) findViewById(R.id.arrival_time)).setText(String.format("%1$s",
                LocalDate.getTime(schedule.arrival_time)));
        ((TextView) findViewById(R.id.duration))
                .setText(String.format("Duration: %1$s", schedule.duration));

        for (int x = 0; x < numPeople; x++) {
            final Passenger passenger = new Passenger();
            passenger.id = x;
            passenger.firstnames = "";
            passenger.surname = "";
            passenger.id_number = "";
            passenger.date_of_birth = "";
            passenger.gender   = "";
            passenger.drink_id = null;
            passenger.food_id  = null;
            passenger.flight_seat_id = 0;
            passenger.booking_id = 0;
            session.addorUpdateToBookingPassengers(passenger);
            //passengersList.add(passenger);
        }

        passengersList = session.getBookingPassengers();
        final List<PassengerHeader> passengerHeadersList = new ArrayList<>();

        for (Passenger passenger : passengersList) {
            final List<Passenger> p = new ArrayList<>();
            p.add(passenger);
            passengerHeadersList.add(new PassengerHeader("", p));
        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        passengersAdapter = new PassengersAdapter(this, passengerHeadersList);
        passengersAdapter.setItems(passengersList);
        passengersAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(passengersAdapter);

        final AppCompatButton btnBook = (AppCompatButton) findViewById(R.id.book);
        final Gson gson = new GsonBuilder().create();
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canBook = true;
                for (final Passenger passenger : passengersList) {
                    if (passenger.firstnames.trim().equals("") ||
                            passenger.surname.trim().equals("") ||
                            passenger.id_number.trim().equals("") ||
                            passenger.flight_seat_id == 0) {
                        canBook = false;
                        break;
                    }
                }

                if (canBook) {
                    RequestManager.makeBooking(
                            session,
                            BookingActivity.this,
                            session.getLoggedInUser().id,
                            schedule.flight_id,
                            schedule.flight.aircraft_id,
                            gson.toJson(passengersList),
                            requestHandler);
                } else {
                    Toast.makeText(BookingActivity.this,
                            "Please fill in the data for all passengers and select seats",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSeatSelected(final AbstractItem count) {

    }
}
