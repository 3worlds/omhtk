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
package fr.cnrs.iees.omhtk.utils;

import java.util.Objects;

/**
 * A (simple) class for mathematical intervals. Handles closed, open, half-open
 * and half-closed intervals; and infinite values.
 * <p>
 * Immutable. Comes with method to compare interval, check overlap, etc.
 * </p>
 * <p>
 * The constructor for this class is private. To get an instance, use the static
 * methods provided here.
 * </p>
 * 
 * @author Jacques Gignoux - 12 juin 2019
 *
 */
// Tested OK with version 0.1.4 on 14/6/2019
public class Interval {

	private double sup = Double.POSITIVE_INFINITY;
	private double inf = Double.NEGATIVE_INFINITY;
	private boolean openSup = true;
	private boolean openInf = true;
	// hash code for faster comparison in maps
	private int hash = 0;

	private Interval(double inf, double sup, boolean openInf, boolean openSup) {
		super();
		if (inf > sup)
			throw new IllegalArgumentException("sup (here " + sup + ") must be greater or equal to inf (here " + inf + ")");
		this.sup = sup;
		this.inf = inf;
		this.openInf = openInf;
		this.openSup = openSup;
	}

	/**
	 * @param inf closed lower end
	 * @param sup closed upper end.
	 * @return new closed interval [inf,sup]
	 */
	public static Interval newInstance(double inf, double sup) {
		return new Interval(inf, sup, false, false);
	}

	/**
	 * @param inf lower end
	 * @param sup upper end.
	 * @return new closed interval [inf,sup]
	 */
	public static Interval closed(double inf, double sup) {
		return new Interval(inf, sup, false, false);
	}

	/**
	 * @param inf lower end
	 * @param sup upper end
	 * @return new open interval ]inf,sup[
	 */
	public static Interval open(double inf, double sup) {
		return new Interval(inf, sup, true, true);
	}

	/**
	 * @param inf lower end
	 * @param sup upper end
	 * @return new half open interval ]inf,sup]
	 */
	public static Interval halfOpenInf(double inf, double sup) {
		return new Interval(inf, sup, true, false);
	}

	/**
	 * @param inf lower end
	 * @param sup upper end
	 * @return new half open interval [inf,sup[
	 */
	public static Interval halfOpenSup(double inf, double sup) {
		return new Interval(inf, sup, false, true);
	}

	/**
	 * @param sup upper end
	 * @return new half closed interval ]-∞,sup]
	 */
	public static Interval toNegInf(double sup) {
		return new Interval(Double.NEGATIVE_INFINITY, sup, true, false);
	}

	/**
	 * @param inf lower end
	 * @return new half closed interval [inf,+∞[
	 */
	public static Interval toPosInf(double inf) {
		return new Interval(inf, Double.POSITIVE_INFINITY, false, true);
	}

	/**
	 * @param sup upper end
	 * @return new open interval ]-∞,sup[
	 */
	public static Interval openToNegInf(double sup) {
		return new Interval(Double.NEGATIVE_INFINITY, sup, true, true);
	}

	/**
	 * @param inf lower end.
	 * @return new open interval ]inf,+∞[
	 */
	public static Interval openToPosInf(double inf) {
		return new Interval(inf, Double.POSITIVE_INFINITY, true, true);
	}

	/**
	 * 
	 * @return The interval upper end.
	 */
	public double sup() {
		return sup;
	}

	/**
	 * 
	 * @return The interval lower end.
	 */
	public double inf() {
		return inf;
	}

	/**
	 * @return True if the interval is open at its lower end (]x,y] or ]x,y[).
	 */
	public boolean halfOpenInf() {
		return openInf;
	}

	/**
	 * @return True if the interval is open at its upper end ([x,y[ or ]x,y[).
	 */
	public boolean halfOpenSup() {
		return openSup;
	}

	/**
	 * Test if the interval contains a value. Does not use double equality in order
	 * to give a reliable result.
	 * 
	 * @param x the number to check
	 * @return {@code true} if x is contained in this interval
	 */
	public boolean contains(double x) {
		boolean cdsup = x < sup;
		boolean cdinf = x > inf;
		if ((!openSup) && (!cdsup))
			cdsup = x <= sup;
		if ((!openInf) && (!cdinf))
			cdinf = x >= inf;
		return cdsup && cdinf;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (openInf)
			sb.append(']');
		else
			sb.append('[');
		if (inf == Double.NEGATIVE_INFINITY)
			sb.append("-∞");
		else
			sb.append(inf);
		sb.append(',');
		if (sup == Double.POSITIVE_INFINITY)
			sb.append("+∞");
		else
			sb.append(sup);
		if (openSup)
			sb.append('[');
		else
			sb.append(']');
		return sb.toString();
	}

