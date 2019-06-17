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
package fr.ens.biologie.generic;

/**
 * 
 * @author Jacques Gignoux - 7 mai 2019
 *
 */
public interface Initialisable extends Comparable<Initialisable> {

	/**
	 * An object which can be initialise must have this method, which will be called
	 * by initialisers.
	 */
	public void initialise();
	
	/**
	 * This is used to decide in which order objects must be initialised. They will be
	 * initialised from the lowest to the highest priority. The use case is to set this
	 * as a class constant.
	 * @return the priority level for the object to initialise.
	 */
	public int initRank();
	
	@Override
	public default int compareTo(Initialisable i) {
		if (initRank() == i.initRank())
			return 0;
		if (initRank() > i.initRank())
			return 1;
		if (initRank() < i.initRank())
			return -1;
		return 0;
	}

}
