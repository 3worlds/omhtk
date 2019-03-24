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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/

package au.edu.anu.rscs.aot.util;

import java.io.File;
import java.net.URL;

import au.edu.anu.omhtk.jars.Jars;
import au.edu.anu.rscs.aot.OmhtkException;
// TODO not sure about all this!! Needs testing, documenting  and a home
/**
 * Author Ian Davies
 *
 * Date 19 Dec. 2018
 */
// Copied from old code by S. Flint.
public class Resources {
	/**
	 * @param name
	 * @return
	 */
	public static URL getURL(String name) {
		URL result = ClassLoader.getSystemResource(name);
		if (result==null)
			throw new OmhtkException("Resource not found by ClassLoader: "+name);
		if (new File(result.getFile()).isDirectory()) {
			throw new OmhtkException("Directory resources are not permitted '" + name + "'");				
		}
		return result;
	}

	public static URL getURL(String name, String packageName) {
		String resourceName = packageName.replace('.', Jars.separatorChar) + Jars.separator + name;
		return getURL(resourceName);
	}

	public static URL getURL(String name, Class<?> associatedWithClass) {
		return associatedWithClass.getResource(name);
	}

	public static File getFile(String name) {
		URL url = getURL(name);
		if (url != null)
			return new File(url.getFile());
		else
			return null;
	}

	public static File getFile(String name, Class<?> associatedWithClass) {
		URL url = getURL(name, associatedWithClass);
		if (url != null) {
			return new File(url.getFile());
		}
		else
			return null;
	}

	/**
	 * @param name file name of resource
	 * @param packageName package location
	 * @return fully qualified File obtained by the ClassLoader
	 */
	public static File getFile(String name, String packageName) {
		URL url = getURL(name, packageName);
		if (url != null)
			return new File(url.getFile());
		else
			return null;
	}
	
	/**
	 * @param name package relative resource file (dot separated)
	 * @return fully qualified File obtained by the ClassLoader
	 */
	public static File getPackagedFile(String name) {
		int idx = name.lastIndexOf('.');
		String fname = new File( name.substring(0, idx).replace(".","/")
		        + name.substring(idx)).getName();
		String packageName = name.replace("."+fname, "");
		return getFile(fname,packageName);
		
	}
	

}
