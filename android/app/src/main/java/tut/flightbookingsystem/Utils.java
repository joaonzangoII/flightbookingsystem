package tut.flightbookingsystem;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utils {
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
}
