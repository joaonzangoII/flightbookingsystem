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
import tut.flightbookingsystem.listener.RecyclerClickListener.OnItemClickCallback;
import tut.flightbookingsystem.model.Booking;

public class MyBookingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    private List<Booking> items = Collections.emptyList();

    public MyBookingsAdapter() {
    }

    public void setItems(final List<Booking> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyScheduleHolder(layoutInflater.inflate(R.layout.my_bookings_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyScheduleHolder vH = (MyScheduleHolder) holder;
        final Booking mBooking = getItem(position);
        if (mBooking != null) {
            vH.txt_booking_date.setText(String.format("Booking date: %1$s", mBooking.created_at));
            vH.txt_flight.setText(String.format("Flight: %1$s", mBooking.departure_flight.aircraft.name));
            vH.txt_flight_date.setText(String.format("Date: %1$s", mBooking.departure_flight.schedule.date));
            vH.txt_origin_airport.setText(String.format("From: %1$s", mBooking.departure_flight.schedule.origin_airport.name));
            vH.txt_destination_airport.setText(String.format("To: %1$s", mBooking.departure_flight.schedule.destination_airport.name));
            vH.txt_departure_time.setText(String.format("Departure Time: %1$s", mBooking.departure_flight.schedule.departure_time));
            vH.txt_arrival_time.setText(String.format("Arrival Time: %1$s", mBooking.departure_flight.schedule.arrival_time));
            vH.txt_duration.setText(String.format("Duration: %1$s", mBooking.departure_flight.schedule.duration));
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
    public Booking getItem(final int position) {
        return items.get(position);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    class MyScheduleHolder extends RecyclerView.ViewHolder {
        public TextView txt_flight;
        public TextView txt_booking_date;
        public TextView txt_flight_date;
        public TextView txt_origin_airport;
        public TextView txt_destination_airport;
        public TextView txt_departure_time;
        public TextView txt_arrival_time;
        public TextView txt_duration;

        public MyScheduleHolder(View itemView) {
            super(itemView);
            txt_booking_date = (TextView) itemView.findViewById(R.id.booking_date);
            txt_flight = (TextView) itemView.findViewById(R.id.flight);
            txt_flight_date = (TextView) itemView.findViewById(R.id.flight_date);
            txt_origin_airport = (TextView) itemView.findViewById(R.id.origin_airport);
            txt_destination_airport = (TextView) itemView.findViewById(R.id.destination_airport);
            txt_departure_time = (TextView) itemView.findViewById(R.id.departure_time);
            txt_arrival_time = (TextView) itemView.findViewById(R.id.arrival_time);
            txt_duration = (TextView) itemView.findViewById(R.id.duration);
        }
    }

    public String firstCharUpcase(String word) {
        return String.valueOf(word.charAt(0)).toUpperCase() +
                word.substring(1);
    }
}
