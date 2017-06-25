package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.model.Airport;

public class AirportsSpinnerAdapter extends ArrayAdapter<Airport> /*implements Filterable*/ {
    public List<Airport> airportsList = Collections.emptyList();
    public Context context;
    public int mLayoutResourceId;
    public LayoutInflater layoutInflater;
    public int INVALID_ID = -1;

    public AirportsSpinnerAdapter(Context context,
                                  int resource,
                                  List<Airport> airportsList) {
        super(context, resource, airportsList);
        this.context = context;
        this.mLayoutResourceId = resource;
        this.airportsList = airportsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setItems(final List<Airport> items) {
        this.airportsList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return airportsList == null ? 0 : airportsList.size();
    }

    @Override
    public Airport getItem(int i) {
        return airportsList.get(i);
    }

    public Airport getItemByName(final String name) {
        for (final Airport airport : airportsList) {
            if (airport.name.equals(name)) {
                return airport;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return airportsList.get(i).id;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(mLayoutResourceId, null);
        final Airport airport = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        if (airport != null) {
            title.setText(airport.name);
        }
        return view;
    }

    //    @Override
    //    public boolean isEnabled(int position) {
    //        return airportsList.get(position).id == INVALID_ID;
    //    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
