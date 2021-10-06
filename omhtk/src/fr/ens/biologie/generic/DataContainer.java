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
 * <p>An interface for objects that contain data.</p>
 * 
 * <p>Objects implementing this interface are assumed to contain data of an identical elementary
 * type ({@code Object} or primitive types).
 * 
 * The interface only deals with the block data treatments (clone, clear, fill...).</p>
 * <p>All {@code fillWith(...)} method have a default implementation that does nothing.</p>
 *  
 * @author J. Gignoux - 13 févr. 2017
 *
 */
public interface DataContainer extends Cloneable {

	/** creates a new instance of this container with the same content */
//	public DataContainer clone();
	
	/** sets the content of this container to zero, whatever this means for the content type. */
	public DataContainer clear();
	
	/** fills this container with a single {@code Object} value */
	public default DataContainer fillWith(Object value) {
		// do nothing
		return this;
	}

	/** fills this container with a single {@code int} value */
	public default DataContainer fillWith(int value) {
		// do nothing
		return this;
	}	

	/** fills this container with a single {@code long} value */
	public default DataContainer fillWith(long value) {
		// do nothing
		return this;
	}
	
	/** fills this container with a single {@code short} value */
	public default DataContainer fillWith(short value) {
		// do nothing
		return this;
	}

	/** fills this container with a single {@code byte} value */
	public default DataContainer fillWith(byte value) {
		// do nothing
		return this;
	}

	/** fills this container with a single {@code boolean} value */
	public default DataContainer fillWith(boolean value) {
		// do nothing
		return this;
	}

	/** fills this container with a single {@code double} value */
	public default DataContainer fillWith(double value) {
		// do nothing
		return this;
	}

	/** fills this container with a single {@code float} value */
	public default DataContainer fillWith(float value) {
		// do nothing
		return this;
	}

	/** fills this container with a single {@code char} value */
	public default DataContainer fillWith(char value) {
		// do nothing
		return this;
	}

}
