package tut.flightbookingsystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.AirplaneFlightSeatAdapter;
import tut.flightbookingsystem.listener.MyDialogListener;
import tut.flightbookingsystem.listener.OnSeatSelected;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.CenterItem;
import tut.flightbookingsystem.model.EdgeItem;
import tut.flightbookingsystem.model.EmptyItem;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Passenger;

public class PassengerSeatsDialogFragment extends AppCompatDialogFragment
        implements OnSeatSelected {
    private MyDialogListener myDialogListener;
    private AirplaneFlightSeatAdapter adapter;


    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_airplane_seat_selection, null, false);
    }

    @Override
    public void onViewCreated(final View view,
                              final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        final SessionManager session = new SessionManager(getActivity());
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            getDialog().setTitle("Select Available Seats");
        }

        setupView(getContext(), session, view);
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu,
                                    final MenuInflater inflater) {
        menu.clear();
        // getActivity().getMenuInflater().inflate(R.menu.alert_dialog_input, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupView(final Context context,
                           final SessionManager session,
                           final View itemView) {
        List<FlightSeat> flightSeatsList = session.getFlightSeats();
        final Passenger passenger = session.getPassenger();
        int COLUMNS = 0;
        int addMore = 0;
        String classNName = "First";
        if (flightSeatsList != null &&
                flightSeatsList.size() > 0) {
            classNName = flightSeatsList.get(0).travel_class.name;
            if (classNName.equals("First")) {
                COLUMNS = 2;
            } else if (classNName.equals("Business")) {
                COLUMNS = 7;
            } else {
                COLUMNS = 10;
            }
            /*if (COLUMNS == 2) {
                addMore = 1;
            } else {
                addMore = 2;
            }*/
            COLUMNS = COLUMNS + addMore;
            final List<AbstractItem> items = new ArrayList<>();
            for (int i = 0; i < flightSeatsList.size(); i++) {
                final int remainder = i % COLUMNS;
                AbstractItem item;
                final long id = flightSeatsList.get(i).id;
                final String seatNumber = String.valueOf(flightSeatsList.get(i).number);
                boolean isAvailable = flightSeatsList.get(i).available;

                for(Passenger p :session.getBookingPassengers()) {
                    if (p.flight_seat_id == id) {
                        isAvailable= false;
                    }
                }

                if (COLUMNS == 2) {
                    if (remainder == 0) {
                        item = new EdgeItem(id, seatNumber, !isAvailable);
                        //item = new EmptyItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 1) {
                        item = new EdgeItem(id, seatNumber, !isAvailable);
                    } else {
                        item = new EmptyItem(id, seatNumber, !isAvailable);
                    }
                    items.add(item);
                } else if (COLUMNS == 7) {
                    if (remainder == 0 || remainder == 1 || remainder == 5 || remainder == 6) {
                        item = new EdgeItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 2) {
                        //item = new EmptyItem(id, seatNumber, !isAvailable);
                        item = new CenterItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 3) {
                        item = new CenterItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 4) {
                        item = new CenterItem(id, seatNumber, !isAvailable);
                        //item = new EmptyItem(id, seatNumber, !isAvailable);
                    } else {
                        item = new EmptyItem(id, seatNumber, !isAvailable);
                    }
                    items.add(item);
                } else {
                    if (remainder == 0 || remainder == 1 || remainder == 2 ||
                            remainder == 7 || remainder == 8 || remainder == 9) {
                        item = new EdgeItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 3) {
                        //item = new EmptyItem(id, seatNumber, !isAvailable);
                        item = new CenterItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 4 || remainder == 5) {
                        item = new CenterItem(id, seatNumber, !isAvailable);
                    } else if (remainder == 6) {
                        item = new CenterItem(id, seatNumber, !isAvailable);
                        //item = new EmptyItem(id, seatNumber, !isAvailable);
                    } else {
                        item = new EmptyItem(id, seatNumber, !isAvailable);
                    }
                    items.add(item);
                }
            }

            final GridLayoutManager manager = new GridLayoutManager(context, COLUMNS);
            final RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.my_recycler_view);
            recyclerView.setLayoutManager(manager);
            adapter = new AirplaneFlightSeatAdapter(context, items);
            recyclerView.setAdapter(adapter);
            adapter.refreshLayout();

            final AppCompatButton btnSelectSeat = (AppCompatButton) itemView.findViewById(R.id.btn_select_seat);
            btnSelectSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getSelectedItems().size() > 0) {
                        myDialogListener.userSelectedAValue(items.get(adapter.getSelectedItems().get(0)));
                        dismiss();
                    }
                }
            });
        }

    }

    @Override
    public void onSeatSelected(AbstractItem item) {

    }

    public void setMyDialogListener(MyDialogListener myDialogListener) {
        this.myDialogListener = myDialogListener;
    }
}
