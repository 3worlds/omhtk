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
package au.edu.anu.rscs.aot.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import au.edu.anu.omhtk.jars.Jars;
import au.edu.anu.rscs.aot.OmhtkException;
import au.edu.anu.rscs.aot.collections.DynamicList;
import fr.ens.biologie.generic.utils.Logging;


/**
 * Utilities to handle java specific files, e.g. jar files.
 * @author Shayne Flint - 2012 <br/>
 * 		refactored by Jacques Gignoux - 12 août 2019
 *
 */
// Tested ok with version 0.1.9, except getting classes from jars.
public class JavaUtils {
	
	private static Logger log = Logging.getLogger(JavaUtils.class);

//	/**
//	 * @param className The String className
//	 * @return a Java class from a String className
//	 * @throws Exception
//	 */
//	public static Class<?> getClass(String className) throws Exception {
//		return Class.forName(className);
//	}

//	@SuppressWarnings("unused")
//	public static Boolean classExists(String className) {
//		try {
//			Class<?> ItemClass = getClass(className);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}

	// Based on http://forum.java.sun.com/thread.jspa?threadID=341935&tstart=30
	//

	/**
	 * Get all classes contained in a package. Handles local classes, files, and jar entries transparently.
	 * Implementation from a java.sun.com forum, now lost since oracle overtook sun.
	 * 
	 * @param packageName the package to search (dot-delimited hierarchical String)
	 * @return a list of {@link java.lang.Class Class} objects.
	 */
	public static DynamicList<Class<?>> getClassesInPackage(String packageName) {
		return getClassesInPackage(packageName, false);
	}

	/**
	 * Get all classes contained in a package and all its sub-packages. Handles 
	 * local classes, files, and jar entries transparently.
	 * Implementation from a java.sun.com forum,
	 * now lost since oracle overtook sun.
	 * 
	 * @param packageName the package to search (dot-delimited hierarchical String)
	 * @return a list of {@link java.lang.Class Class} objects.
	 */
	public static DynamicList<Class<?>> getClassesInPackageTree(String packageName) {
		return getClassesInPackage(packageName, true);
	}

