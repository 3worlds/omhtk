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
package au.edu.anu.rscs.aot.environment;

import java.io.File;

import au.edu.anu.rscs.aot.util.ExecutionResults;
import au.edu.anu.rscs.aot.util.FileUtilities;

/**
 * A class to record the running environment of a java application, e.g.
 * operating system, user name, number of CPUs, current directory, etc. Most of
 * the file operations are now redundant with the
 * {@link org.apache.commons.io.FileUtils} package. It may be used to represent
 * a local environment or a remote environment, for applications running
 * processes on multiple computers.
 * 
 * @author Shayne Flint - before 27/2/2012
 */
// NOT TESTED
public abstract class Environment {

	/**
	 * Execute an operating system command.
	 * 
	 * @param command          the command to execute
	 * @param workingDirectory the working directory from where the command is
	 *                         executed
	 * @return the result of the execution
	 */
	public abstract ExecutionResults execute(String command, String workingDirectory);

	/**
	 * Execute an operating system command in the current directory.
	 * 
	 * @param command the command to execute
	 * @return the result of the execution
	 */
	public ExecutionResults execute(String command) {
		return execute(command, null);
	}

	/**
	 * Execute an operating system command and throws an Exception if an error
	 * occurred during execution.
	 * 
	 * @param command          the command to execute
	 * @param workingDirectory the working directory from where the command is
	 *                         executed
	 */
	public void run(String command, String workingDirectory) {
		ExecutionResults results = execute(command, workingDirectory);
		results.check();
	}

	/**
	 * Execute an operating system command in the current directoryand throws an
	 * Exception if an error occurred during execution.
	 * 
	 * @param command the command to execute
	 */
	public void run(String command) {
		run(command, null);
	}

	/**
	 * Change directory.
	 * 
	 * @param path the path of the directory to change to
	 */
	public abstract void chDir(String path);

	/**
	 * Set the current working directory.
	 * 
	 * @param newDir the new directory to set as current
	 */
	public abstract void pushDir(String newDir);

	/** Set the current working directory to the user home directory. */
	public abstract void pushHome();

	/** Remove the current working directory. */
	public abstract void popDir();

	/**
	 * Get the current working directory name
	 * 
	 * @return the directory name
	 */
	public abstract String cwd();

	/**
	 * Test if a path {@code String} is a valid file name and creates it if doesnt.
	 * 
	 * @param path the file name
	 * @return {@code true}
	 */
	public abstract boolean isFile(String path);

	/**
	 * Copy a file.
	 * 
	 * @param source the file to copy
	 * @param target the destination to copy to
	 */
	public abstract void cpFile(String source, String target);

	/**
	 * Create a symbolic link to a file / directory.
	 * 
	 * @param source the name of the new link
	 * @param target the file / directory pointed at by this link
	 */
	public abstract void symLink(String source, String target);

	/**
	 * Delete a file.
	 * 
	 * @param path the file to delete
	 */
	public abstract void rmFile(String path);

	/**
	 * Delete a file when the application closes. Do NOT use for locking!
	 * 
	 * @param filename the file to delete
	 */
	public abstract void removeOnExit(String filename);

	/**
	 * Delete a directory tree
	 * 
	 * @param path the root of the directory tree to delete
	 */
	public abstract void rmTree(String path);

	/**
	 * Create a tree of nested directories as specified by its argument in the OS
	 * file system.
	 * 
	 * @param path the hierarchy of directories to create.
	 */
	public abstract void mkDirs(String path);

	/**
	 * Returns the content of an existing file to the output console.
	 * 
	 * @param file the file to read
	 */
	public abstract void catFile(File file);

	/**
	 * Get the computer host name at the time of instantiation of this class.
	 * 
	 * @return the host name
	 */
	public abstract String hostName();

	/**
	 * Get the current user name.
	 * 
	 * @return the user name
	 */
	public abstract String userName();

	/**
	 * Get the current user home directory as an OS-dependent String.
	 * 
	 * @return the user home directory
	 */
	public abstract String userHome();

	/**
	 * Get the current operating system name
	 * 
	 * @return the OS name
	 */
	public abstract String osName();

	/**
	 * @return Get the current operating system version
	 */
	public abstract String osVersion();

	/**
	 * @return Get the current operating system architecture
	 *
	 */
	public abstract String osArch();

	/**
	 * @return Get the current running version of java
	 * 
	 */
	public abstract String javaVersion();

	/**
	 * Get the current runtime java class path property
	 * 
	 * @return the java class path
	 */
	public abstract String javaClassPath();

	/**
	 * @return the current running environment (operating system name and
	 *         architecture and java version).
	 */
	public String platform() {
		return osName() + " [" + osArch() + "] running Java " + javaVersion();
	}

	/**
	 * Copy a file.
	 * 
	 * @param source the file to copy
	 * @param target the destination to copy to
	 */
	public void cpFile(File source, File target) {
		cpFile(source.getAbsolutePath(), target.getAbsolutePath());
	}

	@Override
	public String toString() {
		return "userName:" + userName() + ", userHome:" + userHome() + ", osName:" + osName() + ", osversion:"
				+ osVersion() + ", osArch:" + osArch() + ", javaversion:" + javaVersion() + ", javaClassPath:"
				+ javaClassPath();
	}

	/**
	 * Constructs a path name from its arguments and test if it represents an
	 * absolute path name in the operating system. If not, set it into the current
	 * working directory.
	 * 
	 * @param pathElement a list of path elements (i.e. OS-compatible names)
	 * @return the full name of the created file / directory
	 */
	public String makeAbsolutePath(String... pathElement) {
		String result = makePath(pathElement);
		File f = new File(result);
		if (!f.isAbsolute())
			result = cwd() + File.separator + result;
		// log.debug("Environment.makeAbsolutePath {}", elementString(pathElement) + "
		// -> " + result);
		return result;
	}

	/**
	 * Constructs a OS-independent path from its arguments. Has no effect on the
	 * file system, i.e. this is just String manipulation. Ignores empty and
	 * {@code null} Strings in its arguments.
	 * 
	 * @param pathElements a list of path elements (i.e. OS-compatible names)
	 * @return the full path name
	 */
	public String makePath(String... pathElements) {
		return FileUtilities.makePath(pathElements);
	}

	/**
	 * Constructs a path name from its arguments and test it is a valid file /
	 * directory name for the operating system.
	 * 
	 * @param pathElement a list of path elements (i.e. OS-compatible names)
	 * @return the created file / directory
	 */
	public File makeFile(String... pathElement) {
		return new File(makeAbsolutePath(pathElement));
	}

}
