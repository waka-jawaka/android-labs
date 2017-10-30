package algie.lab3.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by me on 24.10.17.
 */

public class DateFormat {

    private static final String FORMAT = "d EEE HH:mm";

    public String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT, Locale.ENGLISH);
        return sdf.format(date);
    }

    public String convertDBDateToView(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT, Locale.ENGLISH);
        try {
            Date date = sdf.parse(datetime);
            sdf = new SimpleDateFormat(FORMAT, Locale.getDefault());
            datetime = sdf.format(date);
        } catch (ParseException e) { }
        return datetime;
    }
}
