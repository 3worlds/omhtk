/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
 *                                                                        *
 **************************************************************************                                       
 *  This file is part of OMHTK (One More Handy Tool Kit).                 *
 *                                                                        *
 *  OMHTK is free software: you can redistribute it and/or modify         *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  OMHTK is distributed in the hope that it will be useful,              *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package au.edu.anu.omhtk.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * A date-time format. Immutable.
 * 
 * @author Shayne Flint - 2012
 *
 */
public class DateTime {

	// time is milliseconds since epoch at Greenwich
	//
	private Date date;

	/**
	 * Constructor using the current time as a date.
	 */
	public DateTime() {
		this.date = new Date();
	}

	/**
	 * Constructor using the argument as a time.
	 * 
	 * @param time The time to clone.
	 */
	public DateTime(long time) {
		this.date = new Date(time);
	}

	/**
	 * Constructor using the argument as a date.
	 * 
	 * @param date The date to clone.
	 */
	public DateTime(Date date) {
		this.date = date;
	}

	/**
	 * Constructor using the arguments to build a time. Self-explained arguments.
	 * 
	 * @param year   The year
	 * @param month  The month
	 * @param day    The day
	 * @param hour   The hour
	 * @param minute The minute
	 * @param second The second
	 * @param millis The milliseconds
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second, int millis) {
		Calendar cal = new GregorianCalendar(year, month, day, hour, minute, second);
		cal.set(Calendar.MILLISECOND, millis);
		this.date = cal.getTime();
	}

	/**
	 * Constructor using a String argument.
	 * 
	 * @param dateStr A date string arg that can be parsed by a java DateFormat.
	 */
	public DateTime(String dateStr) {
		DateFormat[] formats = { DateFormat.getDateInstance(), DateFormat.getDateInstance(DateFormat.SHORT),
				DateFormat.getDateInstance(DateFormat.MEDIUM), DateFormat.getDateInstance(DateFormat.LONG),
				DateFormat.getDateInstance(DateFormat.FULL), format("yyyy/MM/dd HH:mm:ss"), format("HH:mm:ss"),
				format("HH:mm"), format("HH"), format("yyyy/MM/dd"), format("dd/MM/yy"), format("dd/MM/yyyy hh:mm:ss"),
				format("dd/MM/yy hh:mm:ss"), format("dd/MM/yyyy"), format("dd/MM/yy") };
		for (DateFormat fmt : formats) {
			try {
				fmt.setLenient(false);
				Date localDate = fmt.parse(dateStr);
				this.date = localDate;
				return;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return The date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return The time in milliseconds since 1st of January 1970, 00:00:00 GMT.
	 */
	public long getTime() {
		return date.getTime();
	}

	/**
	 * @return The date in "yyyy/MM/dd" format.
	 */
	public String dateString() {
		return format("yyyy/MM/dd").format(date);
	}

	/**
	 * @return The date, with time in "HH:mm:ss.SSS" format.
	 */
	public String timeString() {
		return format("HH:mm:ss.SSS").format(date);
	}

	/**
	 * @return The date, with time in milliseconds.
	 */
	public String milliSecondsString() {
		return format("S").format(date);
	}

	/**
	 * @return The date as a "date time" format.
	 */
	public String dateTimeString() {
		return dateString() + " " + timeString();
	}

	/**
	 * The date in custom format.
	 * 
	 * @param fmt the format to use for time
	 * @return The date in custom format.
	 */
	public String dateTimeString(String fmt) {
		return format(fmt).format(date);
	}

	/**
	 * @return The date in a file name compatible format, ie a String that can be
	 *         used in a file name.
	 */
	public String dateTimeFileName() {
		return String.format("%016X", date.getTime());
	}

	@Override
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

//	public static void main(String[] args) {
//		DateTime dt = new DateTime();
//		System.out.println(dt.dateTimeString()); 
//		System.out.println(dt.dateTimeFileName()); 
//		System.out.println(new DateTime("2015/2/24 11:13:14"));
//		System.out.println(new DateTime("2015/12/30"));
//		System.out.println(new DateTime("2015/12/30  11:13"));
//		System.out.println(new DateTime("11:13:00"));
//		System.out.println(new DateTime("00:11"));
//		System.out.println(new DateTime("12"));
//	}
}
