package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date mapStringToDate(String stringDate) {
		try {
			return new SimpleDateFormat(Constants.DATE_PATTERN).parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String mapDateToString(Date date) {
		DateFormat df =	new SimpleDateFormat(Constants.DATE_PATTERN);
		return df.format(date);
	}
	
	public static Date getCurrentDate() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		return date.getTime();
	}
	
	public static String getStringCurrentDate() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		return mapDateToString(date.getTime());
	}
}
