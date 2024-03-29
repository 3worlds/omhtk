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
package au.edu.anu.omhtk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import au.edu.anu.omhtk.jars.Jars;
import fr.cnrs.iees.omhtk.utils.Logging;

/**
 * Utilities to locate resources (usually text files) in the project class
 * hierarchy.
 * 
 * @author Ian Davies - 19 Dec. 2018<br/>
 *         Copied from old code by S. Flint.<br/>
 *         refactored by J. Gignoux 22/5/2019
 */
// Tested OK with version 0.1.1 on 22/5/2019 - works with jars too.
public class Resources {

	private static Logger log = Logging.getLogger(Resources.class);

	/**
	 * Creates a URL for the required resource, based on the SystemClassLoader class
	 * path, i.e. the class loader running the current application.
	 * 
	 * @param name the resource to look for
	 * @return the url, <strong>null</strong> if not found
	 */
	public static URL getURL(String name) {
		URL result = ClassLoader.getSystemResource(name);
//		if (result==null)
//			throw new something("Resource not found by ClassLoader: "+name);
//		if (new File(result.getFile()).isDirectory()) {
//			throw new something("Directory resources are not permitted '" + name + "'");				
//		}
		return result;
	}

	/**
	 * Creates a URL based on the required resource name and the associated package
	 * name
	 * 
	 * @param name        the resource to look for (usually, a file)
	 * @param packageName the package in which the resource is to be found
	 * @return the URL of the resource, <strong>null</strong> if not found
	 */
	public static URL getURL(String name, String packageName) {
		String resourceName = packageName.replace('.', Jars.separatorChar) + Jars.separator + name;
		return getURL(resourceName);
	}

	/**
	 * Creates a URL based on the required resource name and the associated class
	 * package name.
	 * 
	 * @param name                the resource to look for (usually, a file)
	 * @param associatedWithClass the class in which package the resource is to be
	 *                            found
	 * @return the URL of the resource, <strong>null</strong> if not found
	 */
	public static URL getURL(String name, Class<?> associatedWithClass) {
		return associatedWithClass.getResource(name);
	}

	/**
	 * Returns a file matching the required resource by searching in the application
	 * class path. <strong>WARNING</strong>: this will not work for resources in
	 * jars, that cant be Files
	 * 
	 * @param name the resource to look for
	 * @return the File matching the resource, <strong>null</strong> if not found
	 */
	public static File getFile(String name) {
		URL url = getURL(name);
		if (url != null)
			return new File(url.getFile());
		else
			return null;
	}

	/**
	 * Returns a file matching the required resource by searching in the package of
	 * the <em>Class</em> argument. <strong>WARNING</strong>: this will not work for
	 * resources in jars, that cant be Files
	 * 
	 * @param name                the resource to look for
	 * @param associatedWithClass the class in which package the resource is to be
	 *                            found
	 * @return the File matching the resource, <strong>null</strong> if not found
	 */
	public static File getFile(String name, Class<?> associatedWithClass) {
		URL url = getURL(name, associatedWithClass);
		if (url != null) {
			return new File(url.getFile());
		} else
			return null;
	}

	/**
	 * Returns a file matching the required resource by searching in the specified
	 * package <strong>WARNING</strong>: this will not work for resources in jars,
	 * that cant be Files
	 * 
	 * @param name        the resource to look for
	 * @param packageName the package in which the resource is to be found
	 * @return the File matching the resource, <strong>null</strong> if not found
	 */
	public static File getFile(String name, String packageName) {
		URL url = getURL(name, packageName);
		if (url != null)
			return new File(url.getFile());
		else
			return null;
	}

	private static List<String> readText(BufferedReader reader, String name) {
		List<String> text = new LinkedList<String>();
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
//				byte[] bytes = line.getBytes(StandardCharsets.UTF_8 );
//				line = new String(bytes,StandardCharsets.UTF_8);
				text.add(line);
			}
		} catch (IOException e) {
			log.severe("Error reading resource " + name);
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * This will get a text resource either from a plain file or from a jar
	 * 
	 * @param name the resource to look for
	 * @return the text as a list of Strings, one per line
	 */
	public static List<String> getTextResource(String name) {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		if (in == null)
			throw new NullPointerException("Resource '" + name + "' not found");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		return readText(reader, name);
	}

	/**
	 * This will get a text resource either from a plain file or from a jar. The
	 * resource will be searched for in the same package as the <em>Class</em>
	 * parameter.
	 * 
	 * @param name                the resource to look for
	 * @param associatedWithClass the class in which package the resource is to be
	 *                            found
	 * @return the text as a list of Strings, one per line
	 */
	public static List<String> getTextResource(String name, Class<?> associatedWithClass) {
		InputStream in = associatedWithClass.getResourceAsStream(name);
		if (in == null)
			throw new NullPointerException("Resource '" + name + "' not found");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		return readText(reader, name);
	}

	/**
	 * @param name package relative resource file (dot separated)
	 * @return fully qualified File obtained by the ClassLoader
	 */
	public static File getPackagedFile(String name) {
		int idx = name.lastIndexOf('.');
		String fname = new File(name.substring(0, idx).replace(".", "/") + name.substring(idx)).getName();
		String packageName = name.replace("." + fname, "");
		return getFile(fname, packageName);
	}

	/**
	 * Convert a dot separated string to a forward slash ('/') separated string(?).
	 * NB this is no OS independent!
	 * 
	 * @return Forward slash separated string.
	 * @param name a dot separated package name.
	 * 
	 */
	public static String getPackagedFileName(String name) {
		int idx = name.lastIndexOf('.');
		String fname = name.substring(0, idx).replace(".", "/") + name.substring(idx);
		return fname;
	}

}
