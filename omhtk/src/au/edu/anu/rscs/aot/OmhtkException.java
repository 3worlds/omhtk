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
package au.edu.anu.rscs.aot;

import fr.ens.biologie.generic.Textable;

/**
 * The {@link java.lang.Exception} class specific to this library.
 * 
 * @author shayne.flint@anu.edu.au
 * 
 */
//NB: this was previously AotException
//Policy is to make an exception at least for each library
//The general advice for exceptions is to throw early and catch late.
public class OmhtkException extends RuntimeException {

	private static final long serialVersionUID = -8889113181614003738L;

	/**
	 * Instantiate an exception on an object with a message
	 * @param item the item which caused the problem
	 * @param message the error message
	 */
	public OmhtkException(Textable item, String message) {
		super("[on " + item + "]\n[" + message + "]");
	}

	/**
	 * Instantiate an exception with a message
	 * @param message the error message
	 */
	public OmhtkException(String message) {
		super("[" + message + "]");
	}

	/**
	 * Exception wrapper.
	 * @param e the exception to wrap
	 */
	public OmhtkException(Exception e) {
		super(e);
	}

	/**
	 * Exception wrapper with additional information
	 * @param message the error message
	 * @param e the exception to wrap
	 */
	public OmhtkException(String message, Exception e) {
		super("[" + message + "]\n[original exception: " + e + "]");
		e.printStackTrace();
	}

}
