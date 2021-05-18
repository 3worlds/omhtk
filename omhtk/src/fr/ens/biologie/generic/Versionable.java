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
 * An interface to put version numbers on objects. Assumes a major.minor.micro format.
 * Methods are self-explained getters and setters.
 * 
 * @author Jacques Gignoux - 11/5/2018
 *
 */
public interface Versionable {
	
	public String getVersion();
	
	public void setVersion(String major, String minor, String micro);
	
	default public void setVersion(int major, int minor, int micro) {
		setVersion(major,minor,micro);
	}
	
	default public String getMajor() {
		return getVersion().split("\\.")[0];
	}
	
	default public String getMinor() {
		return getVersion().split("\\.")[1];
	}

	default public String getMicro() {
		return getVersion().split("\\.")[2];
	}

	default public void setMajor(String major) {
		String[] s = getVersion().split("\\.");
		setVersion(major,s[1],s[2]);
	}
	
	default public void setMinor(String minor) {
		String[] s = getVersion().split("\\.");
		setVersion(s[0],minor,s[2]);
	}

	default public void setMicro(String micro) {
		String[] s = getVersion().split("\\.");
		setVersion(s[0],s[1],micro);
	}

	default public void setMajor(int major) {
		setMajor(Integer.toString(major));
	}

	default public void setMinor(int minor) {
		setMinor(Integer.toString(minor));
	}

	default public void setMicro(int micro) {
		setMicro(Integer.toString(micro));
	}

}
