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
package au.edu.anu.omhtk.jars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import au.edu.anu.rscs.aot.OmhtkException;
import au.edu.anu.rscs.aot.environment.Environment;
import au.edu.anu.rscs.aot.environment.LocalEnvironment;
import au.edu.anu.rscs.aot.util.JavaUtils;
import au.edu.anu.rscs.aot.util.Resources;
import fr.ens.biologie.generic.utils.Logging;

/**
 * <p>A class to package items into a 
 * <a href ="https://docs.oracle.com/javase/tutorial/deployment/jar/index.html">jar</a> file
 * with a version number. Includes a  
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jar.html#JAR_Manifest">
 * manifest file</a>.</p>
 * <p>Usage:
 * <ol>
 * <li>Define a descendant class setting the proper fields needed by your specific jar packing task.</li>
 * <li>Create an instance of this class.</li>
 * <li>Populate the instance with request to add entries to the jar using all the add&lt;something&gt; methods.</li>
 * <li>Set the main class / application entry point, if any.</li>
 * <li>Call {@code saveJar(...)} from the instance to create the jar file.</li>
 * </ol>  
 * </p>
 * 
 * @author Shayne Flint <br/> 
 * 		refactored by Jacques Gignoux 2017 <br/> 
 * 		added merging of services from multiple jars by Ian Davies 2020.
 *
 */
public abstract class Jars {

	/** The separator character for path names */
	public static final char separatorChar = '/';
	/** The separator String for path names */
	public static final String separator = "" + separatorChar;
	/** The lowest version number*/
	protected String version = "0.0.0";
	
	private static Logger log = Logging.getLogger(Jars.class);
	private Set<String> classNames = new HashSet<String>();
	private Set<JarFileRecord> files = new HashSet<JarFileRecord>();
	private Set<String> jars = new HashSet<String>();
	
	/** Information for the manifest - specification vendor */
	protected String specVendor = null;
	/** Information for the manifest - specification title */
	protected String specTitle = null;
	/** Information for the manifest - main class name */
	protected String mainClassName = null;
	private Set<String> dependsOnJars = new HashSet<String>();

	/**
	 * <p>A method to test if the code where the klass argument was found originates from a jar. 
	 * [hack found <a
	 * href=https://stackoverflow.com/questions/482560/can-you-tell-on-runtime-
	 * if-youre-running-java-from-within-a-jar> there</a>. The test is based on the
	 * existence of the manifest].
	 * </p>
	 * 
	 * @param klass the class to search for
	 */
	@SuppressWarnings("unused")
	public static String getRunningJarFilePath(Class<?> klass) {
		String result = null;
		try {
			result = new File(klass.getProtectionDomain().getCodeSource().getLocation().getPath()).toString();
			result = URLDecoder.decode(result, "UTF-8");
			ZipFile zipFile = new ZipFile(result);
			ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
			zipFile.close();
			return result;
		} catch (Exception exception) {
			return null;
		}
	}

	/** Information for the manifest - specification vendor */
	public String getSpecVendor() {
		return specVendor;
	}

	/** Information for the manifest - specification title */
	public String getSpecTitle() {
		return specTitle;
	}

	/** Information for the manifest - main class name */
	public String getVersion() {
		return version;
	}

//	public void setVersion(String major, String minor, String micro) {
//		version = major+"."+minor+"."+micro;
//	}

	/**
	 * Add all entries found in a jar to this jar.
	 * @param jarName the jar to copy entries from
	 */
	public void addDependencyOnJar(String jarName) {
		dependsOnJars.add(jarName);
	}

	/**
	 * Add a class to this jar.
	 * @param className the class to add
	 */
	public void addClass(String className) {
		this.classNames.add(className);
	}

	/**
	 * Add all entries found in a package to this jar.
	 * @param packageName the package which classes are added to this jar
	 */
	public void addPackage(String packageName) {
		for (Class<?> c : JavaUtils.getClassesInPackage(packageName))
			this.classNames.add(c.getName());
	}

