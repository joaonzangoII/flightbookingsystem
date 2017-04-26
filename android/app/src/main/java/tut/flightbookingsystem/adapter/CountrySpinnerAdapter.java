package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.model.Country;
import tut.flightbookingsystem.model.Drink;

public class CountrySpinnerAdapter extends BaseAdapter {
    public List<Country> countries = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public CountrySpinnerAdapter(Context context,
                                 List<Country> countries) {
        this.context = context;
        this.countries = countries;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return countries == null ? 0 : countries.size();
    }

    @Override
    public Country getItem(int i) {
        return countries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return countries.get(i).id;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(android.R.layout.select_dialog_item, null);
        final Country country = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(country.name);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
