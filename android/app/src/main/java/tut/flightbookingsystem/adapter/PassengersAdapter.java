package tut.flightbookingsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.AircraftSeat;
import tut.flightbookingsystem.model.Passenger;

public class PassengersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;
    private List<Passenger> items = Collections.emptyList();
    private List<AircraftSeat> itemsAircraftSeats = Collections.emptyList();

    public PassengersAdapter() {
    }

    public void setItems(final List<Passenger> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setAircrafSeats(final List<AircraftSeat> itemsAircraftSeats) {
        this.itemsAircraftSeats = itemsAircraftSeats;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyScheduleHolder(layoutInflater.inflate(R.layout.passengers_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 final int position) {
        final MyScheduleHolder vH = (MyScheduleHolder) holder;
        final Passenger passenger = getItem(position);
        if (passenger != null) {
            vH.txt_title.setText(String.format("Passenger: %1$s", (position + 1)));
            vH.edt_first_name.setText(passenger.last_name);
            vH.edt_middle_name.setText(passenger.middle_name);
            vH.edt_last_name.setText(passenger.last_name);
            vH.edt_id_number.setText(passenger.id_number);
            vH.txt_date_of_birth.setText(passenger.date_of_birth);
            vH.btn_add_meal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            vH.itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));

            setListenerAndData(vH.edt_first_name, "first_name", passenger);
            setListenerAndData(vH.edt_middle_name, "middle_name", passenger);
            setListenerAndData(vH.edt_last_name, "last_name", passenger);
            setListenerAndData(vH.edt_id_number, "id_number", passenger);
            setListenerAndData(vH.txt_date_of_birth, "date_of_birth", passenger);

            final AircraftSeatSpinnerAdapter aircraftSeatAdapter = new AircraftSeatSpinnerAdapter
                    (vH.itemView.getContext(), R.layout.spinners_item_layout, itemsAircraftSeats);

            vH.spn_select_seat_id.setAdapter(aircraftSeatAdapter);
            vH.spn_select_seat_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView,
                                           View view,
                                           int i,
                                           long id) {
                    passenger.aircraft_seat_id = id;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    @Nullable
    public Passenger getItem(final int position) {
        return items.get(position);
    }

    public void setOnItemClickCallback(RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    class MyScheduleHolder extends RecyclerView.ViewHolder {
        public TextView txt_title;
        public EditText edt_first_name;
        public EditText edt_middle_name;
        public EditText edt_last_name;
        public EditText edt_id_number;
        public EditText txt_date_of_birth;
        public Button btn_add_meal;
        public Spinner spn_select_seat_id;

        public MyScheduleHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.passengerNumber);
            edt_first_name = (EditText) itemView.findViewById(R.id.first_name);
            edt_middle_name = (EditText) itemView.findViewById(R.id.middle_name);
            edt_last_name = (EditText) itemView.findViewById(R.id.last_name);
            edt_id_number = (EditText) itemView.findViewById(R.id.id_number);
            txt_date_of_birth = (EditText) itemView.findViewById(R.id.date_of_birth);
            spn_select_seat_id = (Spinner) itemView.findViewById(R.id.select_seat_id);
            btn_add_meal = (Button) itemView.findViewById(R.id.add_meal);
        }
    }

    public void setListenerAndData(final View view,
                                   final String column,
                                   final Passenger passenger) {
        final EditText edt = ((EditText) (view));
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int i,
                                          int i1,
                                          int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i,
                                      int i1,
                                      int i2) {
                switch (column) {
                    case "first_name":
                        passenger.first_name = edt.getText().toString();
                        break;
                    case "middle_name":
                        passenger.middle_name = edt.getText().toString();
                        break;
                    case "last_name":
                        passenger.last_name = edt.getText().toString();
                        break;
                    case "id_number":
                        passenger.id_number = edt.getText().toString();
                        break;
                    case "date_of_birth":
                        passenger.date_of_birth = edt.getText().toString();
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
