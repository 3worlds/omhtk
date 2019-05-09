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
package au.edu.anu.rscs.aot.init;

/**
 * A class to store error messages from initialisation.
 * @author Jacques Gignoux - 7 mai 2019
 *
 */
public class InitialiseMessage {

	/** the error raised by the initialise() method */
	private Exception exc = null;
	/** the object which caused the error - NB it may be an archetype node (if there was an error in the archetype file) */
	private Object target = null;
	
	/**
	 * 
	 * @param check
	 * @param failed
	 * @param onNode
	 */
	public InitialiseMessage(Object item, Exception failed) {
		super();
		target = item;
		exc = failed;
	}

	public Exception getException() {
		return exc;
	}

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
