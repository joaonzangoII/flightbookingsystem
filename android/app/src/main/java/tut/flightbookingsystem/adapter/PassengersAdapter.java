package tut.flightbookingsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Passenger;

public class PassengersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;
    private List<Passenger> items = Collections.emptyList();

    public PassengersAdapter() {
    }

    public void setItems(final List<Passenger> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyScheduleHolder(layoutInflater.inflate(R.layout.passengers_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyScheduleHolder vH = (MyScheduleHolder) holder;
        final Passenger passenger = getItem(position);
        if (passenger != null) {
            vH.edt_first_name.setText(passenger.last_name);
            vH.edt_middle_name.setText(passenger.middle_name);
            vH.edt_last_name.setText(passenger.last_name);
            vH.edt_id_number.setText(passenger.id_number);
            vH.txt_date_of_birth.setText(passenger.date_of_birth);
            vH.itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
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
        public EditText edt_first_name;
        public EditText edt_middle_name;
        public EditText edt_last_name;
        public EditText edt_id_number;
        public TextView txt_date_of_birth;
        public Button btn_add_meal;

        public MyScheduleHolder(View itemView) {
            super(itemView);
            edt_first_name = (EditText) itemView.findViewById(R.id.first_name);
            edt_middle_name = (EditText) itemView.findViewById(R.id.middle_name);
            edt_last_name = (EditText) itemView.findViewById(R.id.last_name);
            edt_id_number = (EditText) itemView.findViewById(R.id.id_number);
            txt_date_of_birth = (TextView) itemView.findViewById(R.id.date_of_birth);
            btn_add_meal = (Button) itemView.findViewById(R.id.add_meal);

        }
    }
}
