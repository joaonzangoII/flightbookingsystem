package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.AirplaneFlightSeatAdapter;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.listener.OnSeatSelected;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.CenterItem;
import tut.flightbookingsystem.model.EdgeItem;
import tut.flightbookingsystem.model.EmptyItem;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Schedule;

public class SelectSeatActivity extends BaseActivity implements OnSeatSelected {
    private static int COLUMNS = 5;
    private int addMore = 0;
    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                if (data.getBoolean(Constant.IS_BOOKING)) {
                    final Bundle args = new Bundle();
                    args.putString(Constant.BOOKING, data.getString(Constant.BOOKING));
                    goToActivity(BookingConfirmationActivity.class, args);
                    finish();
                } else {
                    setupView();
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);
        setTitle("Select Seat");
        final Schedule schedule = session.getSchedule();
        final Bundle args = getIntent().getBundleExtra(Constant.DATA);
        final int travel_class_id = args.getInt(Constant.TRAVEL_CLASS_ID, 0);
        final int numPeople = args.getInt(Constant.NUM_PEOPLE, 0);

        RequestManager.getFlightSeats(session, schedule.flight_id, travel_class_id, requestHandler);
    }

    private void setupView() {
        final List<FlightSeat> fligtSeats = session.getFlightSeats();
        String classNName = "First";
        if (fligtSeats != null) {
            classNName = fligtSeats.get(0).travel_class.name;
            if (classNName.equals("First")) {
                COLUMNS = 2;
            } else if (classNName.equals("Business")) {
                COLUMNS = 7;
            } else {
                COLUMNS = 10;
            }

            final List<AbstractItem> items = new ArrayList<>();
            for (int i = 0; i < fligtSeats.size(); i++) {
                final int remainder = i % COLUMNS;
                if (COLUMNS == 2) {
                    if (remainder == 0) {
                        items.add(new EdgeItem(String.valueOf(i)));
                        items.add(new EmptyItem(String.valueOf(i)));
                    } else if (remainder == 1) {
                        items.add(new EdgeItem(String.valueOf(i)));
                    } else {
                        items.add(new EmptyItem(String.valueOf(i)));
                    }
                } else if (COLUMNS == 7) {
                    if (remainder == 0 || remainder == 1 || remainder == 5 || remainder == 6) {
                        items.add(new EdgeItem(String.valueOf(i)));
                    } else if (remainder == 2) {
                        items.add(new EmptyItem(String.valueOf(i)));
                        items.add(new CenterItem(String.valueOf(i)));
                    } else if (remainder == 3) {
                        items.add(new CenterItem(String.valueOf(i)));
                    } else if (remainder == 4) {
                        items.add(new CenterItem(String.valueOf(i)));
                        items.add(new EmptyItem(String.valueOf(i)));
                    } else {
                        items.add(new EmptyItem(String.valueOf(i)));
                    }
                } else {
                    if (remainder == 0 || remainder == 1 || remainder == 2 ||
                            remainder == 7 || remainder == 8 || remainder == 9) {
                        items.add(new EdgeItem(String.valueOf(i)));
                    } else if (remainder == 3) {
                        items.add(new EmptyItem(String.valueOf(i)));
                        items.add(new CenterItem(String.valueOf(i)));
                    } else if (remainder == 4 || remainder == 5) {
                        items.add(new CenterItem(String.valueOf(i)));
                    } else if (remainder == 6) {
                        items.add(new CenterItem(String.valueOf(i)));
                        items.add(new EmptyItem(String.valueOf(i)));
                    } else {
                        items.add(new EmptyItem(String.valueOf(i)));
                    }
                }
            }

            if (COLUMNS == 2) {
                addMore = 1;
            } else {
                addMore = 2;
            }

            final GridLayoutManager manager = new GridLayoutManager(this, COLUMNS + addMore);
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
            recyclerView.setLayoutManager(manager);

            final AirplaneFlightSeatAdapter adapter = new AirplaneFlightSeatAdapter(this, items);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onSeatSelected(int count) {
        ((AppCompatTextView) findViewById(R.id.txt_seat_selected)).setText("" + count);
    }
}
