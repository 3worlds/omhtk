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
package au.edu.anu.omhtk;

/**
 * A list of supported natural languages.
 * 
 * @author Ian Davies - 5 April 2021
 *
 */
public class Language {
	private Language() {
	};

	private static final String lang = System.getProperty("user.language");

	/**
	 * Opening single quote.
	 */
	public static String oq = "'";
	/**
	 * Closing single quote.
	 */
	public static String cq = "'";
	static {
		if (French()) {
			oq = "«";
			cq = "»";
		} else if (Japanese()) {
			oq = "「";
			cq = "」";
		}
	}

	/**
	 * @return true if the system's language is French, false otherwise.
	 */
	public static boolean French() {
		return lang.equals("fr");
	}

	/**
	 * @return true if the system's language is Japanese, false otherwise.
	 */
	public static boolean Japanese() {
		return lang.equals("jp");
	}

	/**
	 * @return true if the system's language is Chinese (Mandarin), false otherwise.
	 */
	public static boolean Chinese() {
		return lang.equals("cn");
	}

}
