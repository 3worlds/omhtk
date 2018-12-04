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

import java.io.File;

import au.edu.anu.rscs.aot.util.ExecutionResults;
import au.edu.anu.rscs.aot.util.FileUtilities;

/**
 * 
 * @author Shayne Flint - before 27/2/2012
 *
 */
// NOT TESTED
public abstract class Environment {

//	private Logger log = LoggerFactory.getLogger(Environment.class);

	public abstract ExecutionResults execute(String command, String workingDirectory);

	public ExecutionResults execute(String command) {
		return execute(command, null);
	}

	public void run(String command, String workingDirectory) {
		ExecutionResults results = execute(command, workingDirectory);
		results.check();
	}

	public void run(String command) {
		run(command, null);
	}

	public abstract void chDir(String path);
	public abstract void pushDir(String newDir);
	public abstract void pushHome();
	public abstract void popDir();
	public abstract String cwd();
	
	public abstract boolean isFile(String path);
	public abstract void cpFile(String source, String target);
	public abstract void symLink(String source, String target);
	public abstract void rmFile(String path);
	public abstract void removeOnExit(String filename);
	public abstract void rmTree(String path);
	public abstract void mkDirs(String path);
	public abstract void catFile(File file);

	public abstract String hostName();
	public abstract String userName();
	public abstract String userHome();
	public abstract String osName();
	public abstract String osVersion();
	public abstract String osArch();
	public abstract String javaVersion();
	public abstract String javaClassPath();

	public String platform() {
		return osName() + " [" + osArch() + "] running Java " + javaVersion();
	}

	public void cpFile(File source, File target) {
		cpFile(source.getAbsolutePath(), target.getAbsolutePath());
	}


	public String toString() {
		return "userName:" + userName()
		+ ", userHome:" + userHome()
		+ ", osName:" + osName()
		+ ", osversion:" + osVersion()
		+ ", osArch:" + osArch()
		+ ", javaversion:" + javaVersion()
		+ ", javaClassPath:" + javaClassPath();
	}


	public String makeAbsolutePath(String... pathElement) {
		String result = makePath(pathElement);
		File f = new File(result);
		if (!f.isAbsolute())
			result = cwd() + File.separator + result;
		//log.debug("Environment.makeAbsolutePath {}", elementString(pathElement) + " -> " + result);
		return result;
	}

//	private String elementString(String... pathElement) {
//		String result = "[";
//		for (String s : pathElement) {
//			result = result + s + ", ";
//		}
//		return result + "]";
//	}

	public String makePath(String... pathElements) {
		return FileUtilities.makePath(pathElements);
	}

	public File makeFile(String... pathElement) {
		return new File(makeAbsolutePath(pathElement));
	}

	
}
