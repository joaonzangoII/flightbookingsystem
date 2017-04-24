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
    public List<TravelClass> travelClassesList = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public TravelClassSpinnerAdapter(Context context,
                                     List<TravelClass> travelClassesList) {
        this.context = context;
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
        return 0;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(android.R.layout.select_dialog_item, null);

        final TravelClass airport = getItem(i);

        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(airport.name);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
