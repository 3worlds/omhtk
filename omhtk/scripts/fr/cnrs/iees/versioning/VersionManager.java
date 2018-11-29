/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2018: Shayne FLint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent, plus some other utilities. The kind of things you need    *
 *  in all software projects and keep rebuilding all the time.            *
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
 *  along with UIT.  If not, see <https://www.gnu.org/licenses/gpl.html>. *
 *                                                                        *
 **************************************************************************/
package fr.cnrs.iees.versioning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static java.nio.file.StandardCopyOption.*;

/**
 * <p>A class to generate ivy.xml files with the proper versioning while keeping the version history.</p>
 * <p>Every time this class {@code main()} method is run, it will:</p>
 * <ul>
 * <li>compute a new version number</li>
 * <li>archive the former scripts ({@code ivy.xml} and {@code build.xml}) into its package directory, by appending their
 * version number (e.g. {@code ivy-6.23.765.xml})</li>
 * <li>re-write new {@code ivy.xml} and {@code build.xml} with the new version number</li>
 * </ul>
 * <p>So it is a bad idea to run this class just 'to see'. Know what you do.</p>
 * <p>The current version number is stored in the {@code current-version.txt} file which should
 * NEVER be edited by hand. It is updated at every run of this class.</p>
 * <p>Invoking {@code main()} with no argument will cause an increase in the last version number
 * (the 'build' number). To increase major or minor version numbers (ie the first and second version
 * numbers), the command-line arguments {@code -major} and {@code -minor} should be used.</p> 
 * 
 * <p>To adapt this class to the evolution of the project code, static fields at the top of the class
 * should be updated. In particular, the dependency list can evolve with versions. </p>
 * 
 * <p>TODO: {@code build.xml} may need some refactoring to deal with publishing the project to remote locations.</p> 
 * 
 * @author Jacques Gignoux - Oct. 2018
 *
 */
public class VersionManager {
	
	// Change these fields to suit the project ====================================================
	
	/** The organisation name as will appear in the ivy module specification - it is a good idea
	 * to keep it consistent with the project src directory (although not required).*/
	private static String ORG = "fr.ens.biologie";
	
	/** The name of the ivy module (this will be the name of the generated jar file for 
	 * dependent projects).*/
	private static String MODULE = "generics";
	
	/** The ivy status of the module: integration, milestone, or release are the ivy defaults
	 * But we can define ours like bronze, gold, silver, or crap, supercrap, ultracrap. */
	private static String STATUS = "integration";
	
	/** The license under which this module (= jar) is distributed */
	private static String LICENSE = "gpl3";
	
	/**The url to the text of the license */
	private static String LICENSE_URL = "https://www.gnu.org/licenses/gpl-3.0.txt";
	
	/**A (long) description of the ivy module */
	private static String DESCRIPTION = 
		"This module contains generic, all-purpose, basic utilities for almost any java project.";
	
	/**
	 * <p>Dependencies on other modules (they will be integrated in the ivy script).</p>
	 * 
	 * <p>This is a (n * 3) table of Strings.<br/>
	 * Every line is a new dependency.
	 * On every line, the 3 Strings must match the ivy fields 'org' (for organisation), 
	 * 'name' (for the module name), and 'rev' (for the revision or version number). The '+' can
	 * be conveniently used to specify 'any version'.
	 * The field can be empty (just needs the external braces).<br/>
	 * Example value: 
	 * <pre>{{"org.galaxy.jupiter","crap","1.0.+"},
	 * {"org.ocean.lostIsland","strungk","3.12.254"}}</pre> </p>
	 * <p>Wildcards for revision numbers are indicated <a href="http://ant.apache.org/ivy/history/master/ivyfile/dependency.html">there</a>.</p>
	 * 
	 */
	private static String[][] DEPS = { 
	};
	// ============================================================================================
	
	// This shouldnt be changed ===================================================================
	private static String workDir = System.getProperty("user.dir") + File.separator + "scripts";
	private static String packageDir = VersionManager.class.getPackage().getName().replace('.', File.separatorChar);
	private static String DOT = ".";
	
