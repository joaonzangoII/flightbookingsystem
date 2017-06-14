package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import tut.flightbookingsystem.base.AuthBaseActivity;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;

public class SplashScreenActivity extends AuthBaseActivity {
    private Button btnLogin;
    private Button btnRegister;
    private ProgressBar progressBar;

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final SessionManager session = new SessionManager(SplashScreenActivity.this);
            progressBar.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            if (data.getBoolean(Constant.DONE_LOADING)) {
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SessionManager session = new SessionManager(this);
        viewMain(session.isLoggedIn());
        setContentView(R.layout.activity_splash_screen);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnRegister = (Button) findViewById(R.id.email_register_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        // REQUEST INITIAL DATA
        RequestManager.getInitialData(session, requestHandler);

        viewMain(session);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewLogin();
            }
        });

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
            final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    private void viewLogin() {
        final Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
