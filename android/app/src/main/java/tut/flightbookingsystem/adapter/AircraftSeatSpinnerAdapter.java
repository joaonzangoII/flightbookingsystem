package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.model.AircraftSeat;
import tut.flightbookingsystem.model.Drink;

public class AircraftSeatSpinnerAdapter extends BaseAdapter {
    public List<AircraftSeat> aircraftSeats = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public AircraftSeatSpinnerAdapter(Context context,
                                      List<AircraftSeat> aircraftSeats) {
        this.context = context;
        this.aircraftSeats = aircraftSeats;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return aircraftSeats == null ? 0 : aircraftSeats.size();
    }

    @Override
    public AircraftSeat getItem(int i) {
        return aircraftSeats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return aircraftSeats.get(i).id;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(android.R.layout.select_dialog_item, null);
        final AircraftSeat aircraftSeat = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(aircraftSeat.number);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
