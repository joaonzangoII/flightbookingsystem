package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.model.Drink;

public class DrinkSpinnerAdapter extends BaseAdapter {
    private final int mLayoutResourceId;
    public List<Drink> drinks = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public DrinkSpinnerAdapter(Context context,
                               int resource,
                               List<Drink> drinks) {
        this.context = context;
        this.mLayoutResourceId = resource;
        this.drinks = drinks;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return drinks == null ? 0 : drinks.size();
    }

    @Override
    public Drink getItem(int i) {
        return drinks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return drinks.get(i).id;
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(mLayoutResourceId, null);
        final Drink drink = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(drink.name);
        return view;
    }

    public int getById(final long id) {
        for (int x = 0; x < drinks.size(); x++) {
            if (drinks.get(x).id == id) {
                return x;
            }
        }
        return 0;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
