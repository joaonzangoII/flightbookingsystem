package tut.flightbookingsystem.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tut.flightbookingsystem.model.Airport;
import tut.flightbookingsystem.model.Booking;
import tut.flightbookingsystem.model.Country;
import tut.flightbookingsystem.model.Drink;
import tut.flightbookingsystem.model.FlightSeat;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.model.Passenger;
import tut.flightbookingsystem.model.Schedule;
import tut.flightbookingsystem.model.TravelClass;
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
    private static final String KEY_COUNTRIES = "countries";
    private static final String KEY_PASSENGER = "passenger";
    private static final String KEY_BOOKING_PASSENGERS = "booking_passengers";
    private static final String KEY_TRAVEL_CLASSES = "travel_classes";
    private static final String KEY_AIRPORTS = "airports";
    private static final String KEY_FOODS = "foods";
    private static final String KEY_DRINKS = "drinks";
    private static final String KEY_SCHEDULE = "schedule";
    private static final String KEY_MY_BOOKINGS = "my_bookings";
    private static final String FLIGHT_SEATS = "flight_seats";
    private static final String KEY_LATEST_SCHEDULES = "latest_schedules";

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

    public void setPassenger(final String passenger) {
        editor.putString(KEY_PASSENGER, passenger);
        editor.apply();  // commit changes
    }

    public Passenger getPassenger() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<Passenger>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_PASSENGER, null), type);
    }

    public void setBookingPassengers(final String passengers) {
        editor.putString(KEY_BOOKING_PASSENGERS, passengers);
        editor.apply();  // commit changes
    }

    public List<Passenger> getBookingPassengers() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Passenger>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_BOOKING_PASSENGERS, "[]"), type);
    }

    public void addorUpdateToBookingPassengers(final Passenger passenger) {
        final List<Passenger> passengers = getBookingPassengers();
        boolean isAdded = false;
        int foundAt = 0;
        for (int x = 0; x < passengers.size(); x++) {
            final Passenger cA = passengers.get(x);
            if (cA.id == passenger.id) {
                isAdded = true;
                foundAt = x;
            }
        }

        final Passenger mPassenger;
        if (isAdded) {
            mPassenger = passengers.get(foundAt);
            Log.e("FOUND", String.valueOf(mPassenger.id));
            mPassenger.flight_seat_id = passenger.flight_seat_id ;
            passengers.set(foundAt, mPassenger);
            // Log.e("ADDING", mCartItem.toString());
        } else {
            mPassenger = passenger;
            passengers.add(mPassenger);
        }

        final Gson gson = new GsonBuilder().create();
        editor.putString(KEY_BOOKING_PASSENGERS, gson.toJson(passengers));
        editor.apply();
    }

    public void setSchedule(final String schedule) {
        editor.putString(KEY_SCHEDULE, schedule);
        editor.apply();  // commit changes
    }

    public Schedule getSchedule() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<Schedule>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_SCHEDULE, null), type);
    }

    public void setAirports(final String airports) {
        editor.putString(KEY_AIRPORTS, airports);
        editor.apply();  // commit changes
    }

    public List<Airport> getAirports() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Airport>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_AIRPORTS, "[]"), type);
    }

    public void setFoods(final String foods) {
        editor.putString(KEY_FOODS, foods);
        editor.apply();  // commit changes
    }

    public List<Food> getFoods() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Food>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_FOODS, "[]"), type);
    }

    public void setDrinks(final String drinks) {
        editor.putString(KEY_DRINKS, drinks);
        editor.apply();  // commit changes
    }

    public List<Drink> getDrinks() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Drink>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_DRINKS, "[]"), type);
    }

    public void setTravelClasses(final String travelClasses) {
        editor.putString(KEY_TRAVEL_CLASSES, travelClasses);
        editor.apply();  // commit changes
    }

    public List<TravelClass> getTravelClasses() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<TravelClass>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_TRAVEL_CLASSES, "[]"), type);
    }

    public void setCountries(final String countries) {
        editor.putString(KEY_COUNTRIES, countries);
        editor.apply();  // commit changes
    }

    public List<Country> getCountries() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Country>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_COUNTRIES, "[]"), type);
    }

    public void setLatestSchedules(final String latestSchedules) {
        editor.putString(KEY_LATEST_SCHEDULES, latestSchedules);
        editor.apply();  // commit changes
    }

    public List<Schedule> getLatestSchedules() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Booking>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_LATEST_SCHEDULES, "[]"), type);
    }

    public void setMyBookings(final String myBookings) {
        editor.putString(KEY_MY_BOOKINGS, myBookings);
        editor.apply();  // commit changes
    }

    public List<Booking> getMyBookings() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<Booking>>() {
        }.getType();
        return gson.fromJson(pref.getString(KEY_MY_BOOKINGS, "[]"), type);
    }

    public void setFlightSeats(final String flight_seats) {
        editor.putString(FLIGHT_SEATS, flight_seats);
        editor.apply();  // commit changes
    }

    public List<FlightSeat> getFlightSeats() {
        final Gson gson = new GsonBuilder().create();
        final Type type = new TypeToken<List<FlightSeat>>() {
        }.getType();
        return gson.fromJson(pref.getString(FLIGHT_SEATS, "[]"), type);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGEDIN, false);
        editor.putString(KEY_LOGGEDIN_USER, null);
        editor.apply(); // commit changes
    }

    public void setServerUrl(final String serverUrl) {
        editor.putString(KEY_SERVER_URL, serverUrl);
        editor.apply();  // commit changes
    }

    public String getServerUrl() {
        return pref.getString(KEY_SERVER_URL, "https://www.flightbookingsystem.tchinossanda.com");
    }
}
