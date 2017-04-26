package tut.flightbookingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.AirportsAdapter;
import tut.flightbookingsystem.adapter.ScheduleAdapter;
import tut.flightbookingsystem.adapter.TravelClassSpinnerAdapter;
import tut.flightbookingsystem.listener.RecyclerClickListener.OnItemClickCallback;
import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Schedule;
import tut.flightbookingsystem.model.TravelClass;

public class FindFlightActivity extends AppCompatActivity {
    private static String TAG = FindFlightActivity.class.getName();
    private List<Airport> airportsList = new ArrayList<>();
    private List<TravelClass> travelClassesList = new ArrayList<>();
    private String[] people = new String[9];
    private SessionManager session;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleadapter;
    private List<Schedule> mSchedules;
    private String num_people;
    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                final Gson gson = new GsonBuilder().create();
                final Type type = new TypeToken<List<Schedule>>() {
                }.getType();
                mSchedules = gson.fromJson(data.getString(Constant.STR_SCHEDULE), type);
                scheduleadapter.setItems(mSchedules);
            }
            return false;
        }
    });

    private OnItemClickCallback onItemClickCallback =
            new OnItemClickCallback() {

                @Override
                public void onItemClicked(final View view,
                                          final int parentPosition,
                                          final int childPosition) {
                }

                @Override
                public void onItemClicked(final View view,
                                          int position) {
                    final Intent i = new Intent(FindFlightActivity.this, BookingActivity.class);
                    if (position >= 0) {
                        Schedule schedule = mSchedules.get(position);
                        final Gson gson = new GsonBuilder().create();
                        final Type type = new TypeToken<Schedule>() {
                        }.getType();
                        session.setSchedule(gson.toJson(schedule, type));
                        Log.e(TAG, schedule.toString());
                        i.putExtra(Constant.NUM_PEOPLE, Integer.valueOf(num_people));
                        startActivity(i);
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_flight);
        setTitle("Find Flight");
        session = new SessionManager(this);
        mSchedules = new ArrayList<>();
        travelClassesList = session.getTravelClasses();
        airportsList = session.getAirports();
        for (int x = 0; x < 9; x++) {
            people[x] = String.format("%1$d people", (x + 1));
        }

        //Creating the instance of ArrayAdapter containing list of language names
        final AirportsAdapter adapterOriginAirports = new AirportsAdapter
                (this, android.R.layout.select_dialog_item, airportsList);
        final AirportsAdapter adapterDestinationAirports = new AirportsAdapter
                (this, android.R.layout.select_dialog_item, airportsList);

        TravelClassSpinnerAdapter adapterTravelClass = new TravelClassSpinnerAdapter
                (this, travelClassesList);
        ArrayAdapter<String> adapterPeople = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, people);

        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView originAirport = (AutoCompleteTextView) findViewById(R.id.departure);
        originAirport.setThreshold(1);//will start working from first character
        originAirport.setAdapter(adapterOriginAirports);//setting the adapter data into the AutoCompleteTextView
        originAirport.setTextColor(Color.RED);

        final AutoCompleteTextView destinationAirport = (AutoCompleteTextView) findViewById(R.id.destination);
        destinationAirport.setThreshold(1);//will start working from first character
        destinationAirport.setAdapter(adapterDestinationAirports);//setting the adapter data into the AutoCompleteTextView
        destinationAirport.setTextColor(Color.RED);

        final Spinner spnTravelClass = (Spinner) findViewById(R.id.travel_class);
        spnTravelClass.setAdapter(adapterTravelClass);
        final Spinner spnNumPeople = (Spinner) findViewById(R.id.num_people);
        spnNumPeople.setAdapter(adapterPeople);

        final AppCompatEditText departureDate = (AppCompatEditText) findViewById(R.id.departure_date);
        final AppCompatEditText returnDate = (AppCompatEditText) findViewById(R.id.return_date);

        final AppCompatButton btnCheckAvailability = (AppCompatButton) findViewById(R.id.check_availability);
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestManager.findFlights(session,
                        FindFlightActivity.this,
                        adapterOriginAirports.getItemByName(
                                originAirport.getText().toString()).id,
                        adapterDestinationAirports.getItemByName(
                                destinationAirport.getText().toString()).id,
                        departureDate.getText().toString(),
                        returnDate.getText().toString(),
                        requestHandler);
                num_people = spnNumPeople.getSelectedItem().toString().substring(0, 1);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleadapter = new ScheduleAdapter();
        scheduleadapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(scheduleadapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.logout:
                logout();
                break;
            default:
                break;
        }

        return true;
    }

    public void logout() {
        session.logout();
        viewSplashScreen();
    }

    private void viewSplashScreen() {
        final Intent intent = new Intent(FindFlightActivity.this, SplashScreenActivity.class);
        finish();
        startActivity(intent);
    }
}
