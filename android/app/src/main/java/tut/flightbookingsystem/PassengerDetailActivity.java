package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.MealDrink;
import tut.flightbookingsystem.model.MealFood;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.util.Constant;
import tut.flightbookingsystem.util.Utils;

public class PassengerDetailActivity extends BaseActivity implements View.OnClickListener {
    private Passenger passenger;


    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final boolean error = data.getBoolean(Constant.ERROR);
            if (!error) {
                if (data.getBoolean(Constant.IS_BOOKING)) {
                    final Gson gson = new GsonBuilder().create();
                    final Type type = new TypeToken<Booking>() {
                    }.getType();
                    /*final Booking mBooking = gson.fromJson(data.getString(Constant.BOOKING), type);
                    passengersList = mBooking.passengers;
                    myBookingsDetailAdapter.itemsChanged(mBooking, mBooking.passengers, position);*/
                    goToActivity(MyBookingsActivity.class, true);
                } else {
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_detail);
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<Passenger>() {
        }.getType();

        final Bundle bundle = (Bundle) getIntent().getBundleExtra(Constant.DATA);
        passenger = session.getPassenger();
        final TextView firstnames = (TextView) findViewById(R.id.firstnames);
        final TextView surname = (TextView) findViewById(R.id.surname);
        final TextView travelClass = (TextView) findViewById(R.id.travel_class);
        final TextView seatNumber = (TextView) findViewById(R.id.seat_number);
        final TextView foodType = (TextView) findViewById(R.id.food_type);
        final TextView foodTextView = (TextView) findViewById(R.id.food);
        final TextView drinkTextView = (TextView) findViewById(R.id.drink);
        final TextView foodAndDrink = (TextView) findViewById(R.id.food_and_drink);
        final AppCompatImageButton btnAddMeal = (AppCompatImageButton) findViewById(R.id.btn_add_meal);
        final AppCompatImageButton btnDeleteMeal = (AppCompatImageButton) findViewById(R.id.btn_delete_meal);

        final AppCompatButton btnUpdateDrink = (AppCompatButton) findViewById(R.id.btn_update_drink);
        final AppCompatButton btnDeleteDrink = (AppCompatButton) findViewById(R.id.btn_delete_drink);
        final AppCompatButton btnUpdateFood = (AppCompatButton) findViewById(R.id.btn_update_food);
        final AppCompatButton btnDeleteFood = (AppCompatButton) findViewById(R.id.btn_delete_food);
        final View view = getWindow().getDecorView().getRootView();
        final Spinner foodsSpinner = (Spinner) getFoodsAdapter(view);
        final Spinner drinksSpinner = (Spinner) getDrinksAdapter(view);

        btnAddMeal.setVisibility(View.GONE);
        btnDeleteMeal.setVisibility(View.GONE);
        if (passenger.drink != null) {
            drinksSpinner.setSelection(drinksAdapter.getById(passenger.drink.drink_id));
        }

        if (passenger.food != null) {
            foodsSpinner.setSelection(foodsAdapter.getById(passenger.food.food_id));
        }

        if (passenger != null) {
            setTitle(passenger.name);
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

            btnUpdateDrink.setOnClickListener(this);
            btnDeleteDrink.setOnClickListener(this);

            btnUpdateFood.setOnClickListener(this);
            btnDeleteFood.setOnClickListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_update_drink:
                addOrUpdateDrink(passenger, "add-update", requestHandler);
                break;
            case R.id.btn_delete_drink:
                if (passenger.food != null) {
                    addOrUpdateDrink(passenger, "delete", requestHandler);
                } else {
                    Toast.makeText(this, "You haven't added a drink yet", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_food:
                addOrUpdateFood(passenger, "add-update", requestHandler);
                break;
            case R.id.btn_delete_food:
                if (passenger.food != null) {
                    addOrUpdateFood(passenger, "delete", requestHandler);
                } else {
                    Toast.makeText(this, "You haven't added a food yet", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                final Gson gson = new GsonBuilder().create();
                final Bundle bundle = new Bundle();
                session.setPassenger(gson.toJson(passenger));
                bundle.putParcelable("PASSENGER", passenger);
                goToActivity(PassengerDetailActivity.class, bundle);
                break;
        }
    }
}
