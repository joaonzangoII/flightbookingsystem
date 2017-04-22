package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SessionManager session = new SessionManager(this);
        final Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        viewMain(session);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        final Button btnRegister = (Button) findViewById(R.id.email_register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void viewMain(final SessionManager session) {
        if (session.isLoggedIn()) {
            final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
