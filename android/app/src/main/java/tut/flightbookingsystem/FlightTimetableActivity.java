package tut.flightbookingsystem;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.AirportsAdapter;
import tut.flightbookingsystem.adapter.ScheduleAdapter;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Schedule;

public class FlightTimetableActivity extends AppCompatActivity {
    private SessionManager session;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleadapter;
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
                scheduleadapter.setItems(mSchedules);
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
                    //                    final Intent i = new Intent(MainActivity.this, BookingActivity.class);
                    //                    if (position >= 0) {
                    //                        Schedule schedule = mSchedules.get(position);
                    //                        Log.e(TAG, schedule.toString());
                    //                        i.putExtra(Constant.SCHEDULE, schedule);
                    //                        /*final Gson gson = new GsonBuilder().create();
                    //                        final Type type = new TypeToken<CategoriaModel>() {
                    //                        }.getType();
                    //                        sessionManager.setCategoria(gson.toJson(categoria));*/
                    //                        startActivity(i);
                    //                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        session = new SessionManager(this);
        mSchedules = new ArrayList<>();
        airportsList = session.getAirports();
        final AirportsAdapter adapterOriginAirports = new AirportsAdapter
                (this, android.R.layout.select_dialog_item, airportsList);
        final AirportsAdapter adapterDestinationAirports = new AirportsAdapter
                (this, android.R.layout.select_dialog_item, airportsList);

        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView originAirport = (AutoCompleteTextView) findViewById(R.id.departure);
        originAirport.setThreshold(1);//will start working from first character
        originAirport.setAdapter(adapterOriginAirports);//setting the adapter data into the AutoCompleteTextView
        originAirport.setTextColor(Color.RED);

        final AutoCompleteTextView destinationAirport = (AutoCompleteTextView) findViewById(R.id.destination);
        destinationAirport.setThreshold(1);//will start working from first character
        destinationAirport.setAdapter(adapterDestinationAirports);//setting the adapter data into the AutoCompleteTextView
        destinationAirport.setTextColor(Color.RED);

        final AppCompatTextView departureDate = (AppCompatTextView) findViewById(R.id.departure_date);

        final AppCompatButton btnCheckAvailability = (AppCompatButton) findViewById(R.id.check_availability);
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestManager.getTimetable(session,
                        FlightTimetableActivity.this,
                        adapterOriginAirports.getItemByName(
                                originAirport.getText().toString()).id,
                        adapterDestinationAirports.getItemByName(
                                destinationAirport.getText().toString()).id,
                        departureDate.getText().toString(),
                        requestHandler);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleadapter = new ScheduleAdapter();
        scheduleadapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(scheduleadapter);
    }
}
