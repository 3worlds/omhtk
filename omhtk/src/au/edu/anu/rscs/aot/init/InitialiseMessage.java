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
package au.edu.anu.rscs.aot.init;

import fr.ens.biologie.generic.Initialisable;

/**
 * <p>A class to store error messages that occurred during a late (=after instantiation) initialisation.
 * Works in conjunction with {@link Initialiser}.</p>
 * @author Jacques Gignoux - 7 mai 2019
 *
 */
public class InitialiseMessage {
	
	private Exception exc = null;
	private Object target = null;
	
	/**
	 * Constructor
	 * 
	 * @param item the object on which initialisation failed
	 * @param failed the error raised by the initialisation method call
	 */
	public InitialiseMessage(Object item, Exception failed) {
		super();
		target = item;
		exc = failed;
	}

	/** The error raised by the initialisation method call */
	public Exception getException() {
		return exc;
	}

	/** The object which caused the error. Usually, an {@link Initialisable} instance.*/
	public Object getTarget() {
		return target;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Initialisation failed for object:\n\t")
			.append(target.toString())
			.append("\n--with Error:\n\t")
			.append(exc.toString());
		return sb.toString();
	}
}
