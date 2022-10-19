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
package fr.cnrs.iees.omhtk.codeGeneration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian Davies
 * 
 *         Date 08 Jul. 2021
 *
 */
public class JavaUtilities {
	private JavaUtilities() {
	};

	/**
	 * TODO used in node renaming to update references to this node in other java
	 * files.
	 * 
	 * @param f          The file to examine.
	 * @param oldElement Current string in file.
	 * @param newElement New string in file.
	 * @throws IOException File reads or attempt to process a non-java file.
	 */
	public static void updatePackgeEntry(File f, String oldElement, String newElement) throws IOException {
		if (!f.getName().contains(".java"))
			throw new IllegalArgumentException("Cannot change package name in non-java files. [" + f.getAbsolutePath() + "]");
		List<String> lines = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
		List<String> newLines = new ArrayList<>();
		for (String line : lines) {
			if (line.contains("package") && line.contains(oldElement) && line.endsWith(";")) {
				line = line.replace(oldElement, newElement);
			}
			newLines.add(line);
		}
		Files.write(f.toPath(), newLines, StandardOpenOption.TRUNCATE_EXISTING);
	}

}
