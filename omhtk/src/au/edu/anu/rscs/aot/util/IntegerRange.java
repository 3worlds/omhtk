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

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * A property type for multiplicities (i.e. accepts the '*' notation for 'any
 * number').
 * 
 * @author Shayne Flint - 2012?
 *
 */
// Tested OK with version 0.0.5 on 4/4/2019
public class IntegerRange {

	private int first = 0;
	private int last = Integer.MAX_VALUE;

	public IntegerRange(int first, int last) {
		this.first = first;
		this.last = last;
		check();
	}

	public IntegerRange(String str) {
		int idx = str.indexOf("..");
		if (idx > 0 && (str.length() - idx) > 2) {
			String startStr = str.substring(0, idx);
			String endStr = str.substring(idx + 2);
			try {
				first = Integer.valueOf(startStr);
			} catch (NumberFormatException e) {
				first = Integer.MIN_VALUE;
			}
			try {
				last = Integer.valueOf(endStr);
			} catch (NumberFormatException e) {
				last = Integer.MAX_VALUE;
			}
		} else
			throw new OmhtkException("'" + str + "' is not a valid integer range");
		check();
	}

//	public IntegerRange(String str) {
//		int idx = str.indexOf("..");
//		if (idx > 0 && (str.length() - idx) > 2) {
//			String startStr = str.substring(0, idx);
//			String endStr   = str.substring(idx+2);
//			first = Integer.valueOf(startStr);
//			if (endStr.equals("*"))
//				last = Integer.MAX_VALUE;
//			else
//				last = Integer.valueOf(endStr);
//		} else
//			throw new OmhtkException("'" + str + "' is not a valid integer range");
//		check();
//	}

	@Override
	public boolean equals(Object value) {
		IntegerRange test = (IntegerRange) value;
		return first == test.first && last == test.last;
	}

	private void check() {
		if (first > last)
			throw new OmhtkException("Invalid integer range: " + first + " must <= " + last);
	}

	public boolean inRange(int value) {
		return (value >= first) && (value <= last);
	}

	public void check(int value) {
		if (!inRange(value))
			throw new OmhtkException(value + " is not in integer range " + first + ".." + last);
	}

	public static IntegerRange valueOf(String str) {
		return new IntegerRange(str);
	}

	private String integerString(int i) {
		if (i == Integer.MIN_VALUE)
			return "MIN_INTEGER";
		if (i == Integer.MAX_VALUE)
			return "*";
		return String.valueOf(i);
	}

	public String toString() {
		return integerString(first) + ".." + integerString(last);
	}

	public IntegerRange setFirst(int first) {
		this.first = first;
		return this;
	}

	public int getFirst() {
		return first;
	}

	public IntegerRange setLast(int last) {
		this.last = last;
		return this;
	}

	public int getLast() {
		return last;
	}
	
	public boolean contains (IntegerRange r) {
		return (r.getFirst()>=first && r.getLast()<=last);
	}
}
