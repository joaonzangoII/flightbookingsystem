package tut.flightbookingsystem;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

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

    public static Object[][] splitArray(final Object[] arrayToSplit,
                                        final int chunkSize){
        if(chunkSize<=0){
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
        for(int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++){
            // this copies 'chunk' times 'chunkSize' elements into a new array
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        }
        if(rest > 0){ // only when we have a rest
            // we copy the remaining elemeLnts into the last chunk
            arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
        }
        return arrays; // that's it
    }
}
