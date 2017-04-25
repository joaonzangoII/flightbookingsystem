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
    public List<Drink> drinks = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public DrinkSpinnerAdapter(Context context,
                               List<Drink> drinks) {
        this.context = context;
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
        view = getLayoutInflater().inflate(android.R.layout.select_dialog_item, null);
        final Drink drink = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(drink.name);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
