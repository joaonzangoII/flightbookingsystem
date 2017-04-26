package tut.flightbookingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.DrinkSpinnerAdapter;
import tut.flightbookingsystem.adapter.FoodSpinnerAdapter;
import tut.flightbookingsystem.adapter.PassengersAdapter;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.AircraftSeat;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.Meal;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.model.Schedule;

public class BookingActivity extends AppCompatActivity {
    private SessionManager session;
    private RecyclerView recyclerView;
    private PassengersAdapter passengersAdapter;
    private List<Passenger> passengersList = new ArrayList<>();
    private int selected_drink_id;
    private int selected_food_id;
    private Schedule schedule;

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                if(data.getBoolean(Constant.IS_BOOKING)) {
                    final Intent intent = new Intent(BookingActivity.this, BookingConfirmationActivity.class);
                    intent.putExtra(Constant.BOOKING, data.getString(Constant.BOOKING));
                    finish();
                    startActivity(intent);
                }else{
                    passengersAdapter.setAircrafSeats(session.getAircraftSeats());
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
                        Button button = ((Button) view);
                        if (position >= 0) {
                            final Passenger passenger = passengersList.get(position);
                            if (button.getId() == R.id.add_meal) {
                                addMeal(passenger);
                            } else {
                                //selectSeat(passenger);
                            }
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
        RequestManager.getFlightSeats(session, schedule.flight_id, requestHandler);
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

        for (int x = 0; x < numPeople; x++) {
            final Passenger passenger = new Passenger();
            passenger.first_name = "";
            passenger.middle_name = "";
            passenger.last_name = "";
            passenger.booking_id = 0;
            passenger.id_number = "";
            passenger.date_of_birth = "";
            passenger.gender = "";
            passenger.meal = null;
            passengersList.add(passenger);
        }

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        passengersAdapter = new PassengersAdapter();
        passengersAdapter.setItems(passengersList);
        passengersAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(passengersAdapter);

        final AppCompatButton btnBook = (AppCompatButton) findViewById(R.id.book);
        final Gson gson = new GsonBuilder().create();
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ITEMS>>", gson.toJson(passengersList));
                RequestManager.makeBooking(
                        session,
                        BookingActivity.this,
                        session.getLoggedInUser().id,
                        schedule.flight_id,
                        schedule.flight.aircraft_id,
                        gson.toJson(passengersList),
                        requestHandler);
            }
        });
    }


    public void addMeal(final Passenger passenger) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.cust_dialog);
        final View dialogView = getInflater().inflate(R.layout.add_meal_layout, null);

        final List<Food> foods = session.getFoods();
        final List<Drink> drinks = session.getDrinks();

        final FoodSpinnerAdapter adapterFoods = new FoodSpinnerAdapter
                (this, foods);
        final DrinkSpinnerAdapter drinksAdapter = new DrinkSpinnerAdapter
                (this, drinks);


        final Spinner foodsSpinner = (Spinner) dialogView.findViewById(R.id.foodSpinner);
        foodsSpinner.setAdapter(adapterFoods);
        foodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                selected_food_id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner drinksSpinner = (Spinner) dialogView.findViewById(R.id.drinksSpinner);
        drinksSpinner.setAdapter(drinksAdapter);
        drinksSpinner.setAdapter(drinksAdapter);
        drinksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                selected_drink_id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        alertDialogBuilder.setTitle("Select Meal Choice");
        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Meal meal = new Meal();
                meal.drink_id = drinksSpinner.getSelectedItemId();
                meal.food_id = foodsSpinner.getSelectedItemId();
                passenger.meal = meal;
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.create().show();
    }

    //
    //    public void selectSeat(final Passenger passenger) {
    //        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.cust_dialog);
    //        final View dialogView = getInflater().inflate(R.layout.seats_layout, null);
    //        final List<AircraftSeat> aircraftSear = session.getAircraftSeats();
    //        RequestManager.getFlightSeats(session, schedule.flight_id, requestHandler);
    //        //        final FoodSpinnerAdapter adapterFoods = new FoodSpinnerAdapter
    //        //                (this, foods);
    //        //        final Spinner foodsSpinner = (Spinner) dialogView.findViewById(R.id.foodSpinner);
    //        //        foodsSpinner.setAdapter(adapterFoods);
    //        //        foodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    //        //            @Override
    //        //            public void onItemSelected(AdapterView<?> adapterView,
    //        //                                       View view,
    //        //                                       int i,
    //        //                                       long l) {
    //        //                selected_food_id = i;
    //        //            }
    //        //
    //        //            @Override
    //        //            public void onNothingSelected(AdapterView<?> adapterView) {
    //        //
    //        //            }
    //        //        });
    //
    //        alertDialogBuilder.setTitle("Select Seat Choice");
    //        alertDialogBuilder.setView(dialogView);
    //        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                //passenger.meal = aircraft_seat_id;
    //            }
    //        });
    //
    //        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //
    //            }
    //        });
    //        alertDialogBuilder.create().show();
    //    }


    public LayoutInflater getInflater() {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
