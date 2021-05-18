package fr.ens.biologie.codeGeneration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.logging.Logger;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import au.edu.anu.rscs.aot.OmhtkException;
import fr.ens.biologie.generic.utils.Logging;

/**
 * A class to compile a {@code .java} file to a {@code .class} file.
 * <p>Usage: (1) get an instance, (2) call one of the compileCode(...) methods. It can
 * compile a single java file or all the files found in a root directory.</p>
 * 
 * @author Shayne Flint, <br/>modified by Jacques Gignoux - 10 sept. 2014
 *
 */
public final class JavaCompiler {

	private static Logger log = Logging.getLogger(JavaCompiler.class);
	/** the java compiler */
	private javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	/**
	 * Compile one {@code .java} file into a {@code .class} file. Display
	 * compile errors in the console if any.
	 * 
	 * @param classFile    the java file to compile
	 * @param compilerRoot the root directory for compilation
	 * @return error messages, if any
	 */
	public final String compileCode(File classFile, File compilerRoot) {
		log.info("Compiling " + classFile.getName() + " ...");
		String result = compileClass(classFile, compilerRoot);
		if ((result != null) && (!result.isEmpty())) {
			log.severe("********************************************************************");
			log.severe("There were compiling errors in " + classFile.getName() + ":");
			log.severe(result);
			log.severe("********************************************************************");
		}
		log.info("done.");
		if ((result != null) && (!result.isEmpty())) {
			return result;
		} else
			return null;
	}
	
	// helper for the previous method
	private final String compileClass(File classFile, File sourceDir) {
		String result = null;
		// Must have a compiler available!
		if (compiler == null)
			throw new OmhtkException("No complier provided: Please install jdk");
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
		File[] files = new File[] { classFile };
		Iterable<? extends JavaFileObject> compilationUnits = stdFileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(files));
		LinkedList<String> options = new LinkedList<String>();
		options.add("-sourcepath");
		options.add(sourceDir + File.separator);
		options.add("-classpath");
		options.add(System.getProperty("java.class.path"));
		options.add("-Xlint"); // due to a strange error with DataContainer (usually a warning, actually ??)
		StringWriter errors = new StringWriter();
		javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(errors, null, null, options, null,
				compilationUnits);
		task.call();
		result = errors.toString();
		try {
			stdFileManager.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		return result;
	}
		
	/**
	 * Compile all {@code .java} files found in the compiler root. Display
	 * compile errors in the console if any.
	 * 
	 * @param compilerRoot the root directory for compilation
	 * @return error messages, if any
	 */
	public final String compileCode(File compilerRoot) {
		log.info("Compiling all java files in " + compilerRoot.getName() + " ...");
		String result = compileClass(compilerRoot);
		if ((result != null) && (!result.isEmpty())) {
			log.severe("********************************************************************");
			log.severe("There were compiling errors in " + compilerRoot.getName() + ":");
			log.severe(result);
			log.severe("********************************************************************");
		}
		log.info("done.");
		if ((result != null) && (!result.isEmpty()))
			return result;
		else
			return null;
	}

	// helper for the previous method
	private final String compileClass(File sourceDir) {
		String result = null;
		// Must have a compiler available!
		if (compiler == null)
			throw new OmhtkException("No complier provided: Please install jdk");
		if (!sourceDir.isDirectory())
			throw new OmhtkException("Error: '"+sourceDir+"' is not a directory");
		FileFilter ff = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.toString().endsWith(".java");
			}
		};
		File[] javaFiles = sourceDir.listFiles(ff);
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);		
		Iterable<? extends JavaFileObject> compilationUnits = stdFileManager
			.getJavaFileObjectsFromFiles(Arrays.asList(javaFiles));
		LinkedList<String> options = new LinkedList<String>();
		options.add("-sourcepath");
		options.add(sourceDir + File.separator);
		options.add("-classpath");
		options.add(System.getProperty("java.class.path"));
		options.add("-Xlint"); // due to a strange error with DataContainer (usually a warning, actually ??)
		StringWriter errors = new StringWriter();
		javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(errors, null, null, options, null,
			compilationUnits);
		task.call();
		result = errors.toString();
		try {
			stdFileManager.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}

}
