package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SessionManager session = new SessionManager(this);
        final Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);

        //viewMain(session);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
                final String password = ((AutoCompleteTextView) findViewById(R.id.password)).getText().toString();
                // RequestManager.login(session, LoginActivity.this, email, password);
                viewMain(session);
            }
        });

        final Button btnRegister = (Button) findViewById(R.id.email_register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void viewMain(final SessionManager session) {
        // if (session.isLoggedIn()) {
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
        //  }
    }
}

