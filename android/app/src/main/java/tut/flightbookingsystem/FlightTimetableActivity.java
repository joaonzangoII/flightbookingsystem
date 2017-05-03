package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.AirportsAdapter;
import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Schedule;

public class FlightTimetableActivity extends AppCompatActivity {
    private SessionManager session;
    private List<Schedule> mSchedules;
    private List<Airport> airportsList = new ArrayList<>();

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                final Gson gson = new GsonBuilder().create();
                final Type type = new TypeToken<List<Schedule>>() {
                }.getType();
                mSchedules = gson.fromJson(data.getString(Constant.STR_SCHEDULE), type);
                if (mSchedules != null) {
                    if (mSchedules.size() == 0) {
                        Utils.showDialog(FlightTimetableActivity.this);
                    } else {
                        final Intent intent = new Intent(FlightTimetableActivity.this, QueryResultsActivity.class);
                        intent.putExtra(Constant.DATA_BUNDLE, data);
                        intent.putExtra(Constant.TRAVEL_CLASS_ID, 0);
                        intent.putExtra(Constant.NUM_PEOPLE, 0);
                        startActivity(intent);
                    }
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        setTitle("Flight Timetable");

        session = new SessionManager(this);
        mSchedules = new ArrayList<>();
        airportsList = session.getAirports();

        final AirportsAdapter originAirportsAdapter = new AirportsAdapter
                (this, R.layout.spinners_item_layout, airportsList);
        final AirportsAdapter destinationAirportsAdapter = new AirportsAdapter
                (this, R.layout.spinners_item_layout, airportsList);

        final Spinner originAirport = (Spinner) findViewById(R.id.departure);
        originAirport.setAdapter(originAirportsAdapter);

        final Spinner destinationAirport = (Spinner) findViewById(R.id.destination);
        destinationAirport.setAdapter(destinationAirportsAdapter);

        final AppCompatEditText departureDate = (AppCompatEditText) findViewById(R.id.departure_date);

        final AppCompatButton btnCheckAvailability = (AppCompatButton) findViewById(R.id.check_availability);
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RequestManager.getTimetable(session,
                        FlightTimetableActivity.this,
                        originAirport.getSelectedItemId(),
                        destinationAirport.getSelectedItemId(),
                        departureDate.getText().toString(),
                        requestHandler);
            }
//            }
        });
    }
}
