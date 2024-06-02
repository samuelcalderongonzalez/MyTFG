package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The date utils class
 * 
 * @author Samuel Calderón González
 *
 */
public class DateUtils {
	/**
	 * Maps a provided string to a date
	 * 
	 * @param stringDate
	 * @return Date
	 */
	public static Date mapStringToDate(String stringDate) {
		try {
			return new SimpleDateFormat(Constants.DATE_PATTERN).parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Maps a date to string format
	 * 
	 * @param date
	 * @return string
	 */
	public static String mapDateToString(Date date) {
		DateFormat df = new SimpleDateFormat(Constants.DATE_PATTERN);
		return df.format(date);
	}

	/**
	 * Gets the current date with dd-MM-yyyy format
	 * 
	 * @return Date
	 */
	public static Date getCurrentDate() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		return date.getTime();
	}

	/**
	 * Gets the current date as a string with dd-MM-yyyy format
	 * 
	 * @return String
	 */
	public static String getStringCurrentDate() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		return mapDateToString(date.getTime());
	}
}
