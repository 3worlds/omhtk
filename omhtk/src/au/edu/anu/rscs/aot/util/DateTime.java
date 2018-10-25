package au.edu.anu.rscs.aot.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import au.edu.anu.rscs.aot.OmhtkException;


public class DateTime {

	// time is milliseconds since epoch at Greenwich
	//
	private Date date;

	public DateTime() {
		this.date = new Date();
	}

	public DateTime(long time) {
		this.date = new Date(time);
	}

	public DateTime(Date date) {
		this.date = date;
	}

	public DateTime(int year, int month, int day, int hour, int minute, int second, int millis) {
		Calendar cal = new GregorianCalendar(year, month, day, hour, minute, second);
		cal.set(Calendar.MILLISECOND, millis);
		this.date = cal.getTime();
	}

	public DateTime(String dateStr) {
		DateFormat[] formats = {
				DateFormat.getDateInstance(),
				DateFormat.getDateInstance(DateFormat.SHORT),
				DateFormat.getDateInstance(DateFormat.MEDIUM),
				DateFormat.getDateInstance(DateFormat.LONG),
				DateFormat.getDateInstance(DateFormat.FULL),
				format("yyyy/MM/dd HH:mm:ss"),
				format("HH:mm:ss"),
				format("HH:mm"),
				format("HH"),
				format("yyyy/MM/dd"),
				format("dd/MM/yy"),
				format("dd/MM/yyyy hh:mm:ss"),
				format("dd/MM/yy hh:mm:ss"),
				format("dd/MM/yyyy"),
				format("dd/MM/yy")};
		for (DateFormat fmt : formats) {
			try {
				fmt.setLenient(false);
				Date localDate = fmt.parse(dateStr);
				this.date = localDate;
				return;
			} catch (ParseException e) {
//				System.out.println(e);
			}
		}
		throw new OmhtkException("Cannot convert '" + dateStr + "' to a date");
	}

	public Date getDate() {
		return date;	
	}

	public long getTime() {
		return date.getTime();	
	}


	public String dateString() {
		return format("yyyy/MM/dd").format(date);
	}

	public String timeString() {
		return format("HH:mm:ss.SSS").format(date);
	}

	public String milliSecondsString() {
		return format("S").format(date);
	}

	public String dateTimeString() {
		return dateString() + " " + timeString();
	}

	public String dateTimeString(String fmt) {
		return format(fmt).format(date);
	}

	public String dateTimeFileName() {
		return String.format("%016X",  date.getTime());
	}

	public String toString() {
		SimpleDateFormat format = format("dd MMM yyyy 'at' hh:mm:ss.S zzz");
		return format.format(date);
	}

	private SimpleDateFormat format(String fmt) {
		SimpleDateFormat result = new SimpleDateFormat(fmt);
		TimeZone zone = TimeZone.getTimeZone("UTC");
		result.setTimeZone(zone);
		return result;
	}
	
	
	// TESTING
	//

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		System.out.println(dt.dateTimeString()); 
		System.out.println(dt.dateTimeFileName()); 
		System.out.println(new DateTime("2015/2/24 11:13:14"));
		System.out.println(new DateTime("2015/12/30"));
		System.out.println(new DateTime("2015/12/30  11:13"));
		System.out.println(new DateTime("11:13:00"));
		System.out.println(new DateTime("00:11"));
		System.out.println(new DateTime("12"));
	}
}
