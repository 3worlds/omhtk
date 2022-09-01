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
package fr.ens.biologie.generic;

/**
 * <p>An interface for relatively complex objects that can return information with various 
 * levels of detail.</p>
 * <p>This interface should <strong>not</strong> be used to save objects as text. For
 * this task, the {@link SaveableAsText} interface is better adapted.</p>
 * <p>These methods complement the {@code Object.toString()} method that is used in eclipse
 * for debugging code.</p>
 * 
 * @author gignoux - 25 août 2017
 *
 */
public interface Textable {
	
	/**
	 * Displays the object as simply as possible, showing its unicity as an instance.
	 * Typically, it should show the object address + some indication of the object
	 * content. Or if the object has a unique ID (e.g. graph elements), it should
	 * use it.
	 * 
	 * @return a String uniquely characterising this instance
	 */
	public default String toUniqueString() {
		// Typically, this method shoud return Object.toString(), which returns the
		// object reference. But this cannot be made the default behaviour in
		// an interface because super is not available in interfaces
		throw new UnsupportedOperationException("Unimplemented method: toUniqueString()");
	}

	/**
	 * <p>Displays the object as simply as possible, with no guarantee that another 
	 * object will not return the same string. </p>
	 * <p>The returned description must fit on one single, short (~20 character), line
	 * of text.</p>
	 * @return a short String describing this instance
	 */
	public default String toShortString() {
		return toString();
	}

	/**
	 * <p>Displays the object with all - or at least a lot of - the detailed of its content, for screen or console
	 * display purpose.</p>
	 * <p>The returned description should not contain line breaks.</p>
	 * 
	 * @return a long String describing this instance.
	 */
//	public String toLongString(); // bad name
	public default String toDetailedString() {
		return toUniqueString()+" "+toString();
	}
	
	/**
	 * This is the inherited method from {@code Object}.
	 * Keep it simple as it is used by the debugger to show object content
	 * Suggestion is to make it return one of the other methods depending
	 * on what debugging level is required (e.g. toUniqueString() when instance
	 * identity is important, toDetailedString() when everything is important,
	 * toShortString() when not much is important).
	 * 
	 * @return
	 */
//	public String toString();
	
}
