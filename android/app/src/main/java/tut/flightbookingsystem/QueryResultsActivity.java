package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tut.flightbookingsystem.adapter.ScheduleAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Schedule;

public class QueryResultsActivity extends BaseActivity {
    private SessionManager session;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleadapter;
    private List<Schedule> mSchedules;
    private static String TAG = QueryResultsActivity.class.getName();
    private int num_people;
    private int travel_class_id;

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                final List<FlightSeat> flighSeatsList = session.getFlightSeats();
                if (flighSeatsList.size() >= num_people) {
                    final Bundle args = new Bundle();
                    args.putSerializable(Constant.SCHEDULE, session.getSchedule());
                    args.putInt(Constant.NUM_PEOPLE, num_people);
                    args.putInt(Constant.TRAVEL_CLASS_ID, travel_class_id);
                    goToActivity(BookingActivity.class, args);
                } else {
                    Toast.makeText(QueryResultsActivity.this,
                            String.format("There are are only %1$d available", flighSeatsList.size()),
                            Toast.LENGTH_SHORT).show();
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
                    if (num_people > 0) {
                        if (position >= 0) {
                            final Schedule schedule = mSchedules.get(position);
                            final String strSchedule = new GsonBuilder()
                                    .create()
                                    .toJson(schedule);
                            session.setSchedule(strSchedule);
                            RequestManager.getFlightSeats(session, schedule.flight_id, travel_class_id, requestHandler);
                        }
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_results);
        setTitle("Flights");
        session = new SessionManager(this);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        final Type type = new TypeToken<List<Schedule>>() {
        }.getType();

        final Bundle args = getIntent().getBundleExtra(Constant.DATA);
        num_people = args.getInt(Constant.NUM_PEOPLE, 0);
        travel_class_id = args.getInt(Constant.TRAVEL_CLASS_ID, 0);
        final Bundle data = args.getBundle(Constant.DATA_BUNDLE);
        mSchedules = new GsonBuilder()
                .create()
                .fromJson(data.getString(Constant.STR_SCHEDULE), type);

        ((TextView) findViewById(R.id.number_flights))
                .setText(getResources().getQuantityString(R.plurals.flights, mSchedules.size(), mSchedules.size()));
        ((TextView) findViewById(R.id.from_to))
                .setText(String.format("%1$s - %2$s",
                        mSchedules.get(0).origin_airport.name,
                        mSchedules.get(0).destination_airport.name));

        ((TextView) findViewById(R.id.date))
                .setText(String.format("%1$s", mSchedules.get(0).date));

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleadapter = new ScheduleAdapter();
        scheduleadapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(scheduleadapter);

        scheduleadapter.setItems(mSchedules);
    }


}