	/**
	 * Add all entries found in a package to this jar.
	 * @param packageName the package which classes are added to this jar.
	 */
	public void addPackageTree(String packageName) {
		for (Class<?> c : JavaUtils.getClassesInPackageTree(packageName))
			this.classNames.add(c.getName());
	}

	/**
	 * Sets the <a href="https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html">entry point</a> of this jar
	 * @param className the class to use as an entry point - must have a {@code main(...)} method
	 */
	public void setMainClass(String className) {
		this.mainClassName = className;
	}

	/**
	 * Add any file (i.e. not only java classes) to this jar.
	 * @param fileName the name of the file
	 * @param jarDirectory the directory where the file is
	 */
	public void addFile(String fileName, String jarDirectory) {
		File file = new File(fileName);
		this.files.add(new JarFileRecord(fileName, jarDirectory + Jars.separator + file.getName()));
	}

	/**
	 * Recursive addition of (non-java) resources in jars (i.e. excluding .java and .class
	 * files). Collects all non-java files from the package argument and adds them to this jar.<br/>
	 * <Strong>CAUTION</strong>: directory names including dots are not supported (e.g. workflow.graffle).
	 * 
	 * @param packageName the java package name to search for resources, i.e. with dots as separators
	 */
	// CAUTION: 
	// * packageName = java name of the package, i.e. with dots as separators
	// * absolutePackageDirName = absolute packageDirName, i.e. with the root of the threeworlds 
	// 		project hierarchy as a prefix
	public void addResources(String packageName) {
		log.info("adding resources in jar from package " + packageName);
		URL root = ClassLoader.getSystemResource("");
		String absolutePackageDirName = root.getFile() + packageName.replace('.', '/');
		addResources(packageName, absolutePackageDirName);
	}

	// the recursion for the addResources(String packageName) method
	private void addResources(String packageName, String absolutePackageDirName) {
		File dir = new File(absolutePackageDirName);
		if (dir.isDirectory())
			for (String fn : dir.list()) {
				File f = new File(absolutePackageDirName + File.separator + fn);
				if (f.isDirectory())
					addResources(packageName + "." + fn, absolutePackageDirName + File.separator + fn);
				else if (!(fn.endsWith(".java") | fn.endsWith(".class") | fn.endsWith(".jar"))) {
					// addResourceFile(fn,packageName);
					log.info("adding resource " + fn);
					files.add(new JarFileRecord(f.getPath(),
							packageName.replace(".", File.separator) + File.separator + fn));
				}
			}
		else
			throw new OmhtkException("Error packing jar: " + packageName + " is not a directory");
	}

	/**
	 * Add a single (non-java) resource file to this jar. <br/> 
	 * <Strong>CAUTION</strong>: directory names including dots are not supported (e.g. workflow.graffle).
	 * @param resourceName the name of the resource
	 * @param packageName the java package name to search for the resource, i.e. with dots as separators
	 */
	public void addResourceFile(String resourceName, String packageName) {
		File file = Resources.getFile(resourceName, packageName);
		this.files.add(new JarFileRecord(file.getPath(),
				packageName.replace(".", File.separator) + File.separator + resourceName));
	}

	/**
	 * Add an external <a href="https://ant.apache.org/ivy/">ivy</a> library to this jar.
	 * @param org organisation responsible for the library maintenance - see ivy specification for explanation
	 * @param name library name
	 * @param version library version - see ivy specification for the version number interpretation
	 */
	public void addIvyLibrary(String org, String name, String version) {
		Environment env = new LocalEnvironment("");
		String userHome = env.userHome();
		String ivyCache = env.makePath(userHome, ".ivy2", "cache");
		String jarPath = env.makePath(ivyCache, org, name, "jars", name + "-" + version + ".jar");
		jars.add(jarPath);
	}

