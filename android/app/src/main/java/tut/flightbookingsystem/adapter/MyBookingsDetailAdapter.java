package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.MealDrink;
import tut.flightbookingsystem.model.MealFood;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.util.BookingStatus;
import tut.flightbookingsystem.util.LocalDate;
import tut.flightbookingsystem.util.Utils;

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
        return passengersList == null
                ? 1
                : 1 + passengersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0
                ? TYPE_INFO
                : TYPE_PASSENGER;
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
            final TextView firstnames = (TextView) cardview.findViewById(R.id.firstnames);
            final TextView surname = (TextView) cardview.findViewById(R.id.surname);
            final TextView travelClass = (TextView) cardview.findViewById(R.id.travel_class);
            final TextView seatNumber = (TextView) cardview.findViewById(R.id.seat_number);
            final TextView foodType = (TextView) cardview.findViewById(R.id.food_type);
            final TextView foodAndDrink = (TextView) cardview.findViewById(R.id.food_and_drink);
            final TextView foodTextView = (TextView) cardview.findViewById(R.id.food);
            final TextView drinkTextView = (TextView) cardview.findViewById(R.id.drink);

            final AppCompatImageButton btnAddMeal = (AppCompatImageButton)
                    cardview.findViewById(R.id.btn_add_meal);
            final AppCompatImageButton btnDeleteMeal = (AppCompatImageButton)
                    cardview.findViewById(R.id.btn_delete_meal);

            firstnames.setText(Utils.fromHtml(
                    String.format("<b>First Names:</b> %1$s", passenger.firstnames)));
            surname.setText(Utils.fromHtml(String.format("<b>Surname:</b> %1$s", passenger.surname)));
            final FlightSeat flight_seat = passenger.flight_seat;
            if (flight_seat != null) {
                seatNumber.setText(Utils.fromHtml(
                        String.format("<b>Seat Number:</b> %1$s", passenger.flight_seat.number)));
                if (flight_seat.travel_class != null) {
                    if (flight_seat.travel_class.name != null) {
                        travelClass.setText(Utils.fromHtml(
                                String.format("<b>Travel Class:</b> %1$s", flight_seat.travel_class.name)));
                    }
                }
            }

            final MealDrink mealDrink = passenger.drink;
            final MealFood mealFood = passenger.food;
            foodAndDrink.setText(Utils.fromHtml(
                    String.format("<b>Meal:</b> %1$s", passenger.food_and_drink)));
            foodAndDrink.setVisibility(View.VISIBLE);

            if (mealDrink != null) {
                final Drink drink = mealDrink.drink;
                drinkTextView.setText(Utils.fromHtml(
                        String.format("<b>Drink:</b> %1$s", drink.name)));
            } else {
                drinkTextView.setText(Utils.fromHtml(
                        String.format("<b>Drink:</b> %1$s", "None")));
                btnDeleteMeal.setVisibility(View.GONE);
            }

            if (mealFood != null) {
                final Food food = mealFood.food;
                foodTextView.setText(Utils.fromHtml(
                        String.format("<b>Food:</b> %1$s", food.name)));
                if (food != null) {
                    if (food.food_type != null) {
                        foodType.setText(Utils.fromHtml(
                                String.format("<b>Food Type:</b> %1$s", food.food_type.name)));
                        foodType.setVisibility(View.VISIBLE);
                    } else {
                        foodType.setText("No Meal Added yet");
                    }
                }
            } else {
                foodTextView.setText(Utils.fromHtml(
                        String.format("<b>Food:</b> %1$s", "None")));
                btnDeleteMeal.setVisibility(View.GONE);
            }


            btnAddMeal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            btnDeleteMeal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            cardview.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        }
    }

    private class InfoViewHolder extends RecyclerView.ViewHolder {
        public InfoViewHolder(final View itemView) {
            super(itemView);
        }

        public void bind(final View view,
                         final Booking mBooking) {
            final ImageView imgStatus = (ImageView) view.findViewById(R.id.status);
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

            imgStatus.setBackground(BookingStatus.getColor(itemView.getContext(),
                    mBooking.status));
            txtBookingNumber.setText(String.format("%1$s", mBooking.booking_number));
            created_at.setText(String.format("%1$s", LocalDate.formatDate(mBooking.created_at)));
            txtTotal.setText(String.format("R%1$s", mBooking.total));
            txtFlight.setText(String.format("Flight: %1$s", mBooking.departure_flight.aircraft.name));
            txtFlightDate.setText(String.format("Date: %1$s", mBooking.departure_flight.schedule.date));
            txtOriginAirport.setText(String.format("%1$s(%2$s)",
                    mBooking.departure_flight.schedule.origin_airport.name,
                    mBooking.departure_flight.schedule.origin_airport.iata_airport_code));
            txtDestinationAirport.setText(String.format("%1$s(%2$s)",
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
