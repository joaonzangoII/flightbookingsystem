package tut.flightbookingsystem.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import tut.flightbookingsystem.R;

public class Utils<T> {

    public static String properCase(final String inputVal) {
        // Empty strings should be returned as-is.
        if (inputVal.length() == 0) return "";
        // Strings with only one character uppercased.
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        // Otherwise uppercase first letter, lowercase the rest.
        return inputVal.substring(0, 1).toUpperCase()
                + inputVal.substring(1).toLowerCase();
    }

    public static <T> String jsonToString(final Object object) {
        final Gson gson = new GsonBuilder().create();
        return gson.toJson(object);
    }

    public static String stringFormat(final String var) {
        return String.format(Locale.getDefault(), "%1$s", var);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static String intFormat(final int var) {
        return String.format(Locale.getDefault(), "%1$d", var);
    }

    public static void showKeyboard(final Context context,
                                    final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void showDialog(final Context context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.cust_dialog);
        alertDialogBuilder.setTitle("No data");
        alertDialogBuilder.setMessage("There are no flights available for your query");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialogBuilder.create().show();
    }

    public static String getDate(final String strDate) {
        final SimpleDateFormat fromUser = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        final SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return myFormat.format(fromUser.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTime(final String strDate) {
        final SimpleDateFormat fromUser = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        final SimpleDateFormat myFormat = new SimpleDateFormat("hh:mm:ss");
        try {
            return myFormat.format(fromUser.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Object[][] splitArray(final Object[] arrayToSplit,
                                        final int chunkSize) {
        if (chunkSize <= 0) {
            return null;  // just in case :)
        }
        // first we have to check if the array can be split in multiple
        // arrays of equal 'chunk' size
        int rest = arrayToSplit.length % chunkSize;  // if rest>0 then our last array will have less elements than the others
        // then we check in how many arrays we can split our input array
        int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0); // we may have to add an additional array for the 'rest'
        // now we know how many arrays we need and create our result array
        Object[][] arrays = new Object[chunks][];
        // we create our resulting arrays by copying the corresponding
        // part from the input array. If we have a rest (rest>0), then
        // the last array will have less elements than the others. This
        // needs to be handled separately, so we iterate 1 times less.
        for (int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++) {
            // this copies 'chunk' times 'chunkSize' elements into a new array
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        }
        if (rest > 0) { // only when we have a rest
            // we copy the remaining elemeLnts into the last chunk
            arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
        }
        return arrays; // that's it
    }


    public static void logVolleyMessage(final VolleyError error,
                                        final String TAG) {
        Log.e(TAG, error.getMessage() != null ? error.getMessage() : error.toString());
    }

    public static String getVolleyMessage(final VolleyError error) {
        String body = "";
        //get status code here
        if (error.getMessage() != null) {
            body = error.getMessage();
            //  if (error.networkResponse != null) {
            //           final String statusCode = String.valueOf(error.networkResponse.statusCode);
            //            //get response body and parse with appropriate encoding
            //            if (error.networkResponse.data != null) {
            //                try {
            //                    body = new String(error.networkResponse.data, "UTF-8");
            //                } catch (final UnsupportedEncodingException e) {
            //                    e.printStackTrace();
            //                }
            //            }
        } else {
            body = error.toString();
        }

        return body;
    }
}
