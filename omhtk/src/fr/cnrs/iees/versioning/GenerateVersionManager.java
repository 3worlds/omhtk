package fr.cnrs.iees.versioning;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.List;
import java.util.EnumSet;
import java.util.Set;

/**
 * <p>A little helper to generate identical VersionManager.java files in a set of libraries.</p>
 * 
 * <p>Note: VersionManager depends on a VersionSettings.java file which is different for every
 * library as it lists the dependencies, library name, basically all library-dependent
 * parameters needed for VersionManager.java to run. This package also provides a template
 * for VersionSettings.java, but this is not copied by default as it must be edited by the 
 * user.</p>
 * 
 * <p>This class will copy the VersionManager.java.template file into all the provided
 * directories. The directories can be provided as arguments on the command-line; if not, they
 * will be read in the VersionManagerDirectories.txt file that sits with this class</p>
 * 
 * <p>The copied file is read-only to prevent accidental editing.</p>
 * 
 * @author Jacques Gignoux - 19 févr. 2019
 *
 */
public class GenerateVersionManager {
	
	private static String defaultDir = "scripts";
	private static String defaultPackage = "fr.cnrs.iees.versioning";
	private static String templateName =  System.getProperty("user.dir") // <home dir>/<eclipse workspace>/<project>
			+ File.separator + "src" 
			+ File.separator + GenerateVersionManager.class.getPackage().getName().replace('.',File.separatorChar) 
			+ File.separator + "VersionManager.java.template";
	private static File sourceFile = new File(templateName);
	
	private static void copyFiles(String library) {
		// This is brittle but safer - you have to know what you do
		String targetFileName = System.getProperty("user.home") // <home dir>
			+ File.separator + "git"
			+ File.separator + library
			+ File.separator + library
			+ File.separator + defaultDir
			+ File.separator + defaultPackage.replace('.',File.separatorChar)
			+ File.separator + "VersionManager.java";
		System.out.print("Copying template to "+targetFileName+"...");
		File targetFile = new File(targetFileName);
		try {
			// copy template to VersionManager.java in proper directory
			Files.copy(sourceFile.toPath(), 
				targetFile.toPath(), 
				StandardCopyOption.REPLACE_EXISTING);
			// set VersionManager.java read-only to make sure it's not edited by accident
			Set<PosixFilePermission> perms =  EnumSet.of(PosixFilePermission.GROUP_READ,
				PosixFilePermission.OWNER_READ,
				PosixFilePermission.OTHERS_READ);
			Files.setPosixFilePermissions(targetFile.toPath(),perms);
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
		System.out.println(" Done");
	}
	
	public static void main(String[] args) {
		// command-line arguments must be valid library names (eg omhtk, omugi, aot, etc...)
		if (args.length>0) { 
			for (String arg:args)
				copyFiles(arg);
		}
		// default: use VersionManagerDirectories.txt in this package
		else { 
			String libraryList = System.getProperty("user.dir") // <home dir>/<eclipse workspace>/<project>
				+ File.separator + "src" 
				+ File.separator + GenerateVersionManager.class.getPackage().getName().replace('.',File.separatorChar) 
				+ File.separator + "VersionManagerDirectories.txt";
			File libFile = new File(libraryList);
			List<String> lines = null;
			try {
				lines = Files.readAllLines(libFile.toPath());
			} catch (IOException e) {
				System.out.println("FATAL ERROR: default library list file ("+libraryList+") not found.");
				e.printStackTrace();
			}
			if (lines!=null)
				for (String s:lines)
					copyFiles(s);
		}
	}

}
