package cabtrip.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//General utility class
public class CommonUtils {

	//validating date format
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
}
