package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity {

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SessionManager session = new SessionManager(this);
        viewMain(session.isLoggedIn());
        setContentView(R.layout.activity_splash_screen);
        RequestManager.getTravelclasses(session, requestHandler);
        RequestManager.getAirports(session, requestHandler);
        RequestManager.getFoods(session, requestHandler);
        RequestManager.getDrinks(session, requestHandler);

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
        viewMain(session.isLoggedIn());
    }

    private void viewMain(final boolean isLoggedIn) {
        if (isLoggedIn) {
            final Intent intent = new Intent(SplashScreenActivity.this, NavigationDrawerActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
