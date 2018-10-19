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
 * For any object that has a name
 * @author J. Gignoux - 13 févr. 2017
 *
 */
public interface Named {
		
	public final static String NAME  = "name";
	
	/** (optimised) getter to name 
	 * @return the name
	 * */
	public String getName();
	
	/**
	 * setter to name
	 * @param name the new name
	 * @return this instance for agile programming
	 */
	public Named setName(String name);
		
	/**
	 * checks if this instance has the same name as the argument
	 * @param item another Named instance
	 * @return true if both have the same name
	 */
	public boolean sameName(Named item);
	
	/**
	 * checks the name has a given value
	 * @param name
	 * @return
	 */
	public boolean hasName(String name);
	
}