	private static String headComment1 = 
	"<!--===================================================================================\n"+
	" ant and ivy scripts for project <" + MODULE +"> version ";
	private static String headComment2 = 
	" (" + new Date() +")\n" +
	" author: Jacques Gignoux <jacques.gignoux@upmc.fr>\n\n" +
	" CAUTION: this file is generated - DO NOT EDIT!\n" +
	" If you need to change this file, edit ../version/Setup.java appropriately and run it.\n" +
	" This will re-generate this file and manage version numbers properly. \n" +
	"===================================================================================-->\n";
	
	private static String ivy1 = 
	"<ivy-module version=\"2.0\"\n" +
	"\t\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
	"\t\txsi:noNamespaceSchemaLocation=\"http://ant.apache.org/ivy/schemas/ivy.xsd\">\n\n" +
	"\t<info\torganisation=\"" + ORG  + "\"\n" +
	"\t\t\tmodule=\"" + MODULE + "\"\n" +
	"\t\t\trevision=\"";
 	
	private static String ivy2 =
	"\"\n" +
	"\t\t\tstatus=\"" + STATUS +"\">\n" +
 	"\t\t<license name=\"" + LICENSE + "\" url=\""+ LICENSE_URL + "\"/>\n" +
 	"\t\t<description>" + DESCRIPTION + "</description>\n" +
 	"\t</info>\n\n" +
 	"\t<configurations>\n" +
 	"\t\t<conf name=\"java library\"/>\n" +
 	"\t</configurations>\n\n" +
 	"\t<publications>\n" +
 	"\t\t<artifact type=\"jar\" ext=\"jar\">\n" +
 	"\t\t\t<conf name=\"java library\"/>\n" +
 	"\t\t</artifact>\n" +
 	"\t</publications>\n\n";
	    
	private static String ivy3 =
	"</ivy-module>\n";
	
	private static String build1 =
	"<project xmlns:ivy=\"antlib:org.apache.ivy.ant\" basedir=\"..\">\n\n" +
	"\t<!-- Artifacts are produced in the project lib directory-->\n" +
	"\t<property name=\"jarRepo\" location=\"${user.dir}/../lib\"/>\n\n" +
	"\t<!-- Use this string as the organisation package -->\n" +
	"\t<property name=\"org\" value=\"" + ORG +"\"/>\n\n" +
	"\t<!-- The project name -->\n" +
	"\t<property name=\"project\" value=\""+ MODULE +"\"/>\n\n" +
	"\t<!-- Append the project name to the organisation string -->\n" +
	"\t<property name=\"jarlib\" location=\"${jarRepo}/${org}.${project}\"/>\n\n" +
	"\t<!-- Tasks -->\n" +
	"\t<target name=\"makeArtifactDir\" description=\"create directory for jars\">\n" +
	"\t\t<mkdir dir=\"${jarlib}\"/>\n" +
	"\t</target>\n\n" +
	"\t<target name=\"resolve\" description=\"resolve dependencies and setup publication environment\">\n" +
	"\t\t<ivy:configure/>\n" +
	"\t\t<ivy:resolve file=\"scripts/${ivy.dep.file}\" conf=\"${ivy.configurations}\"/>\n" +
	"\t\t<ivy:retrieve pattern=\"${ivy.retrieve.pattern}\" conf=\"${ivy.configurations}\"/>\n" +
	"\t</target>\n\n" +
	"\t<target name=\"makeJar\" description=\"pack as a jar library\" depends=\"makeArtifactDir,resolve\">\n" +
	"\t\t<jar destfile=\"${jarlib}/${project}.jar\" basedir=\"bin\" excludes=\"**/VersionManager.*,**/LicenseManager.*,**/current-version.txt\"/>\n" +
	"\t</target>\n\n" +
	"\t<target name=\"publishJar\" description=\"make jar library available to others\" depends=\"makeJar\">\n" +
	"\t\t<ivy:publish resolver=\"local\" overwrite=\"true\"  forcedeliver=\"true\">\n" +
	"\t\t\t<artifacts pattern=\"${jarlib}/[artifact].[ext]\"/>\n" +
	"\t\t</ivy:publish>\n" +
	"\t</target>\n\n" +
	"</project>\n";
	
