package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tut.flightbookingsystem.adapter.ScheduleAdapter;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Schedule;

public class QueryResultsActivity extends AppCompatActivity {
    private SessionManager session;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleadapter;
    private List<Schedule> mSchedules;
    private static String TAG = QueryResultsActivity.class.getName();
    private int num_people;
    private int travel_class_id;
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
                        final Intent i = new Intent(QueryResultsActivity.this, BookingActivity.class);
                        if (position >= 0) {
                            final Schedule schedule = mSchedules.get(position);
                            Log.e(TAG, schedule.toString());
                            session.setSchedule(new GsonBuilder()
                                    .create()
                                    .toJson(schedule));
                            i.putExtra(Constant.SCHEDULE, schedule);
                            i.putExtra(Constant.NUM_PEOPLE, num_people);
                            i.putExtra(Constant.TRAVEL_CLASS_ID, travel_class_id);
                            startActivity(i);
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

        final Type type = new TypeToken<List<Schedule>>() {
        }.getType();

        num_people = getIntent().getIntExtra(Constant.NUM_PEOPLE, 0);
        travel_class_id = getIntent().getIntExtra(Constant.TRAVEL_CLASS_ID, 0);
        final Bundle data = getIntent().getBundleExtra(Constant.DATA_BUNDLE);
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
