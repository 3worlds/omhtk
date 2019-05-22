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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/

package au.edu.anu.omhtk.jars;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * <p>Utilities to handle jar files.</p>
 * 
 * @author Ian Davies - 19 Dec. 2018
 * 
 * <p>Re-implementation of  Shayne Flint's work refactored by Jacques Gignoux 2017</p>
 */
public class Jars {
	public static final char separatorChar = '/';
	public static final String separator = "" + separatorChar;

	private String mainClassName;
	private Set<String> dependsOnJars;

	public Jars() {
		dependsOnJars = new HashSet<>();
	}

	protected void setMainClass(String className) {
		this.mainClassName = className;
	}

	protected void addDependencyOnJar(String jarName) {
		dependsOnJars.add(jarName);
	}

	/**
	 * <p>
	 * Testing if the code where the klass argument was found originates from a jar - 
	 * hack found <a
	 * href=https://stackoverflow.com/questions/482560/can-you-tell-on-runtime-
	 * if-youre-running-java-from-within-a-jar> there</a>. The test is based on the
	 * existence of the manifest.
	 * </p>
	 * @param klass the class to search for
	 */
	public static String getRunningJarFilePath(Class<?> klass) {
		String result = null;
		try {
			result = new File(klass.getProtectionDomain().getCodeSource().getLocation().getPath()).toString();
			result = URLDecoder.decode(result, "UTF-8");
			ZipFile zipFile = new ZipFile(result);
			ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
			zipFile.close();
			return result;
		} catch (Exception exception) {
			return null;
		}
	}

}
