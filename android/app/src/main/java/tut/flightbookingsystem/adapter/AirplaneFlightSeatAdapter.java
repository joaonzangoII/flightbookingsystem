package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.OnSeatSelected;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.CenterItem;
import tut.flightbookingsystem.model.EdgeItem;

public class AirplaneFlightSeatAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AbstractItem> mItems;
    private OnSeatSelected mOnSeatSelected;
    private int position;

    public AirplaneFlightSeatAdapter(Context context,
                                     List<AbstractItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
    }

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {
        final TextView txtLabel;
        final ImageView imgSeatNormal;
        private final ImageView imgSeatSelected;
        private final ImageView imgSeatTaken;

        public EdgeViewHolder(View itemView) {
            super(itemView);
            txtLabel = (TextView) itemView.findViewById(R.id.txt_label);
            imgSeatNormal = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            imgSeatTaken = (ImageView) itemView.findViewById(R.id.img_seat_taken);
        }
    }

    private static class CenterViewHolder extends RecyclerView.ViewHolder {
        final TextView txtLabel;
        final ImageView imgSeatNormal;
        private final ImageView imgSeatSelected;
        private final ImageView imgSeatTaken;

        public CenterViewHolder(View itemView) {
            super(itemView);
            txtLabel = (TextView) itemView.findViewById(R.id.txt_label);
            imgSeatNormal = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            imgSeatTaken = (ImageView) itemView.findViewById(R.id.img_seat_taken);
        }

    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

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
            setupView(holder, position, item.getId(), item.getLabel());
        } else if (type == AbstractItem.TYPE_EDGE) {
            final EdgeItem item = (EdgeItem) getItem(position);
            final EdgeViewHolder holder = (EdgeViewHolder) viewHolder;
            setupView(holder, position, item.getId(), item.getLabel());
        }
    }

    private void setupView(final Object clazz,
                           final int position,
                           final long id,
                           final String label) {
        this.position = position;
        final AbstractItem item = mItems.get(position);
        if (clazz instanceof EdgeViewHolder) {
            final EdgeViewHolder vh = ((EdgeViewHolder) clazz);
            vh.txtLabel.setText(label);
            vh.imgSeatNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    clearSelection();
                    toggleSelection(position);
                    mOnSeatSelected.onSeatSelected(item);
                }
            });

            isTakenOrNot(position, vh);

        } else {
            final CenterViewHolder vh = ((CenterViewHolder) clazz);
            vh.txtLabel.setText(label);
            vh.imgSeatNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    clearSelection();
                    toggleSelection(position);
                    mOnSeatSelected.onSeatSelected(item);
                }
            });

            isTakenOrNot(position, vh);
        }
    }

    public void isTakenOrNot(int position, Object clazz) {
        final AbstractItem item = mItems.get(position);
        Log.e("ITEM", "Position" + position +
                " > TAKEN: " +
                item.getIsTaken() +
                " > Is Selected Position: " +
                isSelected(position));
        if (item.getIsTaken()) {
            if (clazz instanceof EdgeViewHolder) {
                final EdgeViewHolder vh = ((EdgeViewHolder) clazz);
                vh.imgSeatTaken.setVisibility(View.VISIBLE);
                vh.imgSeatNormal.setVisibility(View.INVISIBLE);
                vh.imgSeatSelected.setVisibility(View.INVISIBLE);
            } else {
                final CenterViewHolder vh = ((CenterViewHolder) clazz);
                vh.imgSeatTaken.setVisibility(View.VISIBLE);
                vh.imgSeatNormal.setVisibility(View.INVISIBLE);
                vh.imgSeatSelected.setVisibility(View.INVISIBLE);
            }
        } else {
            if (clazz instanceof EdgeViewHolder) {
                final EdgeViewHolder vh = ((EdgeViewHolder) clazz);
                if (isSelected(position)) {
                    vh.imgSeatSelected.setVisibility(View.VISIBLE);
                    vh.imgSeatNormal.setVisibility(View.INVISIBLE);
                    vh.imgSeatTaken.setVisibility(View.INVISIBLE);
                } else {
                    vh.imgSeatNormal.setVisibility(View.VISIBLE);
                    vh.imgSeatSelected.setVisibility(View.INVISIBLE);
                    vh.imgSeatTaken.setVisibility(View.INVISIBLE);
                }
            } else {
                final CenterViewHolder vh = ((CenterViewHolder) clazz);
                if (isSelected(position)) {
                    vh.imgSeatSelected.setVisibility(View.VISIBLE);
                    vh.imgSeatNormal.setVisibility(View.INVISIBLE);
                    vh.imgSeatTaken.setVisibility(View.INVISIBLE);
                } else {
                    vh.imgSeatNormal.setVisibility(View.VISIBLE);
                    vh.imgSeatSelected.setVisibility(View.INVISIBLE);
                    vh.imgSeatTaken.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void refreshLayout() {
        //        for (int x = 0; x < mItems.size(); x++) {
        //            final AbstractItem item = mItems.get(x);
        //            if (item.getIsTaken()) {
        //                setIsSelected(x);
        //            } else {
        //                setIsNotSelected(x);
        //            }
        //        }
    }

    private Object getItem(final int position) {
        return mItems.get(position);
    }
}
