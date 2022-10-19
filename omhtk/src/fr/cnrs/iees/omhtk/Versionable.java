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
package fr.cnrs.iees.omhtk;

/**
 * An interface to put version numbers on objects.
 * <p>
 * Version numbers are dot separated integers in the form
 * {@code <major>.<minor>.<micro>}.
 *
 * <ol>
 * <li>Major: Upgraded when making incompatible API changes.</li>
 * <li>Minor: Upgraded when adding functionality in a backwards compatible
 * manner.</li>
 * <li>Micro: Upgraded when making backwards compatible bug fixes.</li>
 * </ol>
 * 
 * @see <a href ="https://semver.org/">Version numbering semantics</a>
 * 
 * @author Jacques Gignoux - 11/5/2018
 *
 */
public interface Versionable {

	/**
	 * Getter for the version string.
	 * <p>
	 * Version numbers are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * 
	 * @return The version string
	 */
	public String getVersion();

	/**
	 * Interface default implementation setter for version numbers.
	 * <p>
	 * Version numbers are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param major Part representing incompatible API changes.
	 * @param minor Part representing added functionality that is backwards
	 *              compatible.
	 * @param micro Part representing new backwards compatible bug fixes.
	 */
	public void setVersion(String major, String minor, String micro);

	/**
	 * Interface default implementation setter for version numbers.
	 * <p>
	 * Version numbers are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param major Part representing incompatible API changes.
	 * @param minor Part representing added functionality that is backwards
	 *              compatible.
	 * @param micro Part representing new backwards compatible bug fixes.
	 */
	default public void setVersion(int major, int minor, int micro) {
		setVersion(major, minor, micro);
	}

	/**
	 * Getter for the major part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @return Major (first) part of version string.
	 */
	default public String getMajor() {
		return getVersion().split("\\.")[0];
	}

	/**
	 * Getter for the minor part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @return minor (second) part of version string.
	 */
	default public String getMinor() {
		return getVersion().split("\\.")[1];
	}

	/**
	 * Getter for the micro part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @return micro (third) part of version string.
	 */
	default public String getMicro() {
		return getVersion().split("\\.")[2];
	}

	/**
	 * Setter for the major part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param major The major (first) part of the version string.
	 */
	default public void setMajor(String major) {
		String[] s = getVersion().split("\\.");
		setVersion(major, s[1], s[2]);
	}

	/**
	 * Setter for the minor part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param minor The minor (second) part of the version string.
	 */
	default public void setMinor(String minor) {
		String[] s = getVersion().split("\\.");
		setVersion(s[0], minor, s[2]);
	}

	/**
	 * Setter for the micro part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param micro The micro (thrid) part of the version string.
	 */
	default public void setMicro(String micro) {
		String[] s = getVersion().split("\\.");
		setVersion(s[0], s[1], micro);
	}

	/**
	 * Setter for the major part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param major The major (first) part of the version string.
	 */
	default public void setMajor(int major) {
		setMajor(Integer.toString(major));
	}

	/**
	 * Setter for the major part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param minor The minor (second) part of the version string.
	 */
	default public void setMinor(int minor) {
		setMinor(Integer.toString(minor));
	}

	/**
	 * Setter for the micro part of the version string.
	 * <p>
	 * Version strings are dot separated integers in the form
	 * {@code <major>.<minor>.<micro>}.
	 * 
	 * @param micro The micro (thrid) part of the version string.
	 */
	default public void setMicro(int micro) {
		setMicro(Integer.toString(micro));
	}

}
