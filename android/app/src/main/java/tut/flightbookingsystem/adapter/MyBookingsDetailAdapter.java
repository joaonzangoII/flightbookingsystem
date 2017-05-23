package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Meal;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.util.LocalDate;

public class MyBookingsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;
    private int TYPE_INFO = 0, TYPE_PASSENGER = 1;
    private Booking mBooking;
    private List<Passenger> passengersList = new ArrayList<>();
    public Context context;
    public LayoutInflater layoutInflater;

    public MyBookingsDetailAdapter(final Context context) {
        super();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickCallback(RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void itemsChanged(final Booking mBooking,
                             final List<Passenger> passengersList,
                             final int position) {
        this.mBooking = mBooking;
        this.passengersList = passengersList;
        notifyItemChanged(position);
        notifyDataSetChanged();
    }

    public void setItems(final Booking mBooking,
                         final List<Passenger> passengersList) {
        this.mBooking = mBooking;
        this.passengersList = passengersList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup,
                                                      final int position) {
        View view;
        if (getItemViewType(position) == TYPE_INFO) {
            view = getLayoutInflater().inflate(R.layout.my_bookings_detail_adapter_info_item, viewGroup, false);
            return new InfoViewHolder(view);
        } else {
            view = getLayoutInflater().inflate(R.layout.my_bookings_detail_adapter_passenger_item,
                    viewGroup,
                    false);
            return new PassengerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder,
                                 final int position) {
        if (getItemViewType(position) == TYPE_INFO) {
            final InfoViewHolder vH = (InfoViewHolder) viewHolder;
            vH.bind(vH.itemView, mBooking);
        } else {
            final PassengerViewHolder vH = (PassengerViewHolder) viewHolder;
            vH.bind(vH.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return passengersList == null ? 1 : 1 + passengersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_INFO : TYPE_PASSENGER;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    private class PassengerViewHolder extends RecyclerView.ViewHolder {
        public PassengerViewHolder(final View itemView) {
            super(itemView);
        }

        public void bind(final View cardview,
                         final int position) {
            final Passenger passenger = passengersList.get(position - 1);
            final TextView firstName = (TextView) cardview.findViewById(R.id.first_name);
            final TextView lastName = (TextView) cardview.findViewById(R.id.last_name);
            final TextView travelClass = (TextView) cardview.findViewById(R.id.travel_class);
            final TextView seatNumber = (TextView) cardview.findViewById(R.id.seat_number);
            final TextView foodType = (TextView) cardview.findViewById(R.id.food_type);
            final AppCompatButton btnAddMeal = (AppCompatButton) cardview.findViewById(R.id.btn_add_meal);
            final AppCompatButton btnDeleteMeal = (AppCompatButton) cardview.findViewById(R.id.btn_delete_meal);

            firstName.setText(String.format("First Name: %1$s", passenger.first_name));
            lastName.setText(String.format("Last Name: %1$s", passenger.last_name));
            final FlightSeat flight_seat = passenger.flight_seat;
            if (flight_seat != null) {
                seatNumber.setText(String.format("Seat Number: %1$s", passenger.flight_seat.number));
                if (flight_seat.travel_class != null) {
                    if (flight_seat.travel_class.name != null) {
                        travelClass.setText(String.format("Travel Class: %1$s", flight_seat.travel_class.name));
                    }
                }
            }

            final Meal meal = passenger.meal;
            if (meal != null) {
                if (meal.food != null) {
                    btnDeleteMeal.setVisibility(View.VISIBLE);
                    if (meal.food.food_type != null) {
                        btnAddMeal.setText("Edit Meal");
                        foodType.setText(String.format("Food Type:  %1$s", meal.food.food_type.name));
                    } else {
                        btnAddMeal.setText("Add Meal");
                        foodType.setText("No Meal Added yet");
                    }
                }
            } else {
                btnDeleteMeal.setVisibility(View.GONE);
            }

            btnAddMeal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            btnDeleteMeal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        }
    }

    private class InfoViewHolder extends RecyclerView.ViewHolder {
        public InfoViewHolder(final View itemView) {
            super(itemView);
        }

        public void bind(final View view,
                         final Booking mBooking) {
            final TextView txtStatus = (TextView) view.findViewById(R.id.status);
            final TextView txtBookingNumber = (TextView) view.findViewById(R.id.booking_number);
            final TextView created_at = (TextView) view.findViewById(R.id.booking_date);
            final TextView txtTotal = (TextView) view.findViewById(R.id.total);
            final TextView txtFlight = (TextView) view.findViewById(R.id.flight);
            final TextView txtFlightDate = (TextView) view.findViewById(R.id.flight_date);
            final TextView txtOriginAirport = (TextView) view.findViewById(R.id.origin_airport);
            final TextView txtDestinationAirport = (TextView) view.findViewById(R.id.destination_airport);
            final TextView txtDepartureDate = (TextView) view.findViewById(R.id.departure_date);
            final TextView txtDepartureTime = (TextView) view.findViewById(R.id.departure_time);
            final TextView txtArrivalDate = (TextView) view.findViewById(R.id.arrival_date);
            final TextView txtArrivalTime = (TextView) view.findViewById(R.id.arrival_time);
            final TextView txtDuration = (TextView) view.findViewById(R.id.duration);

            txtStatus.setText(String.format("%1$s", mBooking.status));
            txtBookingNumber.setText(String.format("%1$s", mBooking.booking_number));
            created_at.setText(String.format("%1$s", LocalDate.formatDate(mBooking.created_at)));
            txtTotal.setText(String.format("R%1$s", mBooking.total));
            txtFlight.setText(String.format("Flight: %1$s", mBooking.departure_flight.aircraft.name));
            txtFlightDate.setText(String.format("Date: %1$s", mBooking.departure_flight.schedule.date));
            txtOriginAirport.setText(String.format("From: %1$s(%2$s)",
                    mBooking.departure_flight.schedule.origin_airport.name,
                    mBooking.departure_flight.schedule.origin_airport.iata_airport_code));
            txtDestinationAirport.setText(String.format("To:%1$s(%2$s)",
                    mBooking.departure_flight.schedule.destination_airport.name,
                    mBooking.departure_flight.schedule.destination_airport.iata_airport_code));
            txtDepartureDate.setText(String.format("%1$s",
                    LocalDate.formatDate(mBooking.departure_flight.schedule.departure_time)));
            txtDepartureTime.setText(String.format("%1$s",
                    LocalDate.getTime(mBooking.departure_flight.schedule.departure_time)));
            txtArrivalDate.setText(String.format("%1$s",
                    LocalDate.formatDate(mBooking.departure_flight.schedule.arrival_time)));
            txtArrivalTime.setText(String.format("%1$s",
                    LocalDate.getTime(mBooking.departure_flight.schedule.arrival_time)));
            txtDuration.setText(String.format("Duration: %1$s", mBooking.departure_flight.schedule.duration));
        }
    }
}
