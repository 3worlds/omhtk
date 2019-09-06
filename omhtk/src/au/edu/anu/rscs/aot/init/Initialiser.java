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

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import fr.ens.biologie.generic.Initialisable;

/**
 * A new version of Shayne's initialiser - much, much simpler.
 * @author Jacques Gignoux - 7 mai 2019
 *
 */
public class Initialiser {
	
	private SortedMap<Integer,List<Initialisable>> toInit = new TreeMap<>();
	private List<InitialiseMessage> initFailList = new LinkedList<>();

	/**
	 * Constructor takes a list of Initialisable objects
	 * @param initList the list of objects ot initialise
	 */
	public Initialiser(Iterable<Initialisable> initList) {
		super();
		for (Initialisable init:initList) {
			int priority = init.initRank();
			if (!toInit.containsKey(priority))
				toInit.put(priority, new LinkedList<>());
			// the sorted map sorts the key integers in increasing order
			toInit.get(priority).add(init);
		}
	}
	
	/**
	 * Initialises all objects passed to the constructor
	 * following their priority ranking, from the lowest to the highest priority
	 */
	public void initialise() {
		// the SortedMap iterator returns its content in ascending order
		for (int priority:toInit.keySet())
			for (Initialisable init:toInit.get(priority))
				try {
					init.initialise();
				}
				catch (Exception e) {
					initFailList.add(new InitialiseMessage(init,e));
				}
	}
	
	/**
	 * Returns the problems which occured during the initialisation process.
	 * @return null if no error, the error list otherwise
	 */
	public Iterable<InitialiseMessage> errorList() {
		if (initFailList.isEmpty())
			return null;
		else 
			return initFailList;
	}
	
}
