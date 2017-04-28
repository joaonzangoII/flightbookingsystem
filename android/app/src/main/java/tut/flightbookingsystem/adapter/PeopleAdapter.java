package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class PeopleAdapter extends ArrayAdapter<String> {
    public List<String> peopleList = Collections.emptyList();
    public Context context;
    public int mLayoutResourceId;
    public LayoutInflater layoutInflater;
    public int INVALID_ID = -1;

    public PeopleAdapter(Context context,
                         int resource,
                         List<String> peopleList) {
        super(context, resource, peopleList);
        this.context = context;
        this.mLayoutResourceId = resource;
        this.peopleList = peopleList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return peopleList == null ? 0 : peopleList.size();
    }

    @Override
    public String getItem(final int i) {
        return peopleList.get(i);
    }

    public String getNumPeople(final long i) {
        return peopleList.get((int)i).substring(0, 1);
    }

    //    @Override
    //    public long getItemId(int i) {
    //        return peopleList.get(i).id;
    //    }

    @Override
    public View getView(final int i,
                        View view,
                        final ViewGroup viewGroup) {
        view = getLayoutInflater().inflate(mLayoutResourceId, null);
        final String person = getItem(i);
        final TextView title = (TextView) view.findViewById(android.R.id.text1);
        title.setText(person);
        return view;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }
}
