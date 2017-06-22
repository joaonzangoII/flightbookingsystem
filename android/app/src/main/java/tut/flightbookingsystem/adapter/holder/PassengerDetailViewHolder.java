package tut.flightbookingsystem.adapter.holder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tut.flightbookingsystem.PassengerSeatsDialogFragment;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.adapter.DrinkSpinnerAdapter;
import tut.flightbookingsystem.adapter.FoodSpinnerAdapter;
import tut.flightbookingsystem.listener.MyDialogListener;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.util.Utils;

public class PassengerDetailViewHolder extends ChildViewHolder {
    private DatePickerDialog departureDatePickerDialog;
    public TextView txtTitle;
    public AppCompatEditText edtFirstnames;
    public AppCompatEditText edtSurname;
    public AppCompatEditText edtIdNumber;
    public AppCompatEditText txtDateOfBirth;
    public AppCompatEditText txtSelectSeatId;
    public AppCompatSpinner spnDrinkId;
    public AppCompatSpinner spnFoodId;
    // public Spinner spn_select_seat_id;

    public PassengerDetailViewHolder(final View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.passengerNumber);
        edtFirstnames = (AppCompatEditText) itemView.findViewById(R.id.firstnames);
        edtSurname = (AppCompatEditText) itemView.findViewById(R.id.surname);
        edtIdNumber = (AppCompatEditText) itemView.findViewById(R.id.id_number);
        txtDateOfBirth = (AppCompatEditText) itemView.findViewById(R.id.date_of_birth);
        txtDateOfBirth.setInputType(InputType.TYPE_NULL);
        txtSelectSeatId = (AppCompatEditText) itemView.findViewById(R.id.select_seat_id);
        spnDrinkId = (AppCompatSpinner) itemView.findViewById(R.id.drink_id);
        spnFoodId = (AppCompatSpinner) itemView.findViewById(R.id.food_id);
    }

    public void bind(final SessionManager session,
                     final Passenger passenger,
                     final int position,
                     final RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        if (passenger != null) {
            edtFirstnames.setText(passenger.firstnames);
            edtSurname.setText(passenger.surname);
            edtIdNumber.setText(passenger.id_number);
            final Calendar c = Calendar.getInstance();
            if (txtDateOfBirth.getText().toString().equals("")) {
                setDate(txtDateOfBirth,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
            } else {
                txtDateOfBirth.setText(passenger.date_of_birth);
            }

            txtDateOfBirth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    departureDatePickerDialog.show();
                }
            });

            departureDatePickerDialog = datepicker(itemView.getContext(), txtDateOfBirth);

            final List<Drink> drinks = session.getDrinks();
            drinks.add(new Drink(null, "Select Drink", ""));
            final DrinkSpinnerAdapter drinksAdapter = new DrinkSpinnerAdapter
                    (itemView.getContext(), R.layout.spinners_item_layout, drinks);
            drinksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDrinkId.setAdapter(drinksAdapter);
            spnDrinkId.setSelection(drinksAdapter.getCount());
            spnDrinkId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView,
                                           View view,
                                           int i,
                                           long l) {
                    passenger.drink_id = (long) i;
                    // passenger.drink.drink_id = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            final List<Food> foods = session.getFoods();
            foods.add(new Food(null, "Select Food", ""));
            final FoodSpinnerAdapter foodsAdapter = new FoodSpinnerAdapter
                    (itemView.getContext(), R.layout.spinners_item_layout, foods);
            foodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFoodId.setAdapter(foodsAdapter);
            spnFoodId.setSelection(foodsAdapter.getCount());
            spnFoodId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView,
                                           View view,
                                           int i,
                                           long l) {
                    passenger.food_id = (long) i;
                    // passenger.food.food_id = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            setListenerAndData(edtFirstnames, "firstnames", passenger);
            setListenerAndData(edtSurname, "surname", passenger);
            setListenerAndData(edtIdNumber, "id_number", passenger);
            setListenerAndData(txtDateOfBirth, "date_of_birth", passenger);

            txtSelectSeatId.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View v,
                                       final MotionEvent event) {
                    if (MotionEvent.ACTION_UP == event.getAction()) {
                        final FragmentManager manager = ((FragmentActivity)
                                itemView.getContext()).getSupportFragmentManager();
                        final Fragment frag = manager.findFragmentByTag("fragment_passenger_dialog");
                        if (frag != null) {
                            manager.beginTransaction().remove(frag).commit();
                        }

                        final PassengerSeatsDialogFragment dialog = new PassengerSeatsDialogFragment();
                        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        dialog.setMyDialogListener(new MyDialogListener() {
                            public void userSelectedAValue(final Object value) {
                                if (value instanceof AbstractItem) {
                                    final AbstractItem item = ((AbstractItem) value);
                                    passenger.flight_seat_id = item.getId();
                                    txtSelectSeatId.setText(item.getLabel());
                                    session.setPassenger(Utils.jsonToString(passenger));
                                    session.addorUpdateToBookingPassengers(passenger);
                                } else if (value instanceof FlightSeat) {
                                    final FlightSeat item = ((FlightSeat) value);
                                    passenger.flight_seat_id = item.id;
                                    txtSelectSeatId.setText(String.valueOf(item.number));
                                    session.addorUpdateToBookingPassengers(passenger);
                                } else {
                                    passenger.flight_seat_id = (int) value;
                                    txtSelectSeatId.setText(String.valueOf(value));
                                    session.addorUpdateToBookingPassengers(passenger);
                                }
                            }

                            public void userCanceled() {
                            }
                        });
                        dialog.show(manager, "fragment_passenger_dialog");
                    }

                    return true;
                }
            });
        }
    }


    public void setListenerAndData(final View view,
                                   final String column,
                                   final Passenger passenger) {
        final EditText edt = ((EditText) (view));
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int i,
                                          int i1,
                                          int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i,
                                      int i1,
                                      int i2) {
                switch (column) {
                    case "firstnames":
                        passenger.firstnames = edt.getText().toString();
                        break;
                    case "surname":
                        passenger.surname = edt.getText().toString();
                        break;
                    case "id_number":
                        passenger.id_number = edt.getText().toString();
                        break;
                    case "date_of_birth":
                        passenger.date_of_birth = edt.getText().toString();
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    protected void setDate(final EditText view,
                           final int year,
                           final int monthOfYear,
                           final int dayOfMonth) {
        view.setText(String.format("%s-%02d-%02d", year, (monthOfYear + 1), dayOfMonth));
    }

    protected DatePickerDialog datepicker(final Context context,
                                          final AppCompatEditText edt) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        return new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(final DatePicker view,
                                  final int year,
                                  final int monthOfYear,
                                  final int dayOfMonth) {
                final Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
