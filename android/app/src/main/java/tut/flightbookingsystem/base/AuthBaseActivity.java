package tut.flightbookingsystem.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import tut.flightbookingsystem.util.Constant;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.SettingsActivity;

/**
 * Created by joaonzangoii on 5/18/17.
 */

public class AuthBaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.special, menu);
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

    public <T> void goToActivity(final Class<T> clazz) {
        final Intent intent = new Intent(AuthBaseActivity.this, clazz);
        startActivity(intent);
    }

    public <T> void goToActivity(final Class<T> clazz,
                                 final Bundle bundle) {
        final Intent intent = new Intent(AuthBaseActivity.this, clazz);
        intent.putExtra(Constant.DATA, bundle);
        startActivity(intent);
    }

}
