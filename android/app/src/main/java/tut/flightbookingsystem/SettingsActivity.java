package tut.flightbookingsystem;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.MenuItem;

import tut.flightbookingsystem.base.AppCompatPreferenceActivity;


public class SettingsActivity extends AppCompatPreferenceActivity {

    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_screen);

        bindPreferenceSummaryToValue(findPreference("apiUrl"));
    }

    private static void bindPreferenceSummaryToValue(final Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.action_settings));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(final Preference preference,
                                          final Object value) {
            String stringValue = value.toString();

            if (preference instanceof EditTextPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                final EditTextPreference editTextPreference = (EditTextPreference) preference;
                if (preference.getKey().equals("apiUrl")) {
                    if (TextUtils.isEmpty(stringValue)) {
                        // Empty values correspond to 'silent' (no ringtone).
                        preference.setSummary(R.string.pref_default_server_url);

                    } else {
                        final SessionManager sessionManager = new SessionManager(MyApplication.getInstance());
                        sessionManager.setServerUrl(stringValue);
                        preference.setSummary(stringValue);
                    }
                }
            }

            return true;
        }
    };
}
