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
 * <p>
 * An interface for objects that require initialisation (whatever this means)
 * after instantiation.
 * </p>
 * <p>
 * In big applications, the initialisation of objects is often a complex
 * procedure where many different classes must be instantiated in a precise
 * order, and sometimes with reciprocal dependencies that impose some more
 * initialisation after instantiation. This interface defines two methods that
 * help this process:
 * </p>
 * <ul>
 * <li>{@link Initialisable#initialise() initialise()} performs all the
 * operations required before any instance of this interface can be considered
 * 'ready'.</li>
 * <li>{@link Initialisable#initRank() initRank()} returns a rank that insures
 * that the initialisations are made in the proper order.</li>
 * </ul>
 * <p>
 * This interface is meant to be used with the
 * {@link au.edu.anu.rscs.aot.init.Initialiser Initialiser} class.
 * {@code Initialiser} is constructed with a list of {@code Initialisable}
 * instances. Then, a call to {@code Initialiser.initialise()} will call the
 * {@code initialise()} method of all {@code Initialisable} instances in turn,
 * in order of increasing {@code initRank()}.
 * </p>
 * 
 * 
 * @author Jacques Gignoux - 7 mai 2019
 *
 */
public interface Initialisable {

	/**
	 * Initialises this instance after construction. Often, classes require other
	 * classes to be initialized before they themselves can proceed. These
	 * associated classes will be initialized at the first attempt by a class to use
	 * them. Thus, initialization occurs in a cascading chain.
	 * 
	 * @throws Exception If initialization fails or one its associated classes in
	 *                   the cascading chain fails to initialize.
	 */
	public void initialise() throws Exception;

	/**
	 * This is used to decide in which order objects must be initialised. They will
	 * be initialised from the lowest to the highest priority. The use case is to
	 * set this as a class constant.
	 * 
	 * @return the priority level for the object to initialise.
	 */
	public int initRank();

//	@Override
//	public default int compareTo(Initialisable i) {
//		if (initRank() == i.initRank())
//			return 0;
//		if (initRank() > i.initRank())
//			return 1;
//		if (initRank() < i.initRank())
//			return -1;
//		return 0;
//	}

}
