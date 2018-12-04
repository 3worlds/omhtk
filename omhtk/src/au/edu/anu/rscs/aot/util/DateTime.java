/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2018: Shayne FLint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent, plus some other utilities. The kind of things you need    *
 *  in all software projects and keep rebuilding all the time.            *
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
 *  along with UIT.  If not, see <https://www.gnu.org/licenses/gpl.html>. *
 *                                                                        *
 **************************************************************************/
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
