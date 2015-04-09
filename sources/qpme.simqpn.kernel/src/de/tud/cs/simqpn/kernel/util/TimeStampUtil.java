package de.tud.cs.simqpn.kernel.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampUtil {
	//private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");

	public static String format(Date date){
		return TIMESTAMP_FORMAT.format(date);

	}

}
