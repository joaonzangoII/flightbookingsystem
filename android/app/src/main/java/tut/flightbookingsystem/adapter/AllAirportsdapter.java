package tut.flightbookingsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.listener.RecyclerClickListener.OnItemClickCallback;
import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Drink;

public class AllAirportsdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    private List<Airport> items = Collections.emptyList();

    public AllAirportsdapter() {
    }

    public void setItems(final List<Airport> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyScheduleHolder(layoutInflater.inflate(R.layout.airport_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyScheduleHolder vH = (MyScheduleHolder) holder;
        final Airport airport = getItem(position);
        if (airport != null) {
            vH.txt_name.setText(String.format("%1$s", airport.name));
            vH.txt_city.setText(String.format("%1$s", airport.city));
            GlideAdapter.setImage(vH.itemView.getContext(), airport.image, vH.imgImage);
            vH.itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    @Nullable
    public Airport getItem(final int position) {
        return items.get(position);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    class MyScheduleHolder extends RecyclerView.ViewHolder {
        public ImageView imgImage;
        public TextView txt_name;
        public TextView txt_city;

        public MyScheduleHolder(View itemView) {
            super(itemView);
            imgImage = (ImageView) itemView.findViewById(R.id.image);
            txt_name = (TextView) itemView.findViewById(R.id.name);
            txt_city = (TextView) itemView.findViewById(R.id.city);
        }
    }
}
