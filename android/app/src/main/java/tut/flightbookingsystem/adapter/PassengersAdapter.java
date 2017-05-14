package tut.flightbookingsystem.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.Collections;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.adapter.holder.PassengerDetailViewHolder;
import tut.flightbookingsystem.adapter.holder.PassengerHeaderViewHolder;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.model.PassengerHeader;

public class PassengersAdapter extends
        ExpandableRecyclerAdapter<PassengerHeader,
                Passenger,
                PassengerHeaderViewHolder,
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

    //    public PassengersAdapter() {
    //
    //    }

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

    //    public void setFlightSeats(final List<FlightSeat> flightSeatsItems) {
    //        this.flightSeatsItems = flightSeatsItems;
    //        notifyDataSetChanged();
    //    }

    @Override
    public PassengerHeaderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup,
                                                              int viewType) {
        final View recipeView = mInflater.inflate(R.layout.passenger_header_layout, parentViewGroup, false);
        return new PassengerHeaderViewHolder(recipeView);
    }


    @Override
    public PassengerDetailViewHolder onCreateChildViewHolder(final @NonNull ViewGroup childViewGroup,
                                                             final int viewType) {
        final View passengerDetailView = mInflater.inflate(R.layout.passengers_item_layout, childViewGroup, false);
        return new PassengerDetailViewHolder(passengerDetailView);
    }

    // onBind ...
    @Override
    public void onBindParentViewHolder(final @NonNull PassengerHeaderViewHolder recipeViewHolder,
                                       final int parentPosition,
                                       final @NonNull PassengerHeader passengerHeader) {
        recipeViewHolder.bind(passengerHeader, parentPosition);
    }

    @Override
    public void onBindChildViewHolder(@NonNull PassengerDetailViewHolder passengerDetailViewHolder,
                                      int parentPosition,
                                      int childPosition,
                                      @NonNull Passenger ingredient) {
        passengerDetailViewHolder.bind(ingredient, childPosition, onItemClickCallback);
    }


    //    @Override
    //    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent,
    //                                                      final int viewType) {
    //        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    //        return new MyScheduleHolder(layoutInflater.inflate(R.layout.passengers_item_layout, parent, false));
    //    }

    //    @Override
    //    public int getItemCount() {
    //        if (items == null) {
    //            return 0;
    //        }
    //        return items.size();
    //    }
    //
    //    @Nullable
    //    public Passenger getItem(final int position) {
    //        return items.get(position);
    //    }
    //
}
