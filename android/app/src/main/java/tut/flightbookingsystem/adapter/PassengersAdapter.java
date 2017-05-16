package tut.flightbookingsystem.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.adapter.holder.PassengerDetailViewHolder;
import tut.flightbookingsystem.adapter.holder.PassengerHeaderViewHolder;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.model.PassengerHeader;

public class PassengersAdapter extends ExpandableRecyclerViewAdapter<PassengerHeaderViewHolder,
        PassengerDetailViewHolder> {
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;
    private List<Passenger> items = Collections.emptyList();
    private List<FlightSeat> flightSeatsItems = Collections.emptyList();
    private DatePickerDialog departureDatePickerDialog;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    private LayoutInflater mInflater;

    public void setOnItemClickCallback(RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public PassengersAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    public PassengersAdapter(final Context context,
                             final @NonNull List<PassengerHeader> groups) {
        super(groups);
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(final List<Passenger> items) {
        this.items = items;
        for (int i = 0; i < this.items.size(); i++) {
            expandState.append(i, false);
        }
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public PassengerHeaderViewHolder onCreateGroupViewHolder(final ViewGroup parentViewGroup,
                                                             final int viewType) {
        final View recipeView = mInflater.inflate(R.layout.passenger_header_layout, parentViewGroup, false);
        return new PassengerHeaderViewHolder(recipeView);
    }

    @Override
    @NonNull
    public PassengerDetailViewHolder onCreateChildViewHolder(final @NonNull ViewGroup childViewGroup,
                                                             final int viewType) {
        final View passengerDetailView = mInflater.inflate(R.layout.passengers_item_layout, childViewGroup, false);
        return new PassengerDetailViewHolder(passengerDetailView);
    }


    @Override
    public void onBindGroupViewHolder(final @NonNull PassengerHeaderViewHolder headerViewHolder,
                                      final int parentPosition,
                                      final @NonNull ExpandableGroup passengerHeader) {
        headerViewHolder.setHeaderTitle(passengerHeader, parentPosition);
    }

    @Override
    public void onBindChildViewHolder(@NonNull PassengerDetailViewHolder passengerDetailViewHolder,
                                      final int parentPosition,
                                      final ExpandableGroup group,
                                      final int childPosition) {
        passengerDetailViewHolder.bind(getClildItems(group, childPosition), childPosition, onItemClickCallback);
    }

    public Passenger getClildItems(final ExpandableGroup group,
                                   final int childIndex) {
        return ((PassengerHeader) group).getItems().get(childIndex);
    }
}
