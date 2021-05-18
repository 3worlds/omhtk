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
 * An interface for objects that can be write protected / write enabled.
 * 
 * It is similar to {@linkplain Sealable} except the seal is reversible
 * 
 *  @author J. Gignoux - 13 févr. 2017
 *
 */
public interface WriteProtectable {
	
	/**
	 * Whether this instance is writeable.
	 * @return {@code true} if data is write-protected, {@code false} if writing is permitted
	 */
	public boolean isReadOnly();
	
	/**
	 * Enables data writing in this instance.
	 * @return this instance for agile programming
	 */
	public WriteProtectable writeEnable();
	
	/**
	 * Disables data writing in this instance.
	 * @return this instance for agile programming
	 */
	public WriteProtectable writeDisable();

}
