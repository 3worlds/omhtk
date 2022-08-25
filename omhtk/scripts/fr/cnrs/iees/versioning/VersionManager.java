package fr.cnrs.iees.versioning;

/**********************************************************************************************
 *                                                                                            *
 *   GENERATED FILE - DO NOT EDIT !                                                           * 
 *                                                                                            *
 *   if you need to modify this file, use the GenerateVersionManager class in library omhtk   *
 *                                                                                            *
 **********************************************************************************************/

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
import static fr.cnrs.iees.versioning.VersionSettings.*;

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
	"\t\txmlns:m=\"http://maven.apache.org/POM/4.0.0\"\n" +
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
	"<project xmlns:ivy=\"antlib:org.apache.ivy.ant\" basedir=\"..\" default=\"publishJar\">\n\n" +

	"\t<!-- Artifacts are produced in the project lib directory-->\n" +
	"\t<property name=\"jarRepo\" location=\"${user.dir}/../lib\"/>\n\n" +
	"\t<property name=\"docRepo\" location=\"${user.dir}/../javadoc\"/>" +

	"\t<!-- Use this string as the organisation package -->\n" +
	"\t<property name=\"org\" value=\"" + ORG +"\"/>\n\n" +

	"\t<!-- The project name -->\n" +
	"\t<property name=\"project\" value=\""+ MODULE +"\"/>\n\n" +

	"\t<!-- Append the project name to the organisation string -->\n" +
	"\t<property name=\"jarlib\" location=\"${jarRepo}/${org}.${project}\"/>\n\n" +

	"\t<!-- Tasks -->\n" +
	"\t<target name=\"makeArtifactDir\" description=\"create directory for jars and doc\">\n" +
	"\t\t<mkdir dir=\"${jarlib}\"/>\n" +
	"\t\t<mkdir dir=\"${docRepo}\"/>" +
	"\t</target>\n\n" +

	"\t<target name=\"cleanDoc\" description=\"cleanup javadoc directory\">\n" + 
	"\t\t<delete dir=\"${docRepo}\"/>\n" + 
	"\t</target>\n" + 

	"\t<target name=\"resolve\" description=\"resolve dependencies and setup publication environment\">\n" +
	"\t\t<ivy:configure/>\n" +
	"\t\t<ivy:resolve file=\"scripts/${ivy.dep.file}\" conf=\"${ivy.configurations}\"/>\n" +
	"\t\t<ivy:retrieve pattern=\"${ivy.retrieve.pattern}\" conf=\"${ivy.configurations}\"/>\n" +
	"\t</target>\n\n" +

	"\t<target name=\"makeJar\" description=\"pack as a jar library\" depends=\"makeArtifactDir,resolve\">\n" +
	"\t\t<jar destfile=\"${jarlib}/${project}.jar\">\n" + 
	"\t\t\t<fileset dir=\"bin\"> \n" + 
	"\t\t\t\t<exclude name=\"**/*Version*.*\"/>\n" + 
	"\t\t\t\t<exclude name=\"**/LicenseManager.*\"/>\n" + 
	"\t\t\t\t<exclude name=\"**/current-version.txt\"/>\n" + 
	"\t\t\t\t<exclude name=\"**/*Test.class\"/>\n" + 
	"\t\t\t\t<exclude name=\"**/*.xml\"/>\n" + 
	"\t\t\t\t</fileset>\n" + 
	"\t\t\t<fileset dir=\"src\"/>\n" + 
	"\t\t\t<manifest>\n" + 
	"\t\t\t\t<attribute name=\"Implementation-Vendor\" value=\"CNRS/ANU\"/>\n" + 
	"\t\t\t\t<attribute name=\"Implementation-Title\" value=\"" + ORG + "\"/>\n" + 
	"\t\t\t\t<attribute name=\"Implementation-Version\" value=\"";
	// here comes the version number
	private static String build2 = "\"/>\n" + 
	"\t\t\t\t<attribute name=\"Built-By\" value=\"${user.name}\"/>\n ";
	// here comes the main class, if not null
	// Nb in the future there may be a classpath attribute: Class-Path: .3w/tw-dep.jar .3w/threeWorlds.jar
	private static String build3 =
	"\t\t\t</manifest>\n" + 
	"\t\t</jar>\n" + 
	"\t</target>\n\n" +

	"\t<target name=\"publishJar\" description=\"make jar library available to others\" depends=\"makeJar\">\n" +
	"\t\t<ivy:publish resolver=\"local\" overwrite=\"true\"  forcedeliver=\"true\">\n" +
	"\t\t\t<artifacts pattern=\"${jarlib}/[artifact].[ext]\"/>\n" +
	"\t\t</ivy:publish>\n" +
	"\t</target>\n\n" +

	"\t<target name=\"javadoc\" description=\"generate JavaDoc\" depends=\"makeArtifactDir,cleanDoc\">\n" + 
	"\t\t<javadoc destdir=\"${docRepo}\">\n" + 
	"\t\t\t<fileset dir=\"src\"/>\n" + 
	"\t\t</javadoc>\n" + 
	"\t</target>\n" + 

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
					.append("\"");
				if (DEPS[i][3]!=null) {
					if (DEPS[i][3].equals("_os")) {
						// this is a stupid joke - "os" in French means "bone" in English
						String bone = System.getProperty("os.name").toLowerCase();
						String os = "src"; // by default will search for source files
						if (bone.indexOf("win")>=0)
							os = "win";
						else if (bone.indexOf("linux")>=0)
							os = "linux";
						else if (bone.indexOf("mac")>=0)
							os = "mac";
						sb.append(" m:classifier=\"")
							.append(os)
							.append("\"");
					}
					else
						sb.append(" m:classifier=\"")
							.append(DEPS[i][3])
							.append("\"");
				}
				sb.append("/>\n");
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
			fw.write(headComment1+version.toString()+
				headComment2+ivy1+version.toString()+
				ivy2+buildDependencyList()+
				ivy3);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// write new build.xml file
		File antFile = Paths.get(workDir, "build.xml").toFile();
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(antFile));
			String mainClass =""; 
			if (MAINCLASS!=null) 
				mainClass = "\t\t\t\t<attribute name=\"Main-Class\" value=\"" + MAINCLASS + "\"/>\n";
			fw.write(headComment1+version.toString()+
				headComment2+build1+version.toString()+
				build2+mainClass+
				build3);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// that's all!
		System.out.println("Project scripts regenerated - Do not forget to refresh your eclipse workspace before going on.");
	}

}