	private static String buildDependencyList() {
		if (DEPS.length>0) {
			StringBuilder sb = new StringBuilder();
			sb.append("\t<dependencies>\n");
			for (int i=0; i<DEPS.length; i++) {
				sb.append("\t\t<dependency org=\"")
					.append(DEPS[i][0])
					.append("\" name=\"")
					.append(DEPS[i][1])
					.append("\" rev=\"")
					.append(DEPS[i][2])
					.append("\"/>\n");
			}
			sb.append("\t</dependencies>\n\n");
			return sb.toString();
		}
		return "\t<dependencies/>\n\n";
	}
	
	// ============================================================================================
	public static void main(String[] args) {
		// version data
		StringBuilder version = new StringBuilder();
		int major=0, minor=0, build=0;
		
		// read current version
		File vfile = Paths.get(workDir, packageDir, "current-version.txt").toFile(); 
		if (vfile.exists()) 
			try {
				BufferedReader fr = new BufferedReader(new FileReader(vfile));
				String line = fr.readLine();
				while (line!=null) {
					if (line.startsWith("MAJOR")) major = Integer.valueOf(line.split("=")[1]);
					if (line.startsWith("MINOR")) minor = Integer.valueOf(line.split("=")[1]);
					if (line.startsWith("BUILD")) build = Integer.valueOf(line.split("=")[1]);
					line = fr.readLine();
				}
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		// else all version numbers are zero.
		String oldv = major + DOT + minor + DOT + build;

		// compute new version from command line arguments
		if (args.length==0)
			build++;
		else if (args.length==1) {
			if (args[0].equals("-major")) {
				major++;
				minor=0;
				build=0;
			}
			else if (args[0].equals("-minor")) {
				minor++;
				build=0;
			}
			else {
				System.out.println("Wrong arguments. Usage:");
				System.out.println("Setup -minor to re-generate scripts increasing minor version number");
				System.out.println("Setup -major to re-generate scripts increasing major version number");
				System.out.println("Setup with no argument will re-generate the scripts an increase build version number");
				System.exit(1);
			}
		}
		else {
			System.out.println("Usage:");
			System.out.println("Setup -minor to re-generate scripts increasing minor version number");
			System.out.println("Setup -major to re-generate scripts increasing major version number");
			System.out.println("Setup with no argument will re-generate the scripts an increase build version number");
			System.exit(1);
		}
		version.append(major).append(DOT).append(minor).append(DOT).append(build);

		// last chance to exit without harm
		System.out.print("Upgrading \""+ MODULE +
			"\" from version " + oldv +
			" to version " + version.toString()+" (Y/n)? ");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = br.readLine();
			if (s.startsWith("N")||s.startsWith("n")) {
				System.out.println("OK, OK. Why bother me if you don't want to do it? Aborting.");
				System.exit(0);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// archive old scripts by appending the current version number
		try {
			if (Paths.get(workDir,"ivy.xml").toFile().exists())
				Files.copy(Paths.get(workDir,"ivy.xml"),
					Paths.get(workDir,packageDir,"ivy-"+oldv+".xml"),
					REPLACE_EXISTING);
			if (Paths.get(workDir,"build.xml").toFile().exists())
				Files.copy(Paths.get(workDir,"build.xml"),
					Paths.get(workDir,packageDir,"build-"+oldv+".xml"),
					REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save new version into version file
		vfile = Paths.get(workDir, packageDir, "current-version.txt").toFile();
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(vfile));
			fw.write("//GENERATED - DO NOT EDIT THIS FILE"); fw.newLine();
			fw.write("MAJOR="+major); fw.newLine();
			fw.write("MINOR="+minor); fw.newLine();
			fw.write("BUILD="+build); fw.newLine();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// write new ivy.xml file
		File ivyFile = Paths.get(workDir, "ivy.xml").toFile();
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(ivyFile));
			fw.write(headComment1+version.toString()+headComment2+ivy1+version.toString()+ivy2+buildDependencyList()+ivy3);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// write new build.xml file
		File antFile = Paths.get(workDir, "build.xml").toFile();
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(antFile));
			fw.write(headComment1+version.toString()+headComment2+build1);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// that's all!
		System.out.println("Project scripts regenerated - Do not forget to refresh your eclipse workspace before going on.");
	}

}
