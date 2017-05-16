package tut.flightbookingsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.MainItem;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;
    private List<MainItem> items = Collections.emptyList();

    public void setOnItemClickCallback(RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public HomeAdapter() {
    }

    public void setItems(final List<MainItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater.inflate(R.layout.main_page_data_widget, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder vH = (MyViewHolder) holder;
        final MainItem mainItem = getItem(position);
        if (mainItem != null) {
            vH.txt_value.setText(mainItem.value);
            vH.txt_description.setText(mainItem.description);
            //vH.itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
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
    public MainItem getItem(final int position) {
        return items.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_value;
        public TextView txt_description;

        public MyViewHolder(final View itemView) {
            super(itemView);
            //imgImage = (ImageView) itemView.findViewById(R.id.image);
            txt_value = (TextView) itemView.findViewById(R.id.value);
            txt_description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
