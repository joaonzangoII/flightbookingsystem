package tut.flightbookingsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import tut.flightbookingsystem.model.User;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "FlightBookingSystem";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_LOGGEDIN_USER = "loggedUser";
    private static final String KEY_SERVER_URL = "server_url";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(final boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.apply(); // commit changes
    }

    public void setLoggedinUser(final String user) {
        editor.putString(KEY_LOGGEDIN_USER, user);
        editor.apply(); // commit changes
    }

    public User getLoggedInUser() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<User>() {
        }.getType();

        final String data = pref.getString(KEY_LOGGEDIN_USER, null);
        return gson.fromJson(data, type);
    }

    public void setServerUrl(final String serverUrl) {
        editor.putString(KEY_SERVER_URL, serverUrl);
        editor.apply();  // commit changes
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getServerUrl() {
        return "http://10.0.0.108:8000";
        // return "https://www.staging.tchinossanda.com";
        //return pref.getString(KEY_SERVER_URL, "http://www.contextaware.lubelnaportal.com/");
    }

}
