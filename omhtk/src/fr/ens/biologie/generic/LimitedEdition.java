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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package fr.ens.biologie.generic;

/**
 * <p>An interface that returns a limited set of instances, associated to unique ids. It can be seen
 * as a 'id-relative' {@link Singleton}, which returns a unique instance of a class for 
 * a given id (i.e. for a single id it is guaranteed that the returned instance is always the same).</p>
 * 
 * 
 * 
 * @author Jacques Gignoux - 7 oct. 2019
 *
 * @param <T> the type of the returned instances
 * 
 * @see {@link Singleton}
 * @see {@link Factory} 
 */
public interface LimitedEdition<T> {
	
	/**
	 * <p>Returns the unique instance of {@code T} matching the identifier {@code id}.</p>
	 * <p>Implementations should work as follows:</p> 
	 * <ol>
	 * <li>if the unique id is known, return the matching instance;</li>
	 * <li>else if unknown, make a new instance, associate it to this id, and return it.</li>
	 * </ol>
	 * 
	 * @param id the unique identifier
	 * @return the matching {@code T} instance
	 */
	public T getInstance(int id);

}
