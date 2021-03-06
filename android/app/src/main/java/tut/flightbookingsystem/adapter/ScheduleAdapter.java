package tut.flightbookingsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.util.Utils;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.listener.RecyclerClickListener.OnItemClickCallback;
import tut.flightbookingsystem.model.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    private List<Schedule> items = Collections.emptyList();

    public ScheduleAdapter() {
    }

    public void setItems(final List<Schedule> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyScheduleHolder(layoutInflater.inflate(R.layout.schedules_availability_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyScheduleHolder vH = (MyScheduleHolder) holder;
        final Schedule schedule = getItem(position);
        if (schedule != null) {
            if (schedule.flight != null) {
                if (schedule.flight.aircraft != null) {
                    vH.txt_flight.setText(String.format("%1$s", schedule.flight.aircraft.name));
                }
            }

            vH.txt_origin_iata_airport_code.setText(schedule.origin_airport.iata_airport_code);
            vH.txt_departure_date.setText(Utils.getDate(schedule.departure_time));
            vH.txt_departure_hour.setText(Utils.getTime(schedule.departure_time));

            vH.txt_destination_iata_airport_code.setText(schedule.destination_airport.iata_airport_code);
            vH.txt_arrival_date.setText(Utils.getDate(schedule.arrival_time));
            vH.txt_arrival_hour.setText(Utils.getTime(schedule.arrival_time));
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
    public Schedule getItem(final int position) {
        return items.get(position);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    class MyScheduleHolder extends RecyclerView.ViewHolder {
        public TextView txt_flight;
        public TextView txt_origin_iata_airport_code;
        public TextView txt_departure_date;
        public TextView txt_departure_hour;

        public TextView txt_destination_iata_airport_code;
        public TextView txt_arrival_date;
        public TextView txt_arrival_hour;

        public MyScheduleHolder(View itemView) {
            super(itemView);
            txt_flight = (TextView) itemView.findViewById(R.id.flight);
            txt_origin_iata_airport_code = (TextView) itemView.findViewById(R.id.origin_iata_airport_code);
            txt_departure_date = (TextView) itemView.findViewById(R.id.departure_date);
            txt_departure_hour = (TextView) itemView.findViewById(R.id.departure_hour);

            txt_destination_iata_airport_code = (TextView) itemView.findViewById(R.id.destination_iata_airport_code);
            txt_arrival_date = (TextView) itemView.findViewById(R.id.arrival_date);
            txt_arrival_hour = (TextView) itemView.findViewById(R.id.arrival_hour);
        }
    }
}
