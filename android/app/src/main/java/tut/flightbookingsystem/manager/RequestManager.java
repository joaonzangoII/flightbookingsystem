package tut.flightbookingsystem.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tut.flightbookingsystem.Constant;
import tut.flightbookingsystem.MyApplication;
import tut.flightbookingsystem.SessionManager;
import tut.flightbookingsystem.model.User;
import tut.flightbookingsystem.util.Utils;

public class RequestManager {
    private static String TAG = RequestManager.class.getName();
    private static ProgressDialog pDialog;

    public static void register(final SessionManager session,
                                final Context context,
                                final String first_name,
                                final String middle_name,
                                final String last_name,
                                final String id_number,
                                final String phone,
                                final String email,
                                final String password,
                                final long country_id,
                                final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String tag_string_req = "req_register";
        setLoading(context, "Registering...", true);
        final String url = session.getServerUrl() + RouteManager.REGISTER;
        final StringRequest strReq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d(TAG, "Registration Response: " + response);
                        // setLoading(false);
                        try {
                            final JSONObject jObj = new JSONObject(response);
                            final boolean error = jObj.getBoolean("erro");
                            // Check for error node in json
                            if (!error) {
                                session.setLogin(true);
                                session.setLoggedinUser(null);
                                bundle.putBoolean(Constant.LOGGEDIN, false);
                                bundle.putBoolean(Constant.ERROR, false);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                            } else {
                                session.setLogin(false);
                                session.setLoggedinUser(null);
                                bundle.putBoolean(Constant.LOGGEDIN, false);
                                bundle.putBoolean(Constant.ERROR, true);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                                // Error in login. Get the error message
                                final String errorMsg = jObj.getString("messages");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (final JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Json error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        setLoading(context, false);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Registration");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                setLoading(context, false);
                session.setLogin(false);
                session.setLoggedinUser(null);
                bundle.putBoolean(Constant.LOGGEDIN, false);
                bundle.putBoolean(Constant.ERROR, true);
                msg.setData(bundle);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("middle_name", middle_name);
                params.put("last_name", last_name);
                params.put("id_number", id_number);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);
                params.put("country_id", String.valueOf(country_id));
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void login(final SessionManager session,
                             final Context context,
                             final String email,
                             final String password,
                             final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String tag_string_req = "req_login";
        setLoading(context, "Logging in...", true);
        final String url = session.getServerUrl() + RouteManager.LOGIN;
        final StringRequest strReq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.d(TAG, "Login Response: " + response);
                        // setLoading(false);
                        try {
                            final JSONObject jObj = new JSONObject(response);
                            final boolean error = jObj.getBoolean("erro");
                            // Check for error node in json
                            if (!error) {
                                session.setLogin(true);
                                session.setLoggedinUser(jObj.getJSONObject("user").toString());
                                bundle.putBoolean(Constant.LOGGEDIN, true);
                                bundle.putBoolean(Constant.ERROR, false);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                            } else {
                                session.setLogin(false);
                                session.setLoggedinUser(null);
                                bundle.putBoolean(Constant.LOGGEDIN, false);
                                bundle.putBoolean(Constant.ERROR, true);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                                // Error in login. Get the error message
                                final String errorMsg = jObj.getString("messages");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (final JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        setLoading(context, false);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Utils.logVolleyMessage(error, "Login");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                setLoading(context, false);
                session.setLogin(false);
                session.setLoggedinUser(null);

                bundle.putBoolean(Constant.LOGGEDIN, false);
                bundle.putBoolean(Constant.ERROR, true);
                msg.setData(bundle);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void getTimetable(final SessionManager session,
                                    final Context context,
                                    final long origin_airport_id,
                                    final long destination_airport_id,
                                    final String departure_date,
                                    final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_TIMETABLE;
        final String tag_string_req = "req_get_timetable";
        setLoading(context, "Getting flights..", true);
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        bundle.putString(Constant.STR_SCHEDULE, response);
                        bundle.putBoolean(Constant.ERROR, false);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                        setLoading(context, false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Timetable");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                //session.setServicosByCategoria("[]");
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("origin_airport_id", String.valueOf(origin_airport_id));
                params.put("destination_airport_id", String.valueOf(destination_airport_id));
                params.put("departure_date", departure_date);
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void findFlights(final SessionManager session,
                                   final Context context,
                                   final long origin_airport_id,
                                   final long destination_airport_id,
                                   final String departure_date,
                                   final String return_date,
                                   final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.FIND_FLIGHTS;
        final String tag_string_req = "req_find_flights";
        setLoading(context, "Getting flights..", true);
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        setLoading(context, false);
                        bundle.putString(Constant.STR_SCHEDULE, response);
                        bundle.putBoolean(Constant.ERROR, false);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Find Flights");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                //session.setServicosByCategoria("[]");
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("origin_airport_id", String.valueOf(origin_airport_id));
                params.put("destination_airport_id", String.valueOf(destination_airport_id));
                params.put("departure_date", departure_date);
                params.put("return_date", return_date);
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void makeBooking(final SessionManager session,
                                   final Context context,
                                   final long user_id,
                                   final long departure_flight_id,
                                   final long aircraft_id,
                                   final String passengers,
                                   final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.MAKE_BOOKINGS;
        final String tag_string_req = "req_make_bookings";
        setLoading(context, "Making booking..", true);
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);

                        try {
                            final JSONObject jObj = new JSONObject(response);
                            final boolean error = jObj.getBoolean("erro");
                            if (!error) {
                                bundle.putString(Constant.BOOKING, jObj.getJSONObject("booking").toString());
                                bundle.putBoolean(Constant.ERROR, false);
                                bundle.putBoolean(Constant.IS_BOOKING, true);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                                setLoading(context, false);
                            } else {
                                setLoading(context, false);
                                bundle.putBoolean(Constant.ERROR, true);
                                requestHandler.sendMessage(msg);
                                final String errorMsg = jObj.getString("messages");
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (final JSONException error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        setLoading(context, false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Utils.logVolleyMessage(error, "Make Booking");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("departure_flight_id", String.valueOf(departure_flight_id));
                // params.put("return_flight_id", String.valueOf(return_flight_id));
                params.put("aircraft_id", String.valueOf(aircraft_id));
                params.put("passengers", passengers);
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void updateMeal(final SessionManager session,
                                     final Context context,
                                     final long user_id,
                                     final String passenger,
                                     final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.UPDATE_MEAL;
        final String tag_string_req = "req_update_meal";
        setLoading(context, "Updating Passenger Meal..", true);
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        try {
                            final JSONObject jObj = new JSONObject(response);
                            final boolean error = jObj.getBoolean("erro");
                            if (!error) {
                                bundle.putString(Constant.BOOKING,
                                        jObj.getJSONObject("booking").toString());
                                bundle.putBoolean(Constant.ERROR, false);
                                bundle.putBoolean(Constant.IS_BOOKING, true);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                                setLoading(context, false);
                            } else {
                                setLoading(context, false);
                                bundle.putBoolean(Constant.ERROR, true);
                                requestHandler.sendMessage(msg);
                                final String errorMsg = jObj.getString("messages");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (final JSONException error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        setLoading(context, false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Utils.logVolleyMessage(error, "Update Booking");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("passenger", passenger);
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void deleteMeal(final SessionManager session,
                                  final Context context,
                                  final long user_id,
                                  final String passenger,
                                  final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.DELETE_MEAL;
        final String tag_string_req = "req_delete_meal";
        setLoading(context, "Deleting Passenger Meal..", true);
        final StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        try {
                            final JSONObject jObj = new JSONObject(response);
                            final boolean error = jObj.getBoolean("erro");
                            if (!error) {
                                bundle.putString(Constant.BOOKING,
                                        jObj.getJSONObject("booking").toString());
                                bundle.putBoolean(Constant.ERROR, false);
                                bundle.putBoolean(Constant.IS_BOOKING, true);
                                msg.setData(bundle);
                                requestHandler.sendMessage(msg);
                                setLoading(context, false);
                            } else {
                                setLoading(context, false);
                                bundle.putBoolean(Constant.ERROR, true);
                                requestHandler.sendMessage(msg);
                                final String errorMsg = jObj.getString("messages");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (final JSONException error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        setLoading(context, false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Utils.logVolleyMessage(error, "Update Booking");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("passenger", passenger);
                return params;
            }

        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getInitialData(final SessionManager session,
                                      final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_INITIAL_DATA;
        final String tag_string_req = "req_get_initial_data";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        try {
                            final JSONObject jObj = new JSONObject(response);
                            session.setCountries(jObj.getJSONArray("countries").toString());
                            session.setTravelClasses(jObj.getJSONArray("travelClasses").toString());
                            session.setAirports(jObj.getJSONArray("airports").toString());
                            session.setFoods(jObj.getJSONArray("foods").toString());
                            session.setDrinks(jObj.getJSONArray("drinks").toString());
                            bundle.putBoolean(Constant.ERROR, false);
                            bundle.putBoolean(Constant.DONE_LOADING, true);
                            msg.setData(bundle);
                            requestHandler.sendMessage(msg);
                        } catch (final JSONException error) {
                            /*session.setCountries("[]");
                            session.setTravelClasses("[]");
                            session.setAirports("[]");
                            session.setFoods("[]");
                            session.setDrinks("[]");*/
                            bundle.putBoolean(Constant.ERROR, true);
                            bundle.putBoolean(Constant.DONE_LOADING, true);
                            requestHandler.sendMessage(msg);
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Initial Data");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setCountries("[]");
                session.setTravelClasses("[]");
                session.setAirports("[]");
                session.setFoods("[]");
                session.setDrinks("[]");
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getCountries(final SessionManager session,
                                    final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_COUNTRIES;
        final String tag_string_req = "req_countries";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setCountries(response);
                        bundle.putBoolean(Constant.ERROR, false);
                        bundle.putBoolean(Constant.DONE_LOADING, true);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Countries");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setCountries("[]");
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getFoods(final SessionManager session,
                                final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_FOODS;
        final String tag_string_req = "req_get_foods";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setFoods(response);
                        bundle.putBoolean(Constant.ERROR, false);
                        bundle.putString(Constant.FOODS, response);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Foods");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setFoods("[]");
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getDrinks(final SessionManager session, final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_DRINKS;
        final String tag_string_req = "req_get_drinks";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setDrinks(response);
                        bundle.putString(Constant.DRINKS, response);
                        bundle.putBoolean(Constant.ERROR, false);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Drinks");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setDrinks("[]");
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getAirports(final SessionManager session,
                                   final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_AIRPORTS;
        final String tag_string_req = "req_airports";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setAirports(response);
                        bundle.putBoolean(Constant.ERROR, false);
                        bundle.putBoolean(Constant.IS_GETTING_AIRPORTS, true);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Airports");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setAirports("[]");
                bundle.putBoolean(Constant.ERROR, true);
                bundle.putBoolean(Constant.IS_GETTING_AIRPORTS, false);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getTravelclasses(final SessionManager session,
                                        final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.GET_TRAVEL_CLASSES;
        final String tag_string_req = "req_travel_classes";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setTravelClasses(response);
                        bundle.putBoolean(Constant.ERROR, false);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Travel Classes");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setTravelClasses("[]");
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }


    public static void getMyBookings(final SessionManager session,
                                     final Context context,
                                     final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final User user = session.getLoggedInUser();
        if (user == null) {
            session.logout();
        }
        setLoading(context, "Getting bookings..", true);
        final String url = session.getServerUrl() +
                RouteManager.GET_MY_BOOKINGS + "/" +
                session.getLoggedInUser().id;
        final String tag_string_req = "req_my_bookings";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setMyBookings(response);
                        bundle.putBoolean(Constant.ERROR, false);
                        bundle.putString(Constant.MY_BOOKINGS, response);
                        msg.setData(bundle);
                        setLoading(context, false);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get My Bookings");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setMyBookings("[]");
                bundle.putBoolean(Constant.ERROR, true);
                setLoading(context, false);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getFlightSeats(final SessionManager session,
                                      final long flight_id,
                                      final long travel_class_id,
                                      final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() +
                RouteManager.GET_FLIGHT_SEAT +
                "/" +
                flight_id +
                "/" +
                travel_class_id;
        final String tag_string_req = "req_flight_seat";
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(tag_string_req, response);
                        session.setFlightSeats(response);
                        bundle.putBoolean(Constant.ERROR, false);
                        bundle.putBoolean(Constant.IS_BOOKING, false);
                        bundle.putString(Constant.FLIGHT_SEATS, response);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.logVolleyMessage(error, "Get Flight Seats");
                Toast.makeText(getApplicationContext(),
                        Utils.getVolleyMessage(error),
                        Toast.LENGTH_LONG).show();
                session.setFlightSeats("[]");
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }


    private static void setLoading(final Context context,
                                   final boolean isLoading) {
        setLoading(context, "Loading...", isLoading);
    }

    private static void setLoading(final Context context,
                                   final String message,
                                   final boolean isLoading) {
        if (isLoading) {
            pDialog = new ProgressDialog(context);
            pDialog.show();
            pDialog.setMessage(message);
        } else {
            pDialog.dismiss();
        }
    }

    public static Context getApplicationContext() {
        return MyApplication.getInstance();
    }
}
