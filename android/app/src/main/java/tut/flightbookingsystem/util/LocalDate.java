package tut.flightbookingsystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocalDate {

    public static String formatDate(final String date) {
        final SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-d hh:mm:ss",
                Locale.getDefault());
        final DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        try {
            final Date parsedDate = originalFormat.parse(date);
            return targetFormat.format(parsedDate);
        } catch (final Exception e) {
            return date;
        }
    }

    public static String getTime(final String date) {
        final SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-d hh:mm:ss",
                Locale.getDefault());
        final DateFormat targetFormat = new SimpleDateFormat("hh:mm:ss",
                Locale.getDefault());
        try {
            final Date parsedDate = originalFormat.parse(date);
            return targetFormat.format(parsedDate);
        } catch (Exception e) {
            return date;
        }
    }

    public static String format(final String date) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d hh:mm:ss",
                    Locale.getDefault());
            return format.format(Date.parse(date));
        } catch (Exception e) {
            return date;
        }
    }
}
