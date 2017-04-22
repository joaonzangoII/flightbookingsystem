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
                             final String password) {
        // Tag used to cancel the request
        final String tag_string_req = "req_login";
        setLoading(context, true);
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
                            } else {
                                session.setLogin(false);
                                // Error in login. Get the error message
                                final String errorMsg = jObj.getString("mensagens");
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

    public static void checkAvailability(final SessionManager session,
                                         final Handler requestHandler) {
        final Message msg = requestHandler.obtainMessage();
        final Bundle bundle = new Bundle();
        final String url = session.getServerUrl() + RouteManager.CHECK_AVAILABILITY;
        final String tag_string_req = "req_checkAvailability";
        final StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(tag_string_req, response);
                bundle.putString(Constant.STR_SCHEDULE, response);
                msg.setData(bundle);
                requestHandler.sendMessage(msg);

                //final PaginatedServicos paginatedServicos = gson.fromJson(response, type);
                //session.setServicosByCategoria(gson.toJson(paginatedServicos.data));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                //session.setServicosByCategoria("[]");
                requestHandler.sendMessage(msg);
            }
        });
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private static void setLoading(final Context context,
                                   final boolean isLoading) {
        if (isLoading) {
            pDialog = new ProgressDialog(context);
            pDialog.show();
            pDialog.setMessage("Comentando...");
        } else {
            pDialog.dismiss();
        }
    }

    public static Context getApplicationContext() {
        return MyApplication.getInstance();
    }
}
