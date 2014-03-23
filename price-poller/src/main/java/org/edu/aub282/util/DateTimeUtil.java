package org.edu.aub282.util;


import java.sql.Timestamp;
import java.util.Date;


/**
 * 
 * @author ambika_b
 *
 */
public class DateTimeUtil {

	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(new Date().getTime());
	}
	
}