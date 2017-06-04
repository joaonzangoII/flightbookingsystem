package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import tut.flightbookingsystem.adapter.CountrySpinnerAdapter;
import tut.flightbookingsystem.base.AuthBaseActivity;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.model.Country;

public class RegisterActivity extends AuthBaseActivity {
    // private String [] countries = {"South Africa", "Botswana", "Lesotho", "Swaziland", "Mozambique", "Zimbabwe"};

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final boolean isLoggedIn = data.getBoolean(Constant.LOGGEDIN);
            final boolean error = data.getBoolean(Constant.ERROR);
            if (!error) {
                Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                viewMainOrLogin(isLoggedIn);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");

        final SessionManager session = new SessionManager(this);
        final List<Country> countries = session.getCountries();

        final CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(this, R.layout.spinners_item_layout, countries);
        final Spinner countriesSpinner = (Spinner) findViewById(R.id.country_id);
        countriesSpinner.setAdapter(adapter);

        final Button btnRegister = (Button) findViewById(R.id.email_register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstnames = ((AutoCompleteTextView) findViewById(R.id.firstnames)).getText().toString();
                final String surname = ((AutoCompleteTextView) findViewById(R.id.surname)).getText().toString();
                final String id_number = ((AutoCompleteTextView) findViewById(R.id.id_number)).getText().toString();
                final String phone = ((AutoCompleteTextView) findViewById(R.id.phone)).getText().toString();
                final String email = ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
                final String password = ((AutoCompleteTextView) findViewById(R.id.password)).getText().toString();
                final long country_id = countriesSpinner.getSelectedItemId();

                RequestManager.register(session,
                        RegisterActivity.this,
                        firstnames,
                        surname,
                        id_number,
                        phone,
                        email,
                        password,
                        country_id,
                        requestHandler);
            }
        });

    }

    private void viewMainOrLogin(final boolean isLoggedIn) {
        final Intent intent;
        if (isLoggedIn) {
            intent = new Intent(RegisterActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        } else {
            intent = new Intent(RegisterActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
