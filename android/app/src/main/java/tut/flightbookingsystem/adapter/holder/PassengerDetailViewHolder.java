package tut.flightbookingsystem.adapter.holder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tut.flightbookingsystem.PassengerSeatsDialogFragment;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.MyDialogListener;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.AbstractItem;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Passenger;

public class PassengerDetailViewHolder extends ChildViewHolder {
    private DatePickerDialog departureDatePickerDialog;
    public TextView txt_title;
    public AppCompatEditText edt_first_name;
    public AppCompatEditText edt_middle_name;
    public AppCompatEditText edt_last_name;
    public AppCompatEditText edt_id_number;
    public AppCompatEditText txt_date_of_birth;
    public Button btn_add_meal;
    public AppCompatEditText txt_select_seat_id;
    // public Spinner spn_select_seat_id;

    public PassengerDetailViewHolder(final View itemView) {
        super(itemView);
        txt_title = (TextView) itemView.findViewById(R.id.passengerNumber);
        edt_first_name = (AppCompatEditText) itemView.findViewById(R.id.first_name);
        edt_middle_name = (AppCompatEditText) itemView.findViewById(R.id.middle_name);
        edt_last_name = (AppCompatEditText) itemView.findViewById(R.id.last_name);
        edt_id_number = (AppCompatEditText) itemView.findViewById(R.id.id_number);
        txt_date_of_birth = (AppCompatEditText) itemView.findViewById(R.id.date_of_birth);
        txt_date_of_birth.setInputType(InputType.TYPE_NULL);
        txt_select_seat_id = (AppCompatEditText) itemView.findViewById(R.id.select_seat_id);
        btn_add_meal = (Button) itemView.findViewById(R.id.add_meal);
    }

    public void bind(final Passenger passenger,
                     final int position,
                     final RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        if (passenger != null) {
            edt_first_name.setText(passenger.last_name);
            edt_middle_name.setText(passenger.middle_name);
            edt_last_name.setText(passenger.last_name);
            edt_id_number.setText(passenger.id_number);
            final Calendar c = Calendar.getInstance();
            if (txt_date_of_birth.getText().toString().equals("")) {
                setDate(txt_date_of_birth,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
            } else {
                txt_date_of_birth.setText(passenger.date_of_birth);
            }

            txt_date_of_birth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    departureDatePickerDialog.show();
                }
            });

            departureDatePickerDialog = datepicker(itemView.getContext(), txt_date_of_birth);
            btn_add_meal.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
            setListenerAndData(edt_first_name, "first_name", passenger);
            setListenerAndData(edt_middle_name, "middle_name", passenger);
            setListenerAndData(edt_last_name, "last_name", passenger);
            setListenerAndData(edt_id_number, "id_number", passenger);
            setListenerAndData(txt_date_of_birth, "date_of_birth", passenger);

            txt_select_seat_id.setOnTouchListener(new View.OnTouchListener() {
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
                                    passenger.flight_seat_id = ((AbstractItem) value).getId();
                                    txt_select_seat_id.setText(((AbstractItem) value).getLabel());
                                } else if (value instanceof FlightSeat) {
                                    passenger.flight_seat_id = ((FlightSeat) value).id;
                                    txt_select_seat_id.setText(String.valueOf(((FlightSeat) value).number));
                                } else {
                                    passenger.flight_seat_id = (int) value;
                                    txt_select_seat_id.setText(String.valueOf(value));
                                }
                                //Toast.makeText(itemView.getContext(), value.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
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
                    case "first_name":
                        passenger.first_name = edt.getText().toString();
                        break;
                    case "middle_name":
                        passenger.middle_name = edt.getText().toString();
                        break;
                    case "last_name":
                        passenger.last_name = edt.getText().toString();
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
