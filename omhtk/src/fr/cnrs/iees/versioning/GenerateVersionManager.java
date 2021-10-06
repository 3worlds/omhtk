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
 * library (= java project) as it lists the dependencies, library name, basically all library-dependent
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
	
	/**
	 * Copies template files for {@code VersionManager} and {@code VersionSettings} into one or more
	 *  java projects.
	 * <p>The project directories must be passed on the command line, as Strings separated by white space.
	 * If no argument is passed, it uses default directories listed in the file <em>VersionManagerDirectories.txt</em>.
	 * If this file is not found in the same package as this class, it fails with an error message.</p>
	 *  
	 * @param args valid java project directories
	 */
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
