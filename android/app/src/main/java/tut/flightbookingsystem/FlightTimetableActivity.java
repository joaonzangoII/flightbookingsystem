package tut.flightbookingsystem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.View;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tut.flightbookingsystem.adapter.AirportsAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Schedule;

public class FlightTimetableActivity extends BaseActivity {
    private SessionManager session;
    private List<Schedule> mSchedules;
    private List<Airport> airportsList = new ArrayList<>();
    private AppCompatEditText departureDate;
    private DatePickerDialog departureDatePickerDialog;
    private AirportsAdapter originAirportsAdapter;
    private AirportsAdapter destinationAirportsAdapter;


    //private SwipeRefreshLayout swipeRefreshLayout;
    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                 if (data.getBoolean(Constant.IS_GETTING_AIRPORTS )) {
                    originAirportsAdapter.setItems(session.getAirports());
                    destinationAirportsAdapter.setItems(session.getAirports());

                } else {
                     final Gson gson = new GsonBuilder().create();
                     final Type type = new TypeToken<List<Schedule>>() {
                     }.getType();
                     mSchedules = gson.fromJson(data.getString(Constant.STR_SCHEDULE), type);
                    if (mSchedules != null) {
                        if (mSchedules.size() == 0) {
                            Utils.showDialog(FlightTimetableActivity.this);
                        } else {

                            final Bundle args = new Bundle();
                            args.putBundle(Constant.DATA_BUNDLE, data);
                            args.putInt(Constant.TRAVEL_CLASS_ID, 0);
                            args.putInt(Constant.NUM_PEOPLE, 0);
                            goToActivity(QueryResultsActivity.class, args);
                        }
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

        originAirportsAdapter = new AirportsAdapter
                (this, R.layout.spinners_item_layout, airportsList);
        destinationAirportsAdapter = new AirportsAdapter
                (this, R.layout.spinners_item_layout, airportsList);

        final Spinner originAirport = (Spinner) findViewById(R.id.departure);
        originAirport.setAdapter(originAirportsAdapter);

        final Spinner destinationAirport = (Spinner) findViewById(R.id.destination);
        destinationAirport.setAdapter(destinationAirportsAdapter);

        departureDate = (AppCompatEditText) findViewById(R.id.departure_date);
        departureDate.setInputType(InputType.TYPE_NULL);

        final Calendar c = Calendar.getInstance();
        setDate(departureDate,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

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

        setDateTimeField();

        //        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        //        swipeRefreshLayout.setOnRefreshListener(this);
        //
        //        /**
        //         * Showing Swipe Refresh animation on activity create
        //         * As animation won't start on onCreate, post runnable is used
        //         */
        //        swipeRefreshLayout.post(new Runnable() {
        //                                    @Override
        //                                    public void run() {
        //                                        swipeRefreshLayout.setRefreshing(true);
        //                                        RequestManager.getAirports(session, requestHandler);
        //                                    }
        //                                }
        //        );
    }

    @Override
    public void onRefresh() {
        // showing refresh animation before making http call
        //        swipeRefreshLayout.setRefreshing(true);
        //        RequestManager.getAirports(session, requestHandler);
    }

    private void setDateTimeField() {
        departureDate.setOnClickListener(this);
        departureDatePickerDialog = datepicker(departureDate);
    }

    @Override
    public void onClick(final View view) {
        if (view == departureDate) {
            departureDatePickerDialog.show();
        }
    }
}
