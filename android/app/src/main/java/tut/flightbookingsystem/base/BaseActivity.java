package tut.flightbookingsystem.base;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tut.flightbookingsystem.Constant;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.SessionManager;
import tut.flightbookingsystem.SettingsActivity;
import tut.flightbookingsystem.SplashScreenActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    protected SessionManager session;
    protected SimpleDateFormat dateFormatter;

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

    public <T> void goToActivity(final Class<T> clazz) {
        final Intent intent = new Intent(BaseActivity.this, clazz);
        startActivity(intent);
    }

    public <T> void goToActivity(final Class<T> clazz, final Bundle bundle) {
        final Intent intent = new Intent(BaseActivity.this, clazz);
        intent.putExtra(Constant.DATA, bundle);
        startActivity(intent);
    }

    public void logout() {
        Toast.makeText(this, "A sua sessão vai ser terminada em 5 seconds", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                session.setLogin(false);
                session.setLoggedinUser(null);
                session.logout();
                goToActivity(SplashScreenActivity.class);
            }
        }, 5000);
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
}
