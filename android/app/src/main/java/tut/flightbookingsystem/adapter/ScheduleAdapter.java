package tut.flightbookingsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
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
            vH.txt_origin_airport.setText(String.format("From: %1$s", schedule.origin_airport_name));
            vH.txt_destination_airport.setText(String.format("To: %1$s", schedule.destination_airport_name));
            vH.txt_departure_time.setText(String.format("Departure Time: %1$s", schedule.departure_time));
            vH.txt_arrival_time.setText(String.format("Arrival Time: %1$s", schedule.arrival_time));
            vH.itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        }
    }

    @Override
    public int getItemCount() {
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
        public TextView txt_origin_airport;
        public TextView txt_destination_airport;
        public TextView txt_departure_time;
        public TextView txt_arrival_time;
        public MyScheduleHolder(View itemView) {
            super(itemView);
            txt_origin_airport = (TextView) itemView.findViewById(R.id.origin_airport);
            txt_destination_airport = (TextView) itemView.findViewById(R.id.destination_airport);
            txt_departure_time = (TextView) itemView.findViewById(R.id.departure_time);
            txt_arrival_time = (TextView) itemView.findViewById(R.id.arrival_time);
        }
    }
}
