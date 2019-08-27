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
package au.edu.anu.rscs.aot.environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import au.edu.anu.rscs.aot.OmhtkException;
import au.edu.anu.rscs.aot.util.ExceptionString;
import au.edu.anu.rscs.aot.util.ExecutionResults;

/**
 * 
 * @author Shayne Flint - before 27/2/2012
 *
 * <p>NOTE (JG - 2018): I've replaced the aot logging system by java logging classes for simplicity.</p>
 *
 */
// NOT TESTED
public class LocalEnvironment extends Environment {

	private static Logger log = Logger.getLogger(LocalEnvironment.class.getName());

	public static LocalEnvironment env = new LocalEnvironment("local");

	public String hostName = "Unknown Host";

	public LocalEnvironment(String rootDir) {
		dirStack.push(userHome());
		
		File aotDir = new File(userHome() + File.separator + rootDir);
		if (!aotDir.exists()) {
			aotDir.mkdirs();
		}

		try {
			Runtime run = Runtime.getRuntime();
			Process proc;
			proc = run.exec("hostname");
			BufferedInputStream in = new BufferedInputStream(
					proc.getInputStream());
			byte[] bytes = new byte[256];
			in.read(bytes);
			hostName = new String(bytes).trim();
		} catch (IOException e) {
			hostName = "Unknown Host";
		}
	}

	// The concept of 'current working directory' is managed here - not by the
	// host OS (because Java can't do it)
	//

	private Stack<String> dirStack = new Stack<String>();

	@Override
	public void chDir(String path) {
		log.entering("LocalEnvironment", "chDir");  
		log.fine(path);
		dirStack.pop();
		pushDir(path);
	}

	@Override
	public void pushDir(String newDir) {
		log.entering("LocalEnvironment", "pushDir");  
		log.fine(newDir);
		dirStack.push(newDir);
	}

	@Override
	public void pushHome() {
		log.entering("LocalEnvironment", "pushHome");  
		dirStack.push(userHome());
	}

	@Override
	public void popDir() {
		log.entering("LocalEnvironment", "popDir");  
		if (dirStack.size() > 1)
			dirStack.pop();
		else
			throw new OmhtkException("Illegal attempt to pop cwd from dirStack");
	}

	@Override
	public String cwd() {
		log.entering("LocalEnvironment", "cwd");  
		log.fine(dirStack.peek());
		return dirStack.peek();
	}

	// All file operations work with above directory management - also see
	// makePath and makeFile in Environment
	//

	@Override
	public boolean isFile(String path) {
		File f = makeFile(path);
		return f.exists();
	}

	@Override
	public void cpFile(String source, String target) {
		try {
			FileUtils.copyFile(new File(source), new File(target));
		} catch (IOException e) {
			throw new OmhtkException("LocalEnvironment.copyFile", e);
		}
//		String command = "";
//		if (osName().contains("Window"))
//			command = "cmd /c copy ";
//		else
//			command = "cp ";
//		command += makeAbsolutePath(source) + " " + makeAbsolutePath(target);
//		run(command);
	}

	@Override
	public void symLink(String source, String target) {
		// cmd /c mklink
		String command = null;
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			// will hard links crash without admin credentials on windows??
			command = "cmd /c mklink /H";
		else
			command = "ln -s ";
		command += makeAbsolutePath(source) + " " + makeAbsolutePath(target);
		run(command);
	}

	@Override
	public void rmFile(String path) {
		log.entering("LocalEnvironment", "rmFile");  
		log.fine(path);
		makeFile(path).delete();
	}

	@Override
	public void removeOnExit(String filename) {
		File file = new File(filename);
		file.deleteOnExit();
	}

	@Override
	public void rmTree(String path) {
//		log.entering("LocalEnvironment", "rmTree");  
//		log.fine(path);
//		try {
//			FileUtils.deleteDirectory(makeFile(path));
//		} catch (IOException e) {
//			throw new OmhtkException(e);
//		}
	}

	@Override
	public void mkDirs(String path) {
		log.entering("LocalEnvironment", "mkDirs");  
		log.fine(path);
		makeFile(path).mkdirs();
	}

	@Override
	public void catFile(File file) {
		String command = "cat " + file.getAbsolutePath();
		ExecutionResults results = execute(command, null);
		System.out.println(results.toString());
	}

	// System properties

	@Override
	public String hostName() {
		return hostName;
	}

	@Override
	public String userName() {
		return System.getProperty("user.name");
	}

	@Override
	public String userHome() {
		return System.getProperty("user.home");
	}

	@Override
	public String osName() {
		return System.getProperty("os.name");
	}

	@Override
	public String osVersion() {
		return System.getProperty("os.version");
	}

	@Override
	public String osArch() {
		return System.getProperty("os.arch");
	}

	@Override
	public String javaVersion() {
		return System.getProperty("java.version");
	}

	@Override
	public String javaClassPath() {
		return System.getProperty("java.class.path");
	}

	@Override
	public String platform() {
		return osName() + " [" + osArch() + "] running Java " + javaVersion();
	}

	// Execute external processes

	@Override
	public ExecutionResults execute(String command, String workingDirectory) {
		LinkedList<String> results = new LinkedList<String>();
		Process proc;
		try {
			if (workingDirectory == null) {
				proc = Runtime.getRuntime().exec(command);
			} else
				proc = Runtime.getRuntime().exec(command, null,
						new File(workingDirectory));

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line;
			while ((line = stdInput.readLine()) != null) {
				System.out.println(line);
				results.add("[inputStream] " + line);
			}
			stdInput.close();

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					proc.getErrorStream()));
			while ((line = stdError.readLine()) != null) {
				results.add("[errorStream] " + line);
			}
			stdError.close();

			int exitCode = proc.waitFor();
			proc.destroy();
			log.exiting("LocalEnvironment", "execute");  
			log.fine("'" + command + "', exited with code " + exitCode);

			return new ExecutionResults(command, exitCode, results);
		} catch (Exception e) {
			return new ExecutionResults(command,
					ExecutionResults.ABNORMAL_EXIT,
					ExceptionString.exceptionString(e));
		}
	}

	// TESTING
	//

//	public static void main(String[] args) throws Exception {
//
//		Logger log = LoggerFactory.getLogger(LocalEnvironment.class,
//				"test main");
//
//		log.debug("Environment {}", env);
//
//		log.debug("cwd() = {}", env.cwd());
//
//		ExecutionResults res = env.execute("ls -al", "/tmp");
//		log.debug(res.toString());
//		env.run("badCommand");
//	}

}
