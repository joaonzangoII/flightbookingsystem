package tut.flightbookingsystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalDate {

    public static String formatDate(final String date) {
        final SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");
        final DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd @ hh:mm:ss");
        try {
            final Date parsedDate = originalFormat.parse(date);
            return targetFormat.format(parsedDate);
        } catch (Exception e) {
            return date;
        }
    }

    public static String format(final String date) {
        try {
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");
            return format.format(Date.parse(date));
        } catch (Exception e) {
            return date;
        }
    }
}
