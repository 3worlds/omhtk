package fr.cnrs.iees.versioning;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
 * @author Jacques Gignoux - 19 févr. 2019
 *
 */
public class GenerateVersionManager {

	public static void main(String[] args) {
		if (args.length>0) {
			
		}
		else { // default: use VersionManagerDirectories.txt in this package
			String defaultDir = "scripts";
			String defaultPackage = "fr.cnrs.iees.versioning";
			String libraryList = System.getProperty("user.dir") // <home dir>/<eclipse workspace>/<project>
				+ File.separator + "src" 
				+ File.separator + GenerateVersionManager.class.getPackage().getName().replace('.',File.separatorChar) 
				+ File.separator + "VersionManagerDirectories.txt";
			String templateName =  System.getProperty("user.dir") // <home dir>/<eclipse workspace>/<project>
					+ File.separator + "src" 
					+ File.separator + GenerateVersionManager.class.getPackage().getName().replace('.',File.separatorChar) 
					+ File.separator + "VersionManager.java.template";
			File libFile = new File(libraryList);
			File sourceFile = new File(templateName);
	
			try {
				List<String> lines = Files.readAllLines(libFile.toPath());
				for (String s:lines) {
					System.out.println(s);
					String targetFileName = System.getProperty("user.dir");
					File targetFile = new File(targetFileName);
//					Files.copy(sourceFile.toPath(), out, REPLACE_EXISTING);
					Set<PosixFilePermission> perms =  EnumSet.of(PosixFilePermission.GROUP_READ,
						PosixFilePermission.OWNER_READ,
						PosixFilePermission.OTHERS_READ);
					Files.setPosixFilePermissions(targetFile.toPath(),perms);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
