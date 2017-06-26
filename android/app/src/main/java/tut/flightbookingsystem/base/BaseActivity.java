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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tut.flightbookingsystem.util.Constant;
import tut.flightbookingsystem.LoginActivity;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.SettingsActivity;
import tut.flightbookingsystem.adapter.DrinkSpinnerAdapter;
import tut.flightbookingsystem.adapter.FoodSpinnerAdapter;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.util.Utils;

public class BaseActivity extends
        AppCompatActivity implements
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static String TAG = BaseActivity.class.getName();
    protected SessionManager session;
    protected SimpleDateFormat dateFormatter;
    private Long selected_drink_id;
    private Long selected_food_id;
    private Spinner foodsSpinner;
    private Spinner drinksSpinner;
    protected DrinkSpinnerAdapter drinksAdapter;
    protected FoodSpinnerAdapter foodsAdapter;

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


    public AlertDialog.Builder createDialog(final String task) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to perform this task?");
        alertDialogBuilder.setTitle(String.format("%s", Utils.properCase(task)));
        return alertDialogBuilder;
    }

    public void addOrUpdateDrink(final Passenger passenger,
                                 final String action,
                                 final Handler requestHandler) {
        final AlertDialog.Builder alertDialogBuilder = createDialog("drink");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface,
                                int i) {
                RequestManager.addOrUpdateTask(session,
                        BaseActivity.this,
                        passenger.id,
                        drinksSpinner.getSelectedItemId(),
                        "drink",
                        action,
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

    public void addOrUpdateFood(final Passenger passenger,
                                final String action,
                                final Handler requestHandler) {
        final AlertDialog.Builder alertDialogBuilder = createDialog("food");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface,
                                int i) {
                RequestManager.addOrUpdateTask(session,
                        BaseActivity.this,
                        passenger.id,
                        foodsSpinner.getSelectedItemId(),
                        "food",
                        action,
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
                clearSession();

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


    public void clearSession() {
        session.setLogin(false);
        session.setLoggedinUser(null);
        session.logout();
        goToActivity(LoginActivity.class, true);
    }


    public Spinner getFoodsAdapter(final View view) {

        final List<Food> foods = session.getFoods();
        foods.add(0, new Food(0, "Select Food", ""));
        foodsAdapter = new FoodSpinnerAdapter
                (this, R.layout.spinners_item_layout, foods);
        foodsSpinner = (Spinner) view.findViewById(R.id.foodSpinner);
        foodsSpinner.setAdapter(foodsAdapter);
        // show hint
        foodsSpinner.setSelection(0);
        foodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                if (i == 0) {
                    selected_food_id = null;
                } else {
                    selected_food_id = (long) i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return foodsSpinner;
    }

    public Spinner getDrinksAdapter(final View view) {
        final List<Drink> drinks = session.getDrinks();
        drinks.add(0, new Drink(0, "Select Drink", ""));
        drinksAdapter = new DrinkSpinnerAdapter
                (this, R.layout.spinners_item_layout, drinks);
        drinksSpinner = (Spinner) view.findViewById(R.id.drinksSpinner);
        drinksSpinner.setAdapter(drinksAdapter);
        // show hint
        drinksSpinner.setSelection(0);
        drinksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view,
                                       int i,
                                       long l) {
                if (i == 0) {
                    selected_drink_id = null;
                } else {
                    selected_drink_id = (long) i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return drinksSpinner;
    }
}
