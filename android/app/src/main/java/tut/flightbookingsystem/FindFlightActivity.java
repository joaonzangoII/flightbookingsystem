package tut.flightbookingsystem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tut.flightbookingsystem.adapter.AirportsSpinnerAdapter;
import tut.flightbookingsystem.adapter.PeopleAdapter;
import tut.flightbookingsystem.adapter.TravelClassSpinnerAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Schedule;
import tut.flightbookingsystem.model.TravelClass;
import tut.flightbookingsystem.util.Constant;
import tut.flightbookingsystem.util.Utils;

public class FindFlightActivity extends BaseActivity {
    private static String TAG = FindFlightActivity.class.getName();
    private List<Airport> airportsList = new ArrayList<>();
    private List<TravelClass> travelClassesList = new ArrayList<>();
    private List<String> people = new ArrayList<>();
    private SessionManager session;
    private List<Schedule> mSchedules;
    private String num_people;
    private long travel_class_id;
    private AppCompatEditText departureDate;
    private AppCompatEditText returnDate;
    private DatePickerDialog departureDatePickerDialog;
    private DatePickerDialog returnDatePickerDialog;
    private AirportsSpinnerAdapter originAirportsSpinnerAdapter;
    private AirportsSpinnerAdapter destinationAirportsSpinnerAdapter;

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                if (data.getBoolean(Constant.IS_GETTING_AIRPORTS)) {
                    originAirportsSpinnerAdapter.setItems(session.getAirports());
                    destinationAirportsSpinnerAdapter.setItems(session.getAirports());
                } else {
                    final Gson gson = new GsonBuilder().create();
                    final Type type = new TypeToken<List<Schedule>>() {
                    }.getType();
                    mSchedules = gson.fromJson(data.getString(Constant.STR_SCHEDULE), type);

                    if (mSchedules != null) {
                        if (mSchedules.size() == 0) {
                            Utils.showDialog(FindFlightActivity.this);
                        } else {
                            final Bundle args = new Bundle();
                            args.putBundle(Constant.DATA_BUNDLE, data);
                            args.putInt(Constant.TRAVEL_CLASS_ID, (int) travel_class_id);
                            args.putInt(Constant.NUM_PEOPLE, Integer.valueOf(num_people));
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
        setContentView(R.layout.activity_find_flight);
        setTitle("Find Flight");
        session = new SessionManager(this);
        mSchedules = new ArrayList<>();
        travelClassesList = session.getTravelClasses();
        airportsList = session.getAirports();

        for (int x = 0; x < 9; x++) {
            people.add(String.format("%1$d people", (x + 1)));
        }

        originAirportsSpinnerAdapter = new AirportsSpinnerAdapter
                (this, R.layout.spinners_item_layout, airportsList);
        destinationAirportsSpinnerAdapter = new AirportsSpinnerAdapter
                (this, R.layout.spinners_item_layout, airportsList);

        final TravelClassSpinnerAdapter adapterTravelClass = new TravelClassSpinnerAdapter
                (this, R.layout.spinners_item_layout, travelClassesList);

        final PeopleAdapter adapterPeople = new PeopleAdapter
                (this, R.layout.spinners_item_layout, people);

        final Spinner originAirport = (Spinner) findViewById(R.id.departure);
        originAirport.setAdapter(originAirportsSpinnerAdapter);

        final Spinner destinationAirport = (Spinner) findViewById(R.id.destination);
        destinationAirport.setAdapter(destinationAirportsSpinnerAdapter);

        final Spinner spnTravelClass = (Spinner) findViewById(R.id.travel_class);
        spnTravelClass.setAdapter(adapterTravelClass);

        final Spinner spnNumPeople = (Spinner) findViewById(R.id.num_people);
        spnNumPeople.setAdapter(adapterPeople);

        departureDate = (AppCompatEditText) findViewById(R.id.departure_date);
        returnDate = (AppCompatEditText) findViewById(R.id.return_date);
        departureDate.setInputType(InputType.TYPE_NULL);
        returnDate.setInputType(InputType.TYPE_NULL);

        final Calendar c = Calendar.getInstance();
        setDate(departureDate,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        setDate(returnDate,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        final AppCompatButton btnCheckAvailability = (AppCompatButton) findViewById(R.id.check_availability);
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num_people = adapterPeople.getNumPeople(spnNumPeople.getSelectedItemId());
                travel_class_id = spnTravelClass.getSelectedItemId();
                RequestManager.findFlights(session,
                        FindFlightActivity.this,
                        originAirport.getSelectedItemId(),
                        destinationAirport.getSelectedItemId(),
                        departureDate.getText().toString(),
                        returnDate.getText().toString(),
                        travel_class_id,
                        num_people,
                        requestHandler);
            }
        });

        setDateTimeField();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        super.onOptionsItemSelected(item);
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_logout:
                logout();
                break;
            default:
                break;
        }

        return true;
    }

    private void setDateTimeField() {
        departureDate.setOnClickListener(this);
        returnDate.setOnClickListener(this);
        departureDatePickerDialog = datepicker(departureDate);
        returnDatePickerDialog = datepicker(returnDate);
        departureDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        returnDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    @Override
    public void onClick(final View view) {
        if (view == departureDate) {
            departureDatePickerDialog.show();
        } else if (view == returnDate) {
            returnDatePickerDialog.show();
        }
    }
}
