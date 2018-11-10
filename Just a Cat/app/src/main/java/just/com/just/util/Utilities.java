package just.com.just.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// A class of stand-alone utility methods
public final class Utilities
{

    private Utilities() {
        throw new AssertionError("No Utilities instances for you!");
    } // suppress constructor
    /**
     * getDateAsString
     * Convert a date into a string
     *
     * @param date   the date
     * @param format the format in which to return the string
     * @return the new formatted date string
     */
    public static String getDateAsString(Date date, String format, String timezone)
    {
        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        if (timezone == null) formatter.setTimeZone(TimeZone.getDefault());
        else formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        return formatter.format(date);
    }

    /**
     * getStringAsDate
     *
     * @param dateString a string in date format
     * @param format     the resulting date format
     * @return a new date in the specified format
     */
    public static Date getStringAsDate(String dateString, String format, String timezone)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        if (timezone == null) formatter.setTimeZone(TimeZone.getDefault());
        else formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        try
        {
            date = formatter.parse(dateString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }
}
