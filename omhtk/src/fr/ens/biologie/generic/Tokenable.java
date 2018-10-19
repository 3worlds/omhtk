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
 * <p>An interface for objects that can be made into tokens (toToken() method)
 * or loaded from a String using a valueOf() method</p>
 * <p>NOTE: classes implementing this interface <strong>must</strong> implement a 
 * {@code static public void valueOf(String)} method
 * returning an instance built from its argument. Don't forget that this method
 * can only be implemented in the class it is going to instantiate (ie no inheritance).</p>
 * @author Jacques Gignoux - 28-09-2018 
 *
 */
public interface Tokenable {
	
	/** Utility constants for generating blocks of saved data */
	// indices:										  0		 1		 2		 3
	public static char[] START_BLOCK_DELIMITER = 	{'{',	'[',	'(',	'<'};
	public static char[] END_BLOCK_DELIMITER = 		{'}',	']',	')',	'>'};
	public static char[] ITEM_DELIMITER = 			{',',	';',	':',	' '};
	
	/**
	 * 
	 * @return a String version of all the data contained in this object
	 */
	public String toToken();
		
}
