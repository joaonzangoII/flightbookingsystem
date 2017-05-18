package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import tut.flightbookingsystem.adapter.HomeAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.MainItem;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SessionManager session;
    private HomeAdapter myHomeAdapter;
    private List<Booking> myBookingsList = new ArrayList<>();
    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final boolean error = data.getBoolean(Constant.ERROR);
            if (!data.getBoolean(Constant.ERROR)) {
                final Gson gson = new GsonBuilder().create();
                final Type type = new TypeToken<List<Booking>>() {
                }.getType();
                myBookingsList = gson.fromJson(data.getString(Constant.MY_BOOKINGS), type);
                List<MainItem> items = new ArrayList<>();
                if (myBookingsList != null) {
                    items.add(new MainItem(1, String.format("%1$d", myBookingsList.size()), "Number of Bookings"));
                    items.add(new MainItem(2, String.format("%1$s", moneySpent(myBookingsList)), "Money Spent"));
                    items.add(new MainItem(3, String.format("%1$d", passengersBooked(myBookingsList)), "Passengers Booked"));
                }
                myHomeAdapter.setItems(items);

                //                if (myBookingsList != null) {
                //                    ((TextView) findViewById(R.id.num_of_bookings))
                //                            .setText(String.format("%1$d", myBookingsList.size()));
                //
                //                    ((TextView) findViewById(R.id.money_spent))
                //                            .setText(String.format("%1$s", moneySpent(myBookingsList)));
                //
                //                    ((TextView) findViewById(R.id.passengers_booked))
                //                            .setText(String.format("%1$d", passengersBooked(myBookingsList)));
                //                }
            }
            return false;
        }
    });

    public String moneySpent(final List<Booking> myBookingsList) {
        double total = 0.0;

        for (final Booking booking : myBookingsList) {
            total = total + booking.total;
        }

        final Locale locale2 = new Locale("en-ZA", "SOUTH AFRICA");
        final NumberFormat format = NumberFormat.getCurrencyInstance(locale2);
        format.setCurrency(Currency.getInstance("ZAR"));
        return format.format(total);
    }

    public int passengersBooked(final List<Booking> myBookingsList) {
        int total = 0;

        for (final Booking booking : myBookingsList) {
            total = total + booking.passengers.size();
        }

        return total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        session = new SessionManager(this);
        RequestManager.getMyBookings(session, MainActivity.this, requestHandler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final View header = navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        ((TextView) header.findViewById(R.id.user_logged_name))
                .setText(session.getLoggedInUser().name);
        ((TextView) header.findViewById(R.id.user_logged_email))
                .setText(session.getLoggedInUser().email);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(FindFlightActivity.class);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        myHomeAdapter = new HomeAdapter();
        // myHomeAdapter.setItems(myBookingsList);
        // myHomeAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(myHomeAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        if (id == R.id.book) {
            goToActivity(FindFlightActivity.class);
        } else if (id == R.id.timetable) {
            goToActivity(FlightTimetableActivity.class);
        } else if (id == R.id.my_bookings) {
            goToActivity(MyBookingsActivity.class);
        } else if (id == R.id.foods) {
            goToActivity(FoodsActivity.class);
        } else if (id == R.id.drinks) {
            goToActivity(DrinksActivity.class);
        } else {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
