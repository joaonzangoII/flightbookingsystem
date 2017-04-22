package tut.flightbookingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.ScheduleAdapter;
import tut.flightbookingsystem.listener.RecyclerClickListener.OnItemClickCallback;
import tut.flightbookingsystem.model.Schedule;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getName();
    private String[] airports = {"OR Tambo International", "Lanseria", "King Shaka"};
    private SessionManager session;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleadapter;
    private List<Schedule> mSchedules;
    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final Gson gson = new GsonBuilder().create();
            final Type type = new TypeToken<List<Schedule>>() {
            }.getType();
            mSchedules = gson.fromJson(data.getString(Constant.STR_SCHEDULE), type);
            scheduleadapter.setItems(mSchedules);
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
                    final Intent i = new Intent(MainActivity.this, BookingActivity.class);
                    position = position - 2;
                    if (position >= 0) {
                        Schedule schedule = mSchedules.get(position);
                        Log.e(TAG, schedule.toString());
                        i.putExtra(Constant.SCHEDULE, schedule);
                        /*final Gson gson = new GsonBuilder().create();
                        final Type type = new TypeToken<CategoriaModel>() {
                        }.getType();
                        sessionManager.setCategoria(gson.toJson(categoria));*/
                        startActivity(i);
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(this);
        mSchedules = new ArrayList<>();
        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, airports);
        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView departureAirport = (AutoCompleteTextView) findViewById(R.id.departure);
        departureAirport.setThreshold(1);//will start working from first character
        departureAirport.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        departureAirport.setTextColor(Color.RED);

        final AutoCompleteTextView destinationAirport = (AutoCompleteTextView) findViewById(R.id.destination);
        destinationAirport.setThreshold(1);//will start working from first character
        destinationAirport.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        destinationAirport.setTextColor(Color.RED);

        final AppCompatButton btnCheckAvailability = (AppCompatButton) findViewById(R.id.check_availability);
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestManager.checkAvailability(session, requestHandler);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleadapter = new ScheduleAdapter();
        scheduleadapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(scheduleadapter);
    }
}
