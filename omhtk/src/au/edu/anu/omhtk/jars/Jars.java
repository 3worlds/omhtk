package au.edu.anu.omhtk.jars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import au.edu.anu.rscs.aot.OmhtkException;
import au.edu.anu.rscs.aot.environment.Environment;
import au.edu.anu.rscs.aot.environment.LocalEnvironment;
import au.edu.anu.rscs.aot.util.JavaUtils;
import au.edu.anu.rscs.aot.util.Resources;

/**
 * A class to package items into a jar with a version number
 * 
 * @author Shayne Flint refactored by Jacques Gignoux 2017
 *
 */
public abstract class Jars { 
	
	public static final char separatorChar = '/';
	public static final String separator = ""+separatorChar;
	
	protected String version = "0.0.0";
	private static Logger log = Logger.getLogger(Jars.class.getName());
	static {
		log.setLevel(Level.OFF);
	}
	private Set<String> classNames = new HashSet<String>();
	private Set<JarFileRecord> files = new HashSet<JarFileRecord>();
	private Set<String> jars = new HashSet<String>();
	// for the manifest:
	protected String specVendor = null;
	protected String specTitle = null;
	protected String mainClassName = null;
	private Set<String> dependsOnJars = new HashSet<String>();
	
	/**
	 * <p>
	 * Testing if the code where the klass argument was found originates from a jar - 
	 * hack found <a
	 * href=https://stackoverflow.com/questions/482560/can-you-tell-on-runtime-
	 * if-youre-running-java-from-within-a-jar> there</a>. The test is based on the
	 * existence of the manifest.
	 * </p>
	 * @param klass the class to search for
	 */
	@SuppressWarnings("unused")
	public static String getRunningJarFilePath(Class<?> klass) {
		String result = null;
		try {
			result = new File(klass.getProtectionDomain().getCodeSource().getLocation().getPath())
					.toString();
			result = URLDecoder.decode(result, "UTF-8");
			ZipFile zipFile = new ZipFile(result);
			ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
			zipFile.close();
			return result;
		} catch (Exception exception) {
			return null;
		}
	}
	
	public String getSpecVendor() {
		return specVendor;
	}
	
	public String getSpecTitle() {
		return specTitle;
	}
	
	public String getVersion() {
		return version;
	}
	
//	public void setVersion(String major, String minor, String micro) {
//		version = major+"."+minor+"."+micro;
//	}

	public void addDependencyOnJar(String jarName) {
		dependsOnJars.add(jarName);
	}

	public void addClass(String className) {
		this.classNames.add(className);
	}

	public void addPackage(String packageName) {
		for (Class<?> c : JavaUtils.getClassesInPackage(packageName))
			this.classNames.add(c.getName());
	}

	public void addPackageTree(String packageName) {
		for (Class<?> c : JavaUtils.getClassesInPackageTree(packageName))
			this.classNames.add(c.getName());
	}

	public void setMainClass(String className) {
		this.mainClassName = className;
	}

	public void addFile(String fileName, String jarDirectory) {
		File file = new File(fileName);
		this.files.add(new JarFileRecord(fileName, jarDirectory + Jars.separator + file.getName()));
	}

	/**
	 * Recursive addition of resources in jars (i.e. excluding .java and .class
	 * files). CAUTION:
	 * <ul>
	 * <li>packageName = java name of the package, i.e. with dots as separators</li>
	 * <li>absolutePackageDirName = absolute packageDirName, i.e. with the root of
	 * the threeworlds project hierarchy as a prefix</li>
	 * </ul>
	 * 
	 * @param packageName
	 *            the java package name
	 */
	public void addResources(String packageName) {
		log.info("adding resources in jar from package " + packageName);
		URL root = ClassLoader.getSystemResource("");
		String absolutePackageDirName = root.getFile() + packageName.replace('.', '/');
		addResources(packageName, absolutePackageDirName);
	}

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

	// this crashes when directory names include dots without being packages (e.g.
	// workflow.graffle)
	public void addResourceFile(String resourceName, String packageName) {
		File file = Resources.getFile(resourceName, packageName);
		this.files.add(new JarFileRecord(file.getPath(),
				packageName.replace(".", File.separator) + File.separator + resourceName));
	}

	public void addIvyLibrary(String org, String name, String version) {
		Environment env = new LocalEnvironment("");
		String userHome = env.userHome();
		String ivyCache = env.makePath(userHome, ".ivy2", "cache");
		String jarPath = env.makePath(ivyCache, org, name, "jars", name + "-" + version + ".jar");
		jars.add(jarPath);
	}

	public void addJar(String jarPath) {
		jars.add(jarPath);
	}

	public void addResourceJar(String jarName, String packageName) {
		File file = Resources.getFile(jarName, packageName);
		jars.add(file.getAbsolutePath());
	}

	private class JarFileRecord {
		String fileName;
		String jarName;

		public JarFileRecord(String fileName, String jarName) {
//			System.out.println("Read: "+fileName+"\n as: "+jarName+"\n");
			this.fileName = fileName;
			this.jarName = jarName;
		}
	}

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
				// we may need to check : mydir/file.txt as opposed to /mydir/file.txt
				if (jarEntryString.startsWith(Jars.separator)) {
					jarEntryString = jarEntryString.replaceFirst(Jars.separator, "");
				}
				JarEntry jarEntry = new JarEntry(jarEntryString);
//				if (jarEntryString.contains(".ods"))
//					System.out.println(jarEntry);// we need to make these file project relative
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

			for (String jarFileName : jars) {
				File file = new File(jarFileName);
				JarFile jarInputFile = new JarFile(file);
				FileInputStream inputStream = new FileInputStream(file);
				JarInputStream jar = new JarInputStream(inputStream);
				JarEntry jarEntry;
				while ((jarEntry = jar.getNextJarEntry()) != null) {
					String name = jarEntry.getName();
					if (!jEntryList.contains(name))
						// you dont want all that's in dependencies' jars manifests etc.
						if (!name.startsWith("META-INF")) {
							log.info("adding jar entry " + jarEntry.getName());
							jEntryList.add(name);
							jarOutStream.putNextEntry(new JarEntry(name));
							InputStream is = jarInputFile.getInputStream(jarEntry);

							byte[] inBuffer = new byte[4096];
							int bytesRead = 0;
							while ((bytesRead = is.read(inBuffer)) != -1) {
								jarOutStream.write(inBuffer, 0, bytesRead);
							}
							is.close();
							jarOutStream.flush();
							jarOutStream.closeEntry();
						}
				}
				jarInputFile.close();
				jar.close();
			}

			// This closes the stream
			jarOutStream.close();
			// This does nothing
			// stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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