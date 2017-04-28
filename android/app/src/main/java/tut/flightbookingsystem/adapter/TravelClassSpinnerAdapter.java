package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.model.TravelClass;

public class TravelClassSpinnerAdapter extends BaseAdapter {
    private final int mLayoutResourceId;
    public List<TravelClass> travelClassesList = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public TravelClassSpinnerAdapter(Context context,
                                     int resource,
                                     List<TravelClass> travelClassesList) {
        this.context = context;
        this.mLayoutResourceId = resource;
        this.travelClassesList = travelClassesList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return travelClassesList == null ? 0 : travelClassesList.size();
    }

    @Override
    public TravelClass getItem(int i) {
        return travelClassesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return travelClassesList.get(i).id;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(mLayoutResourceId, null);

        final TravelClass travelClass = getItem(i);

        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(travelClass.name);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
