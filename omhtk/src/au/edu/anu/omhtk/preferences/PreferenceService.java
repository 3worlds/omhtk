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
package au.edu.anu.omhtk.preferences;

/**
 * This static class is a singleton container for any implementation of
 * {@link ArrayPreferences}. As a static class, it is globally accessible.
 * 
 * @author Ian Davies - Dec 11, 2018
 */

public class PreferenceService {
	private static ArrayPreferences impl;

	private PreferenceService() {
	};

	/**
	 * Setter for the implementation of this static class. Any implementation
	 * already existing is flushed.
	 * 
	 * @param implementation The implementation.
	 */
	public static void setImplementation(ArrayPreferences implementation) {

		if (PreferenceService.impl != null)
			PreferenceService.impl.flush();
		PreferenceService.impl = implementation;
	}

	/**
	 * Access to the implementation of ArrayPreferences.
	 * 
	 * @return preferences implementation (can be null).
	 */
	public static ArrayPreferences getImplementation() {
		return impl;

	}

}