	/**
	 * Add the content of a jar file to this jar.
	 * @param jarPath the path to the jar file which entries are to be copied
	 */
	public void addJar(String jarPath) {
		jars.add(jarPath);
	}

	/**
	 * Add a resource file to this jar.
	 * @param resourceName the name of the resource file to search  
	 * @param packageName the package name containing the resource
	 */
	// CAUTION: jars cannot be searched for file names - this must be wrong!
	public void addResourceJar(String resourceName, String packageName) {
		File file = Resources.getFile(resourceName, packageName);
		jars.add(file.getAbsolutePath());
	}

	// local class to store the file and java name of a jar entry
	private class JarFileRecord {
		String fileName;
		String jarName;

		public JarFileRecord(String fileName, String jarName) {
			this.fileName = fileName;
			this.jarName = jarName;
		}
	}

	/**
	 * Save this jar instance as a jar file. Uses all the information stored in this class instance
	 * after calls to {@code add...(...)} methods and manifest settings to write the jar file. 
	 * Also packs jar <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jar.html#Service_Provider">services</a>
	 * found in included jars.
	 * @param jarFile the name of the jar file to create
	 */
	// NB refactored by JG to prevent duplicate jar entries.
	public void saveJar(File jarFile) {
		final int BUFFER_SIZE = 10240;
		try {
			byte buffer[] = new byte[BUFFER_SIZE];
			// manifest
			Manifest manifest = new Manifest();
			manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
			manifest.getMainAttributes().put(Attributes.Name.SPECIFICATION_TITLE, specTitle);
			manifest.getMainAttributes().put(Attributes.Name.SPECIFICATION_VENDOR, specVendor);
			manifest.getMainAttributes().put(Attributes.Name.SPECIFICATION_VERSION, version);
			if (mainClassName != null)
				manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClassName);
			if (!dependsOnJars.isEmpty()) {
				String deps = "";
				for (String dep : dependsOnJars) {
					// if (!dep.startsWith("."))
					// dep="./"+dep;
					deps += dep + " ";
				}
				deps = deps.trim();
				manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH, deps);
//				System.out.println("ATTRIBUTES: "+manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH));
			}
			if (jarFile.exists())
				jarFile.delete();
			// FileOutputStream stream = new FileOutputStream(jarFile);
			// jar entries
			// JarOutputStream out = new JarOutputStream(stream, manifest);
			JarOutputStream jarOutStream = new JarOutputStream(new FileOutputStream(jarFile), manifest);
			Set<String> jEntryList = new HashSet<>();

			for (String className : classNames) {
				log.info("adding class " + className);
				File classFile = JavaUtils.fileForClass(className);
				JarEntry jarEntry = new JarEntry(className.replaceAll("\\.", Jars.separator) + ".class");
				jarEntry.setSize(classFile.length());
				jarEntry.setTime(classFile.lastModified());
				jarEntry.setCompressedSize(-1);
				jEntryList.add(jarEntry.getName());
				jarOutStream.putNextEntry(jarEntry);
				FileInputStream in = new FileInputStream(classFile);
				while (true) {
					int nRead = in.read(buffer, 0, buffer.length);
					if (nRead <= 0)
						break;
					jarOutStream.write(buffer, 0, nRead);
				}
				jarOutStream.closeEntry();
				in.close();
			}

			for (JarFileRecord jfr : files) {
				log.info("adding file " + jfr.fileName);
				File inFile = new File(jfr.fileName);
//				String jarEntryString = jfr.jarName.replace("\\", Jar.separator);
				String jarEntryString = jfr.jarName.replaceAll("\\\\", Jars.separator);
				if (jarEntryString.startsWith(Jars.separator)) {
					jarEntryString = jarEntryString.replaceFirst(Jars.separator, "");
				}
				JarEntry jarEntry = new JarEntry(jarEntryString);
				jarEntry.setSize(inFile.length());
				jarEntry.setTime(inFile.lastModified());
				jarEntry.setCompressedSize(-1);
				if (!jEntryList.contains(jarEntry.getName())) {
					jEntryList.add(jarEntry.getName());
					jarOutStream.putNextEntry(jarEntry);
					FileInputStream in = new FileInputStream(inFile);
					while (true) {
						int nRead = in.read(buffer, 0, buffer.length);
						if (nRead <= 0)
							break;
						jarOutStream.write(buffer, 0, nRead);
					}
					jarOutStream.closeEntry();
					in.close();
				}
			}

