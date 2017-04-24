package tut.flightbookingsystem;

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

public class RequestManager {
    private static String TAG = RequestManager.class.getName();
    private static ProgressDialog pDialog;

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
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
                // Posting parameters to login url
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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //session.setServicosByCategoria("[]");
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //session.setServicosByCategoria("[]");
                setLoading(context, false);
                bundle.putBoolean(Constant.ERROR, true);
                requestHandler.sendMessage(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
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

    public static void getFoods(final SessionManager session, final Handler requestHandler) {
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
                        //bundle.putBoolean(Constant.ERROR, true);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                session.setFoods("[]");
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
                        //bundle.putBoolean(Constant.ERROR, true);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                session.setDrinks("[]");
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getAirports(final SessionManager session, final Handler requestHandler) {
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
                        //bundle.putBoolean(Constant.ERROR, true);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                session.setAirports("[]");
                requestHandler.sendMessage(msg);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    public static void getTravelclasses(final SessionManager session, final Handler requestHandler) {
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
                        //bundle.putBoolean(Constant.ERROR, true);
                        msg.setData(bundle);
                        requestHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                session.setTravelClasses("[]");
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
