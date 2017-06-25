package tut.flightbookingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import tut.flightbookingsystem.base.AuthBaseActivity;
import tut.flightbookingsystem.manager.RequestManager;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.util.Constant;

public class LoginActivity extends AuthBaseActivity {

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            final boolean isLoggedIn = data.getBoolean(Constant.LOGGEDIN);
            viewMain(isLoggedIn);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SessionManager session = new SessionManager(this);
        final Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        RequestManager.getInitialData(session, requestHandler);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((AutoCompleteTextView) findViewById(R.id.email)).getText().toString();
                final String password = ((AutoCompleteTextView) findViewById(R.id.password)).getText().toString();
                RequestManager.login(session, LoginActivity.this, email, password, requestHandler);
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

    private void viewMain(final  boolean isLoggedIn) {
        if (isLoggedIn) {
            final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }
}