			Map<String, List<String>> services = getAllServices(jars);

			for (String jarFileName : jars) {
				File file = new File(jarFileName);
				JarFile jarInputFile = new JarFile(file);
				JarInputStream jarInStream = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				while ((jarEntry = jarInStream.getNextJarEntry()) != null) {
					String name = jarEntry.getName();
					if (!jEntryList.contains(name)) {
						jEntryList.add(name);
						if (!name.equals("META-INF/MANIFEST.MF")) {
							jarOutStream.putNextEntry(new JarEntry(name));
							InputStream entryInStream = jarInputFile.getInputStream(jarEntry);

							if (services.containsKey(name)) {// get contents from amalgamated string list of service
																// entry contents
								List<String> lines = services.get(name);
								for (String line : lines)
									jarOutStream.write(line.getBytes(StandardCharsets.UTF_8));
							} else { // just read the file
								byte[] inBuffer = new byte[4096];
								int bytesRead = 0;
								while ((bytesRead = entryInStream.read(inBuffer)) != -1)
									jarOutStream.write(inBuffer, 0, bytesRead);
							}

							entryInStream.close();
							jarOutStream.flush();
							jarOutStream.closeEntry();
						}
					}
				}
				jarInputFile.close();
				jarInStream.close();
			}
			jarOutStream.close();
			if (!services.isEmpty())
				System.out.println("------------- SERVICES ---------------");
			int count = 0;
			for (Map.Entry<String, List<String>> e : services.entrySet())
				System.out.println(++count + "\t" + e.getValue().size() + " entries in " + e.getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Append service entries to a list of strings for later adding to the output
	 * jar.
	 */
	private static Map<String, List<String>> getAllServices(Set<String> jars) {
		Map<String, List<String>> result = new HashMap<>();
		for (String jarFileName : jars) {
			File file = new File(jarFileName);
			JarFile jarInputFile;
			try {
				jarInputFile = new JarFile(file);
				JarInputStream jarInStream = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				// source jars contain the same file so ignore to avoid duplication
				if (!jarFileName.contains("/source/")) { // TODO WINDOWS - may not work!
					while ((jarEntry = jarInStream.getNextJarEntry()) != null) {
						String name = jarEntry.getName();
						if (name.contains("META-INF/services/") && name.contains(".")) {/// TODO WINDOWS - may not work!
							InputStream entryInStream = jarInputFile.getInputStream(jarEntry);
							String serviceEntry = IOUtils.toString(entryInStream, StandardCharsets.UTF_8);
							List<String> list = getServiceList(result, jarEntry.getName());
							list.add(serviceEntry);
							entryInStream.close();
						}
					}
				}
				jarInputFile.close();
				jarInStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private static List<String> getServiceList(Map<String, List<String>> services, String key) {
		if (services.containsKey(key))
			return services.get(key);
		services.put(key, new ArrayList<>());
		return services.get(key);
	}

	/**
	 * Utility to list the content of a jar file.
	 * @param fileName the jar file to list.
	 */
	public void listJarContents(String fileName) {
		File file = new File(fileName);
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
			JarInputStream jar = new JarInputStream(stream);
			JarEntry jarEntry;
			while ((jarEntry = jar.getNextJarEntry()) != null) {
				String name = jarEntry.getName();
				long size = jarEntry.getCompressedSize();
				long time = jarEntry.getTime();
				log.info(name + ", " + size + ", " + time);
			}
			jar.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
