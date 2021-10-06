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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An interface for factories for objects of class {@code T}. 
 * A factory is an object able to return instances of a given class.
 * Here, every call to newInstance() returns
 * a new object of class {@code T}. cf. {@link Singleton} for unique instances. 
 * 
 * @author Jacques Gignoux - 31 mai 2019
 *
 * @param <T> the type of the returned instance

 * @see {@link Singleton}
 * @see {@link LimitedEdition} 
 */
public interface Factory<T> {
	
	/**
	 * Creates and returns a new instance of class {@code T}.
	 * @return the new {@code T} instance
	 */
	public T newInstance();
	
	/**
	 * Creates and returns a collection of new instances of class {@code T}. The default behaviour
	 * is to return a single instance in a collection.
	 * @return the collection of new {@code T} instances
	 */
	public default Collection<T> newInstances() {
		T instance = newInstance();
		List<T> list = new ArrayList<>();
		list.add(instance);
		return list;
	}

}
