package util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class HelperUtils {
	public static String getDateTime(long milliSecond) {
		LocalDateTime date=Instant.ofEpochMilli(milliSecond).atZone(ZoneId.systemDefault()).toLocalDateTime();
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(format);
	}
	public static long minusDates(int days) {
		Instant dates=Instant.ofEpochMilli(System.currentTimeMillis());
		Instant afterdate=dates.minus(Period.ofDays(days));
		return afterdate.toEpochMilli();
	}
	public static String getReferenceId(Long millisecond) {
		Random r=new Random();
		String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char letter=alphabet.charAt(r.nextInt(alphabet.length()));
		String referenceId=String.valueOf(letter).concat(String.valueOf(millisecond%1666670000000l));
		return referenceId;
	}
}
