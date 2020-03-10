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
 * <p>An interface for objects that can be reset to an initial state after having been brought
 * into a different state.
 * It assumes the object has a life cycle of the type "<em>initial state</em> -&gt; <em>running</em>
 *  -&gt; <em>final state</em>" </p>
 *  
 * @author Jacques Gignoux - 2 juin 2012<br/><br/>
 *
 */
public interface Resettable {
	
	/**
	 * Processes whatever has to be done when entering <em>initial state</em> 
	 * (does nothing by default).
	 */
	public default void preProcess() {}
	
	/**
	 * Processes whatever has to be done when entering <em>final state</em> 
	 * (does nothing by default).
	 */
	public default void postProcess() {} 
	
	/**
	 * Revert from <em>final state</em> to <em>initial state</em>
	 * by calling {@code postProcess()}, then {@code preProcess()}.
	 * Should never be overriden.
	 */
	public default void reset() {
		postProcess();
		preProcess();
	}

}
