package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.model.AircraftSeat;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.Flight;
import tut.flightbookingsystem.model.FlightSeat;

public class FlightSeatSpinnerAdapter extends BaseAdapter {
    private final int mLayoutResourceId;
    public List<FlightSeat> flightSeats = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public FlightSeatSpinnerAdapter(Context context,
                                    int resource,
                                    List<FlightSeat> flightSeats) {
        this.context = context;
        this.mLayoutResourceId = resource;
        this.flightSeats = flightSeats;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return flightSeats == null ? 0 : flightSeats.size();
    }

    @Override
    public FlightSeat getItem(int i) {
        return flightSeats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return flightSeats.get(i).id;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(mLayoutResourceId, null);
        final FlightSeat flightSeat = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(flightSeat.number);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