	// Add classes from names package. Search all elements of the class path - disk and Jars
	//
	private static DynamicList<Class<?>> getClassesInPackage(String packageName, boolean doTree) {
		DynamicList<Class<?>> classList = new DynamicList<Class<?>>();

//		ClassLoader cl = ClassLoader.getSystemClassLoader();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
// 		this no longer works - was changed in java 9
//		URL[] urls = ((URLClassLoader) cl).getURLs();
        assert cl != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources;
		try {
			resources = cl.getResources(path);
	        while (resources.hasMoreElements()) {
	            URL url = resources.nextElement();
				if (url.getFile().endsWith(".jar")) {
					File jarFile=null;
					try {
						// under windows at least, %20 needs to be replaced by " "
						jarFile = new File(URLDecoder.decode(url.getFile(),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					getClassesFromJar(packageName, jarFile, classList, doTree);
				} else {
					File dir = new File(url.getFile());
					getClassesFromDisk(packageName, dir, classList, doTree);				
				}
	        }
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return classList;
	}

	// Collect classes from a disk-based class path
	// filtered by package name
	//
	private static void getClassesFromDisk(String packageName, File packageDir, DynamicList<Class<?>> classList, boolean doTree)  {
//		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (packageDir.isDirectory()) {
			for (String name : packageDir.list()) {
				File subDirectory = FileUtilities.makeFile(packageDir.toString(), name);
				if (name.endsWith(".class")) {
					String className = packageName + '.' + name.substring(0, name.length() - 6);
					//				System.out.println(className);
					try {
						Class<?> theClass = classLoader.loadClass(className);
						classList.addUnique(theClass);
					} catch (Throwable e) {
						log.warning("JavaUtils.getClasses: '" + className + "' " + e);
					}
				} else if (subDirectory.isDirectory() && doTree) {
					//				System.out.println(packageName + ", " + directory + ", " + subDirectory + ", " + name);
					getClassesFromDisk(packageName + "." + name, subDirectory, classList, doTree);
				}
			}					
		}
	}

	
	// Collect classes from a Jar
	// filtered by package name
	//
	private static void getClassesFromJar(String packageName, File jar, DynamicList<Class<?>> classList, boolean doTree)  {
		try {
			JarFile jarFile = new JarFile(jar);
			Enumeration<JarEntry> jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry entry = jarEntries.nextElement();
				String name = entry.getName();
				if (name.startsWith(packageName))
					if (doTree)
						getClassFromJar(classList, entry);
					else if (name.substring(packageName.length()).indexOf(Jars.separator) > 0)
						getClassFromJar(classList, entry);
			}
			jarFile.close();
		} catch (IOException e) {
			throw new OmhtkException("JavaUtils.getClassFromJar: cannot open jar '" + jar + "'", e);
		}
	}

	
	private static void getClassFromJar(DynamicList<Class<?>> classList, JarEntry entry) {
		System.out.println("Adding " + entry);
//		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			Class<?> theClass = classLoader.loadClass(entry.getName());
			classList.addUnique(theClass);
		} catch (Throwable e) {
			log.warning("JavaUtils.getClasses: '" + entry.getName() + "' " + e);
		}
	}

//	/**
//	 * get  list of all classes on the class path. 
//	 * doesnt work because some classes dont load simply. forget this. Bad idea.
//	 * @return
//	 */
//	@Deprecated
//	public static List<Class<?>> getClassList() {
//		return getClassList(getClassNameList());
//	}

//	/**
//	 * doesnt work because some classes dont load simply. forget this. Bad idea.
//	 * 
//	 * @param classNameList
//	 * @return
//	 */
//	@Deprecated
//	public static List<Class<?>> getClassList(List<String> classNameList) {
//		List<Class<?>> result = new LinkedList<Class<?>>();
//		for (String className : classNameList)
//			try {
//				result.add(Class.forName(className));
//			} catch (Exception e) {
//				System.out.println("JavaUtils.getClassList: '" + className + "', "  + e);
//				//e.printStackTrace();
//				//throw new OmhtkException("JavaUtils.getClassList(): class not found '" + className + "'", e);
//			}
//		return result;
//	}

	/**
	 * Returns all the classes found on the class path. 
	 * Use with caution, returns a huge mess.
	 * @return a list of class names
	 */
	public static List<String> getClassNameList() {
		List<String> result = new LinkedList<String>();
		String classPath = System.getProperty("java.class.path");
		String[] paths   = classPath.split(File.pathSeparator);
//		for debugging only
//		for (String p : paths) {
//			System.out.println("PATH: " + p);
//		}
		for (String path : paths) {
			File file = new File(path);
			result.addAll(getClasses(file, file));
		}
		return result;
	}

	// helper for getClassNameList()
	private static List<String> getClasses(File root, File file) {
		List<String> result = new LinkedList<String>();
		if (file.isDirectory()) {
			for (File f : file.listFiles())
				result.addAll(getClasses(root, f));
		} else if (file.getPath().endsWith(".class")) {
			result.add(className(root, file));
		} else if (file.getPath().endsWith(".jar")) {
			JarFile jar = null;
			try {
				jar = new JarFile(file);
			} catch (Exception e) {
			}
			if (jar != null) {
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					int classIdx = name.lastIndexOf(".class");
					if (classIdx > 0) {
						result.add(name.substring(0, classIdx).replace("/", "."));
					}
				}
			}
		}
		return result;
	}

	private static String className(File root, File file) {
		String name = file.getPath();
		String rootName = root.getPath() + File.separator;
		if (name.startsWith(rootName)) {
			name = name.substring(rootName.length());
		}
		if (name.endsWith(".class"))
			name = name.substring(0, name.length()-".class".length());
		return name.replace(File.separator,  ".");
	}

	/**
	 * Get the file matching a class. Uses the application {@link java.lang.ClassLoader ClassLoader}
	 * to get the information.
	 * @param theClass the class to look for
	 * @return the file handle to the argument {@code .class} file
	 */
	public static File fileForClass(Class<?> theClass) {
//		String resourceName = theClass.getName().replaceAll("\\.", "/") + ".class";
		//		System.out.println(resourceName);
		URL theURL = theClass.getResource(theClass.getSimpleName() + ".class");
		//		System.out.println(theClass);
		return new File(theURL.getPath());
	}
	
	/**
	 * Get the file matching a class name. Uses the application {@link java.lang.ClassLoader ClassLoader}
	 * to get the information.
	 * @param className the class name to look for
	 * @return the file handle to the argument {@code .class} file
	 */
	public static File fileForClass(String className) {
		URL theURL = Resources.getURL(className.replaceAll("\\.", Jars.separator) + ".class");
		return new File(theURL.getPath());
	}

	/**
	 * Pauses the current thread for a given amount of time.
	 * @param ms time in milliseconds
	 */
	public static void sleep(long ms) {
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
