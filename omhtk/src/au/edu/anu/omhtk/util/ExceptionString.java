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
 * Static method to handle exceptions. Useful when you want to redirect Exception error messages
 * to your own output.
 * 
 * @author Shayne Flint - before 27/2/2012
 *
 */
// NOT TESTED
public class ExceptionString {

	/**
	 * Get an Exception stack trace into a String (instead of sending it to the console as in
	 * {@link java.lang.Exception#printStackTrace() Exception.printStackTrace()}).
	 * 
	 * @param e the exception to gobble
	 * @return the stack trace of the exception
	 */
	public static String exceptionString(Exception e) {
		String result = e.toString();
		for (StackTraceElement ste : e.getStackTrace()) {
			result = result + "\n  at " + ste.toString();
		}
		return result;
	}
}
