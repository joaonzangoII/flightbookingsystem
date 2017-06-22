package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.MyBookingsDetailAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.Passenger;

public class MyBookingsDetailActivity extends BaseActivity {
    private MyBookingsDetailAdapter myBookingsDetailAdapter;
    private List<Passenger> passengersList = new ArrayList<>();
    private int position = 0;

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final boolean error = data.getBoolean(Constant.ERROR);
            if (!error) {
                if (data.getBoolean(Constant.IS_BOOKING)) {
                    final Gson gson = new GsonBuilder().create();
                    final Type type = new TypeToken<Booking>() {
                    }.getType();
                    final Booking mBooking = gson.fromJson(data.getString(Constant.BOOKING), type);
                    passengersList = mBooking.passengers;
                    myBookingsDetailAdapter.itemsChanged(mBooking, mBooking.passengers, position);
                    goToActivity(MyBookingsActivity.class, true);
                } else {
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
                                          final int p) {
                    position = p - 1;
                    final Passenger passenger = passengersList.get(position);
                    switch (view.getId()) {
                        case R.id.btn_delete_drink:
                            if (passenger.drink != null) {
                                addOrUpdateDrink(passenger, "delete", requestHandler);

                            } else {
                                Toast.makeText(MyBookingsDetailActivity.this,
                                        "You have not added a drink yet",
                                        Toast.LENGTH_SHORT).show();
                                ((AppCompatButton) view).setVisibility(View.GONE);
                            }
                            break;
                        default:
                            final Gson gson = new GsonBuilder().create();
                            final Bundle bundle = new Bundle();
                            session.setPassenger(gson.toJson(passenger));
                            //bundle.putSerializable("PASSENGER", passenger);
                            bundle.putParcelable("PASSENGER", passenger);
                            goToActivity(PassengerDetailActivity.class, bundle);
                            break;
                    }
                }
            };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings_detail);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        final Bundle args = getIntent().getBundleExtra(Constant.DATA);
        final String strBooking = args.getString(Constant.BOOKING);
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<Booking>() {
        }.getType();
        //passengerLayout = (LinearLayout) findViewById(R.id.passengerLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myBookingsDetailAdapter = new MyBookingsDetailAdapter(this);
        myBookingsDetailAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(myBookingsDetailAdapter);

        final Booking mBooking = gson.fromJson(strBooking, type);
        if (mBooking != null) {
            setTitle(mBooking.booking_number);
            passengersList = mBooking.passengers;
            myBookingsDetailAdapter.setItems(mBooking, mBooking.passengers);
        }
    }
}
