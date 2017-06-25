package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.MyBookingsAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.util.Constant;

public class MyBookingsActivity extends BaseActivity {
    public static final String TAG = MyBookingsActivity.class.getSimpleName();
    private List<Booking> myBookingsList = new ArrayList<>();
    private MyBookingsAdapter myBookingsAdapter;
    private SessionManager session;
    private ProgressBar progressBar;

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
                    if (position >= 0) {
                        final Booking booking = myBookingsList.get(position);
                        final Gson gson = new GsonBuilder().create();
                        final Bundle args = new Bundle();
                        args.putString(Constant.BOOKING, gson.toJson(booking));
                        goToActivity(MyBookingsDetailActivity.class, args);
                    }
                }
            };

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final boolean error = data.getBoolean(Constant.ERROR);
            if (!data.getBoolean(Constant.ERROR)) {
                final Gson gson = new GsonBuilder().create();
                final Type type = new TypeToken<List<Booking>>() {
                }.getType();
                progressBar.setVisibility(View.GONE);
                myBookingsList = gson.fromJson(data.getString(Constant.MY_BOOKINGS), type);
                myBookingsAdapter.setItems(myBookingsList);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        setTitle("My Bookings");
        session = new SessionManager(this);
        RequestManager.getMyBookings(session, MyBookingsActivity.this, requestHandler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myBookingsAdapter = new MyBookingsAdapter();
        myBookingsAdapter.setItems(myBookingsList);
        myBookingsAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(myBookingsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return true;
    }
}
