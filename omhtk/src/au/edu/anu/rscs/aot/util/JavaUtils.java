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


/**
 * Utilities to handle java specific files, e.g. jar files.
 * @author Shayne Flint - long ago 
 * 		refactored Jacques Gignoux - 12 août 2019
 *
 */
public class JavaUtils {
	
	private static Logger log = Logger.getLogger(JavaUtils.class.getName());

	public static Class<?> getClass(String className) throws Exception {
		return Class.forName(className);
	}

	@SuppressWarnings("unused")
	public static Boolean classExists(String className) {
		try {
			Class<?> ItemClass = getClass(className);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Based on http://forum.java.sun.com/thread.jspa?threadID=341935&tstart=30
	//

	public static DynamicList<Class<?>> getClassesInPackage(String packageName) {
		return getClassesInPackage(packageName, false);
	}

	public static DynamicList<Class<?>> getClassesInPackageTree(String packageName) {
		return getClassesInPackage(packageName, true);
	}

	// Add classes from names package. Search all elements of the class path - disk and Jars
	//
	private static DynamicList<Class<?>> getClassesInPackage(String packageName, boolean doTree) {
		DynamicList<Class<?>> classList = new DynamicList<Class<?>>();

		ClassLoader cl = ClassLoader.getSystemClassLoader();
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return classList;
	}

	// Collect classes from a disk-based class path
	// filtered by package name
	//
	private static void getClassesFromDisk(String packageName, File packageDir, DynamicList<Class<?>> classList, boolean doTree)  {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
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
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try {
			Class<?> theClass = classLoader.loadClass(entry.getName());
			classList.addUnique(theClass);
		} catch (Throwable e) {
			log.warning("JavaUtils.getClasses: '" + entry.getName() + "' " + e);
		}
	}

	// get  list of all classes on the class path
	//
	public static List<Class<?>> getClassList() {
		return getClassList(getClassNameList());
	}

	// doesnt work because some classes dont load simply.
	// forget this. Bad idea.
	public static List<Class<?>> getClassList(List<String> classNameList) {
		List<Class<?>> result = new LinkedList<Class<?>>();
		for (String className : classNameList)
			try {
				result.add(Class.forName(className));
			} catch (Exception e) {
				System.out.println("JavaUtils.getClassList: '" + className + "', "  + e);
				//e.printStackTrace();
				//throw new OmhtkException("JavaUtils.getClassList(): class not found '" + className + "'", e);
			}
		return result;
	}

	/**
	 * Returns all the classes found on the class path. 
	 * Use with caution, returns a huge mess.
	 * @return
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

	public static File fileForClass(Class<?> theClass) {
//		String resourceName = theClass.getName().replaceAll("\\.", "/") + ".class";
		//		System.out.println(resourceName);
		URL theURL = theClass.getResource(theClass.getSimpleName() + ".class");
		//		System.out.println(theClass);
		return new File(theURL.getPath());
	}

	public static File fileForClass(String className) {
		URL theURL = Resources.getURL(className.replaceAll("\\.", Jars.separator) + ".class");
		return new File(theURL.getPath());
	}


	public static void sleep(long ms) {
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
