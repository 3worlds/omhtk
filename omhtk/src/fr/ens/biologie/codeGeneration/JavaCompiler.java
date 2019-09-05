package fr.ens.biologie.codeGeneration;

import java.io.File;
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

/**
 * Compiles a .java file to a .class file
 * 
 * @author Shayne Flint, modified by Jacques Gignoux - 10 sept. 2014 not meant
 *         to have descendants
 *
 */
public final class JavaCompiler {

	private Logger log = Logger.getLogger(JavaCompiler.class.getName());
	/** the java compiler */
	private javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	/** the directory where the compiler is called from (sourcepath) */

	/**
	 * compiles one .java file into a .class file
	 * 
	 * @param classFile    the java file to compile
	 * @param compilerRoot the root directory for compilation
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

	/**
	 * This method compiles one .java file into one .class file.
	 * 
	 * @param classFile the .java file to compile
	 * @param sourceDir the source directory (containing .java file)
	 * @param targetDir the target directory (where to put .class file)
	 */
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

}
