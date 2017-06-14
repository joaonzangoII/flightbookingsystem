package tut.flightbookingsystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class PassengerSeatsDialogFragment extends DialogFragment implements OnSeatSelected {
    private MyDialogListener myDialogListener;
    private AirplaneFlightSeatAdapter adapter;
    private List<FlightSeat> flightSeatsList = new ArrayList<>();

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
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Select Available Seats");
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }

        setupView(getContext(), view);
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
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

        //        if (id == R.id.action_save) {
        //            // handle confirmation button click here
        //            return true;
        //        } else
        if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupView(final Context context,
                           final View itemView) {
        int COLUMNS = 0;
        int addMore = 0;
        final SessionManager session = new SessionManager(context);
        flightSeatsList = session.getFlightSeats();
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
                if (COLUMNS == 2) {
                    if (remainder == 0) {
                        items.add(new EdgeItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                        //items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 1) {
                        items.add(new EdgeItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else {
                        items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    }
                } else if (COLUMNS == 7) {
                    if (remainder == 0 || remainder == 1 || remainder == 5 || remainder == 6) {
                        items.add(new EdgeItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 2) {
                        //items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                        items.add(new CenterItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 3) {
                        items.add(new CenterItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 4) {
                        items.add(new CenterItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                        // items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else {
                        items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    }
                } else {
                    if (remainder == 0 || remainder == 1 || remainder == 2 ||
                            remainder == 7 || remainder == 8 || remainder == 9) {
                        items.add(new EdgeItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 3) {
                        //items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                        items.add(new CenterItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 4 || remainder == 5) {
                        items.add(new CenterItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else if (remainder == 6) {
                        items.add(new CenterItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                        //items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    } else {
                        items.add(new EmptyItem(flightSeatsList.get(i).id, String.valueOf(flightSeatsList.get(i).number)));
                    }
                }
            }

            final GridLayoutManager manager = new GridLayoutManager(context, COLUMNS);
            final RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.my_recycler_view);
            recyclerView.setLayoutManager(manager);
            adapter = new AirplaneFlightSeatAdapter(context, items);
            recyclerView.setAdapter(adapter);

            final AppCompatButton btnSelectSeat = (AppCompatButton) itemView.findViewById(R.id.btn_select_seat);
            // btnSelectSeat.setVisibility(View.GONE);
            btnSelectSeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getSelectedItems().size() > 0) {
                        myDialogListener.userSelectedAValue(items.get(adapter.getSelectedItems().get(0)));
                        // listener is object of your MyDialogListener, which you have set from // Activity.
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
