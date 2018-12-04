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

/**
 * 
 * @author Shayne Flint - 4/4/2012
 *
 */
public class NumberRange {

	public static String range(int min, int max) {
		String result = "";
		if (min == Integer.MIN_VALUE)
			result = "MinInteger";
		else 
			result = result + min;
		result = result + "..";
		if (max == Integer.MAX_VALUE)
			result = result + "MaxInteger";
		else 
			result = result + max;	
		return result;
	}


	public static String range(long min, long max) {
		String result = "";
		if (min == Long.MIN_VALUE)
			result = "MinLong";
		else 
			result = result + min;
		result = result + "..";
		if (max == Long.MAX_VALUE)
			result = result + "MaxLong";
		else 
			result = result + max;	
		return result;
	}

	public static String range(float min, float max) {
		String result = "";
		if (min == Float.MIN_VALUE)
			result = "MinFloat";
		else 
			result = result + min;
		result = result + "..";
		if (max == Float.MAX_VALUE)
			result = result + "MaxFloat";
		else 
			result = result + max;	
		return result;
	}

	public static String range(double min, double max) {
		String result = "";
		if (min == Double.MIN_VALUE)
			result = "MinDouble";
		else 
			result = result + min;
		result = result + "..";
		if (max == Double.MAX_VALUE)
			result = result + "MaxDouble";
		else 
			result = result + max;	
		return result;
	}


}
