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
 * <p>This interface is for objects that can be modified for a certain time, and then be sealed so that no
 * future change is permitted.</p>
 * <p>The typical use case is for objects that cannot be used until some late initialisation (i.e.
 * after instantiation) is performed. It can be quite powerful when used in conjunction with the
 * {@link Initialisable} interface.</p> 
 * 
 *  @author J. Gignoux - 13 févr. 2017
 *
 */
public interface Sealable {
	
	/**
	 * Seals this instance so that no further change in content or structure (depends on implementation)
	 * can be made until instance disposal. Implementations should make sure that further calls to this
	 * method after the first one have no effect.
	 * @return this instance for agile programming
	 */
	public Sealable seal();
	
	/**
	 * Whether this instance is sealed or not.
	 * @return true if this instance is sealed (=protected from further changes, whatever this means)
	 */
	public boolean isSealed();

}
