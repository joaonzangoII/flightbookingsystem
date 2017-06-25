package tut.flightbookingsystem.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tut.flightbookingsystem.R;

public class BookingStatus {
    public static final String BOOKED = "booked";
    public static final String PENDING = "pending";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({BOOKED, PENDING})
    public @interface Status {

    }

    public static Drawable getColor(final Context context,
                                    final @Status String status) {
        switch (status) {
            case BOOKED:
                return context.getResources()
                        .getDrawable(R.drawable.red_circle);
            case PENDING:
                return context.getResources()
                        .getDrawable(R.drawable.red_circle);
            default:
                return context.getResources()
                        .getDrawable(R.drawable.red_circle);
        }
    }
}
