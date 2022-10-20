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

/**
 * <p>
 * A class to represent ranges of integer numbers. Fully compatible with
 * <a href="https://www.uml-diagrams.org/multiplicity.html">UML
 * multiplicities</a>, in particular accepts the '*' notation for 'any number'.
 * </p>
 * 
 * @author Shayne Flint - 2012
 *
 */
// Tested OK with version 0.0.5 on 4/4/2019
public class IntegerRange {

	private int first = 0;
	private int last = Integer.MAX_VALUE;

	/**
	 * Constructor from two numbers. Throws an Exception if first &gt; last.
	 * 
	 * @param first the lowest end of the range
	 * @param last  the upper end of the range
	 */
	public IntegerRange(int first, int last) {
		this.first = first;
		this.last = last;
		check();
	}

	/**
	 * Constructor from a String.
	 * <p>
	 * Examples of valid String formats:<br/>
	 * 1..1<br/>
	 * 1..*<br/>
	 * 0..1<br/>
	 * 0..*<br/>
	 * 5..12<br/>
	 * </p>
	 * 
	 * @param str a String representing an integer range.
	 */
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
			throw new IllegalArgumentException("'" + str + "' is not a valid integer range");
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
//			throw new something("'" + str + "' is not a valid integer range");
//		check();
//	}

	@Override
	public boolean equals(Object value) {
		IntegerRange test = (IntegerRange) value;
		return first == test.first && last == test.last;
	}

	private void check() {
		if (first > last)
			throw new IllegalArgumentException("Invalid integer range: " + first + " must <= " + last);
	}

	/**
	 * Checks that a value is within the range.
	 * 
	 * @param value the value to test
	 * @return {@code true} if the value is ≥ range minimum and ≤ range maximum.
	 */
	public boolean inRange(int value) {
		return (value >= first) && (value <= last);
	}

	/**
	 * Checks that a value is within the range. Throws an Exception if the value is
	 * ≥ range minimum and ≤ range maximum.
	 * 
	 * @param value the value to test
	 */
	public void check(int value) {
		if (!inRange(value))
			throw new IllegalArgumentException(value + " is not in integer range " + first + ".." + last);
	}

	/**
	 * Creates an instance of {@code IntegerRange} from a String.
	 * 
	 * @param str the string to read
	 * @return the new instance
	 */
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

	@Override
	public String toString() {
		return integerString(first) + ".." + integerString(last);
	}

	/**
	 * Reset the range lower end.
	 * 
	 * @param first the new lower end.
	 * @return this instance for agile programming
	 */
	public IntegerRange setFirst(int first) {
		this.first = first;
		return this;
	}

	/**
	 * @return The range lower end.
	 */
	public int getFirst() {
		return first;
	}

	/**
	 * Reset the range upper end.
	 * 
	 * @param last the new upper end.
	 * @return this (fluid interface).
	 */
	public IntegerRange setLast(int last) {
		this.last = last;
		return this;
	}

	/**
	 * @return The range upper end.
	 */
	public int getLast() {
		return last;
	}

	/**
	 * Checks if a range is contained in this one.
	 * 
	 * @param r the IntegerRange to test
	 * @return {@code true} if {@code r} is fully contained in this instance range.
	 */
	public boolean contains(IntegerRange r) {
		return (r.getFirst() >= first && r.getLast() <= last);
	}
}
