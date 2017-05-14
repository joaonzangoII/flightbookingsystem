package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.OnSeatSelected;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.CenterItem;
import tut.flightbookingsystem.model.EdgeItem;

public class AirplaneFlightSeatAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AbstractItem> mItems;
    private OnSeatSelected mOnSeatSelected;
    private int position;
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {
        final TextView txtLabel;
        final ImageView imgSeatNormal;
        private final ImageView imgSeatSelected;

        public EdgeViewHolder(View itemView) {
            super(itemView);
            txtLabel = (TextView) itemView.findViewById(R.id.txt_label);
            imgSeatNormal = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
        }
    }

    private static class CenterViewHolder extends RecyclerView.ViewHolder {
        final TextView txtLabel;
        final ImageView imgSeatNormal;
        private final ImageView imgSeatSelected;

        public CenterViewHolder(View itemView) {
            super(itemView);
            txtLabel = (TextView) itemView.findViewById(R.id.txt_label);
            imgSeatNormal = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
        }

    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

    }

    public AirplaneFlightSeatAdapter(Context context,
                                     List<AbstractItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AbstractItem.TYPE_CENTER) {
            View itemView = mLayoutInflater.inflate(R.layout.seat_list_item, parent, false);
            return new CenterViewHolder(itemView);
        } else if (viewType == AbstractItem.TYPE_EDGE) {
            View itemView = mLayoutInflater.inflate(R.layout.seat_list_item, parent, false);
            return new EdgeViewHolder(itemView);
        } else {
            View itemView = new View(mContext);
            return new EmptyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder,
                                 final int position) {
        int type = mItems.get(position).getType();
        if (type == AbstractItem.TYPE_CENTER) {
            final CenterItem item = (CenterItem) getItem(position);
            final CenterViewHolder holder = (CenterViewHolder) viewHolder;
            setupView(holder, position, item.getLabel());
        } else if (type == AbstractItem.TYPE_EDGE) {
            final EdgeItem item = (EdgeItem) getItem(position);
            final EdgeViewHolder holder = (EdgeViewHolder) viewHolder;
            setupView(holder, position, item.getLabel());
        }
    }

    private void setupView(final Object clazz,
                           final int position,
                           final String label) {
        this.position = position;
        Log.e("TAG", clazz.getClass().getName());
        if (clazz instanceof EdgeViewHolder) {
            ((EdgeViewHolder) clazz).txtLabel.setText(label);
            ((EdgeViewHolder) clazz).imgSeatNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    toggleSelection(position);
                    mOnSeatSelected.onSeatSelected(position);
                }
            });

            ((EdgeViewHolder) clazz).imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
            //((EdgeViewHolder) clazz).imgSeatNormal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        } else {
            ((CenterViewHolder) clazz).txtLabel.setText(label);
            ((CenterViewHolder) clazz).imgSeatNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    toggleSelection(position);
                    mOnSeatSelected.onSeatSelected(position);
                    // mOnSeatSelected.onSeatSelected(getSelectedItemCount());
                }
            });
            ((CenterViewHolder) clazz).imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
            //((CenterViewHolder) clazz).imgSeatNormal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        }
    }

    private Object getItem(final int position) {
        return mItems.get(position);
    }

    public int getSelectedPosition() {
        return this.position;
    }

    public int getSelectedText(final int position) {
        return super.getSelectedItemCount();
    }
}
