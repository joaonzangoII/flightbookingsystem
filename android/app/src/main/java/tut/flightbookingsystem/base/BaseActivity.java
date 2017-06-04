package tut.flightbookingsystem.base;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tut.flightbookingsystem.Constant;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.SessionManager;
import tut.flightbookingsystem.SettingsActivity;
import tut.flightbookingsystem.SplashScreenActivity;
import tut.flightbookingsystem.adapter.DrinkSpinnerAdapter;
import tut.flightbookingsystem.adapter.FoodSpinnerAdapter;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.Meal;
import tut.flightbookingsystem.model.Passenger;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static String TAG = BaseActivity.class.getName();
    protected SessionManager session;
    protected SimpleDateFormat dateFormatter;
    private int selected_drink_id;
    private int selected_food_id;
    private Spinner foodsSpinner;
    private Spinner drinksSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                onBackPressed();
                break;
            case R.id.action_settings:
                goToActivity(SettingsActivity.class);
                break;
            case R.id.action_reload:
                final Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
                break;
            case R.id.action_logout:
                logout();
                break;
        }
        return true;
    }

    protected DatePickerDialog datepicker(final AppCompatEditText edt) {
        Calendar newCalendar = Calendar.getInstance();
        return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view,
                                  int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    protected void setDate(final EditText view,
                           final int year,
                           final int monthOfYear,
                           final int dayOfMonth) {
        view.setText(String.format("%s-%02d-%02d", year, (monthOfYear + 1), dayOfMonth));
    }

    public <T> void goToActivity(final Class<T> clazz,
                                 final boolean finish) {
        final Intent intent = new Intent(BaseActivity.this, clazz);
        if (finish) {
            finish();
        }
        startActivity(intent);
    }

    public <T> void goToActivity(final Class<T> clazz) {
        goToActivity(clazz, false);
    }

    public <T> void goToActivity(final Class<T> clazz, final Bundle bundle) {
        final Intent intent = new Intent(BaseActivity.this, clazz);
        intent.putExtra(Constant.DATA, bundle);
        startActivity(intent);
    }

    private Runnable mShowImeRunnable = new Runnable() {
        @Override
        public void run() {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                //HIDDEN_METHOD_INVOKER.showSoftInputUnchecked(imm, BaseActivity.this, 0);
            }
        }
    };

    protected void setImeVisibility(final boolean visible) {
        if (visible) {
            // post(mShowImeRunnable);
        } else {
            //removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            //            if (imm != null) {
            //                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            //            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }

    public AlertDialog.Builder createDialog(final Passenger passenger,
                                            final boolean isMeal) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final List<Food> foods = session.getFoods();
        final View dialogView = getInflater().inflate(R.layout.add_meal_layout, null);
        if (isMeal) {
            alertDialogBuilder.setMessage("");
            dialogView.setVisibility(View.VISIBLE);
        } else {
            dialogView.setVisibility(View.GONE);
            alertDialogBuilder.setMessage("Are you sure you want to delete the user meal?");
        }

        final List<Drink> drinks = session.getDrinks();
        final FoodSpinnerAdapter adapterFoods = new FoodSpinnerAdapter
                (this, R.layout.spinners_item_layout, foods);
        final DrinkSpinnerAdapter drinksAdapter = new DrinkSpinnerAdapter
                (this, R.layout.spinners_item_layout, drinks);

        foodsSpinner = (Spinner) dialogView.findViewById(R.id.foodSpinner);
        foodsSpinner.setAdapter(adapterFoods);
        foodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                selected_food_id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        drinksSpinner = (Spinner) dialogView.findViewById(R.id.drinksSpinner);
        drinksSpinner.setAdapter(drinksAdapter);
        drinksSpinner.setAdapter(drinksAdapter);
        drinksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                selected_drink_id = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (passenger.meal != null) {
            drinksSpinner.setSelection(drinksAdapter.getById(passenger.meal.drink_id));
            foodsSpinner.setSelection(adapterFoods.getById(passenger.meal.food_id));
        }

        alertDialogBuilder.setTitle("Select Meal Choice");
        alertDialogBuilder.setView(dialogView);
        return alertDialogBuilder;
    }

    public void addMeal(final Passenger passenger) {
        final AlertDialog.Builder alertDialogBuilder = createDialog(passenger, true);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Meal meal = new Meal();
                meal.drink_id = drinksSpinner.getSelectedItemId();
                meal.food_id = foodsSpinner.getSelectedItemId();
                passenger.meal = meal;
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface,
                                final int i) {
            }
        });
        alertDialogBuilder.create().show();
    }

    public void deleteMeal(final Passenger passenger,
                           final Handler requestHandler) {
        final AlertDialog.Builder alertDialogBuilder = createDialog(passenger, false);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Gson gson = new GsonBuilder().create();
                RequestManager.deleteMeal(session,
                        BaseActivity.this,
                        passenger.booking.user_id,
                        gson.toJson(passenger),
                        requestHandler);
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface,
                                final int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.create().show();
    }

    public void addEditMeal(final Passenger passenger,
                            final Handler requestHandler) {
        final AlertDialog.Builder alertDialogBuilder = createDialog(passenger, true);
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Meal meal = new Meal();
                meal.drink_id = drinksSpinner.getSelectedItemId();
                meal.food_id = foodsSpinner.getSelectedItemId();
                passenger.meal = meal;
                final Gson gson = new GsonBuilder().create();
                RequestManager.updateMeal(session,
                        BaseActivity.this,
                        passenger.booking.user_id,
                        gson.toJson(passenger),
                        requestHandler);
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface,
                                final int i) {
            }
        });
        alertDialogBuilder.create().show();
    }

    public LayoutInflater getInflater() {
        return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void logout() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setTitle("Signing Out...");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                session.setLogin(false);
                session.setLoggedinUser(null);
                session.logout();
                goToActivity(SplashScreenActivity.class);

            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface,
                                final int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.create().show();
    }

}