	/**
	 * Construct an interval from a String representation produced with
	 * {@code toString()}.
	 * <p>
	 * Valid Strings for input are:
	 * </p>
	 * <ul>
	 * <li>[x,y], [x,y[, ]x,y] or ]x,y[, where x and y are {@code double}
	 * numbers</li>
	 * <li>]-Inf,y], ]-Inf,y[, ]-∞,y] or ]-∞,y] where y is a {@code double}
	 * number</li>
	 * <li>[x,+Inf[, ]x,+Inf[, [x,+∞[ or ]x,+∞[ where x is a {@code double}
	 * number</li>
	 * <li>]-Inf,+Inf[, ]-Inf,+∞[, ]-∞,+Inf[ or ]-∞,+∞[</li>
	 * </ul>
	 * <p>
	 * Any other String will raise an Exception. Do not forget to trim blank space
	 * before calling this method.
	 * </p>
	 * 
	 * @param interval a String representing an interval
	 * @return an interval instance
	 */
	public static Interval valueOf(String interval) {
		boolean openInf = false;
		boolean openSup = false;
		double inf = 0;
		double sup = 0;
		String s = interval.trim();
		if (s.startsWith("["))
			openInf = false;
		else if (s.startsWith("]"))
			openInf = true;
		else
			throw new IllegalArgumentException("Interval must start with ']' or '[' - '" + s.charAt(0) + "' found instead");
		if (s.endsWith("]"))
			openSup = false;
		else if (s.endsWith("["))
			openSup = true;
		else
			throw new IllegalArgumentException(
					"Interval must end with ']' or '[' - '" + s.charAt(s.length() - 1) + "' found instead");
		s = s.substring(1, s.length() - 1);
		String[] ss = s.split(",");
		if (ss.length > 2)
			throw new IllegalArgumentException("Too many limits in interval string \"" + s + "\"");
		if (ss.length < 2)
			throw new IllegalArgumentException("Not enough limits in interval string \"" + s + "\"");
		if ((ss[0].contains("-∞")) || (ss[0].contains("-Inf")))
			inf = Double.NEGATIVE_INFINITY;
		else
			inf = Double.parseDouble(ss[0].trim());
		if ((ss[1].contains("+∞")) || (ss[1].contains("+Inf")))
			sup = Double.POSITIVE_INFINITY;
		else
			sup = Double.parseDouble(ss[1].trim());
		return new Interval(inf, sup, openInf, openSup);
	}
	
	/**
	 * @param i The other Interval
	 * @return true if the two intervals overlap
	 */
	public boolean overlaps(Interval i) {
		if (inf == i.sup) {
			if ((!openInf) && (!i.openSup))
				return true;
			else
				return false;
		} else if (sup == i.inf) {
			if ((!i.openInf) && (!openSup))
				return true;
			else
				return false;
		} else
			return contains(i.inf) || contains(i.sup);
	}

	@Override
	public int hashCode() {
		if (hash==0)
			hash = Objects.hash(inf,openInf,openSup,sup);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Interval))
			return false;
		Interval other = (Interval) obj;
		return Double.doubleToLongBits(inf) == Double.doubleToLongBits(other.inf) 
			&& openInf == other.openInf
			&& openSup == other.openSup 
			&& Double.doubleToLongBits(sup) == Double.doubleToLongBits(other.sup);
	}

	/**
	 * Test if two intervals are exactly contiguous to each other, eg [0,2[ and
	 * [2,3], [0,2] and ]2,3] will return true, but [0,2[ and ]2,3], [0,2] and
	 * [2,3], will return false.
	 * 
	 * @param i the interval to compare with this instance
	 * @return {@code true} if both intervals are contiguous
	 */
	public boolean contiguousTo(Interval i) {
		if (inf == i.sup)
			return openInf ^ i.openSup;
		if (sup == i.inf)
			return openSup ^ i.openInf;
		return false;
	}

	/**  */
	/**
	 * Compute the union of two overlapping intervals (null if non overlapping).
	 * 
	 * @param i The other interval.
	 * @return The union if the overlap of the two intervals (null if
	 *         non-overlapping).
	 */
	public Interval union(Interval i) {
		if (overlaps(i) || contiguousTo(i)) {
			double min = Math.min(inf, i.inf);
			boolean minOpen = false;
			if (inf == min)
				minOpen = openInf;
			else
				minOpen = i.openInf;
			if (inf == i.inf)
				minOpen = openInf && i.openInf;
			double max = Math.max(sup, i.sup);
			boolean maxOpen = false;
			if (sup == max)
				maxOpen = openSup;
			else
				maxOpen = i.openSup;
			if (sup == i.sup)
				maxOpen = openSup && i.openSup;
			return new Interval(min, max, minOpen, maxOpen);
		}
		return null;
	}

	/**
	 * @param i The other interval
	 * @return the intersection of two intervals (null if non-overlapping)
	 */
	public Interval intersection(Interval i) {
		if (overlaps(i)) {
			double min = Math.max(inf, i.inf);
			boolean minOpen = false;
			if (inf == min)
				minOpen = openInf;
			else
				minOpen = i.openInf;
			if (inf == i.inf)
				minOpen = !((!openInf) && (!i.openInf));
			double max = Math.min(sup, i.sup);
			boolean maxOpen = false;
			if (sup == max)
				maxOpen = openSup;
			else
				maxOpen = i.openSup;
			if (sup == i.sup)
				maxOpen = !((!openSup) && (!i.openSup));
			return new Interval(min, max, minOpen, maxOpen);
		}
		return null;
	}

}
