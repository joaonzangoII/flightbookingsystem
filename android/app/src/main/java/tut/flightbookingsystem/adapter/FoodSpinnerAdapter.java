package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.model.Food;

public class FoodSpinnerAdapter extends ArrayAdapter<Food> {
    private final int mLayoutResourceId;
    public List<Food> foods = Collections.emptyList();
    public Context context;
    public LayoutInflater layoutInflater;

    public FoodSpinnerAdapter(Context context,
                              int resource,
                              List<Food> foods) {
        super(context, resource, foods);
        this.context = context;
        this.mLayoutResourceId = resource;
        this.foods = foods;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        final int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }

    @Override
    public Food getItem(int i) {
        return foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
        /*if (foods == null) {
            return 0;
        }

        if (foods.get(i) == null) {
            return 0;
        } else {
            return foods.get(i).id;
        }*/
    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(mLayoutResourceId, null);
        final Food food = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(food.name);
        return view;
    }

    public int getById(final long id) {
        for (int x = 0; x < foods.size(); x++) {
            if (foods.get(x).id == id) {
                return x;
            }
        }
        return 0;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
