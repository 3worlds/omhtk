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
package au.edu.anu.rscs.aot.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * Utilities to manipulate files independent of OS.
 * 
 * @author Shayne Flint - 20/10/2014
 *
 */
// NOT TESTED
public class FileUtilities {

	/* deletes all files and directories include the root. */
	public static void deleteFileTree(File dir) throws IOException {
		Path root = dir.toPath();
		Files.walk(root).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		if (Files.exists(root))
			throw new OmhtkException("Failed to delete directory tree: [" + root + "]");
	}

	/* https://dzone.com/articles/comparing-files-in-java */
	public static boolean identicalStreams(BufferedInputStream fis1, BufferedInputStream fis2) throws IOException {
		int b1 = 0, b2 = 0;
		while (b1 != -1 && b2 != -1) {
			if (b1 != b2)
				return false;
			b1 = fis1.read();
			b2 = fis2.read();
		}
		if (b1 != b2)
			return false;
		else
			return true;
	}

	public static void copyFileReplace(File src, File dst) {
		try {
			if (!dst.getParentFile().exists())
				dst.getParentFile().mkdirs();
			Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new OmhtkException("FileUtilities.copyFileReplace: ",e);
		}
	}

	public static String makePath(String... pathElement) {
		String result = "";
		for (String s : pathElement) {
			if (s != null && s.length() > 0)
				if (result.length() > 0)
					result = result + File.separator + s;
				else
					result = s;
		}
		return result;
	}

	public static File makeFile(String... pathElements) {
		return new File(makePath(pathElements));
	}

	public static void catFile(File file) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
		} catch (Exception e) {
			throw new OmhtkException("FileUtils.catFile(): " + file);
		}
	}

	public static void catReader(Reader reader) { // was AotReader
		try {
			BufferedReader in = new BufferedReader(reader);
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
		} catch (Exception e) {
			throw new OmhtkException("FileUtils.catReader(): " + reader);
		}
	}

	public static void createPath(String... pathElements) {
		String path = makePath(pathElements);
		if (path.length() > 0) {
			File p = new File(path);
			if (!p.exists()) {
				p.mkdirs();
			}
		}
	}

	public static void createFile(File file, Object... lines) {
		try {
			String dir = file.getParent();
			createPath(dir);
			PrintWriter out = new PrintWriter(file);
			for (Object line : lines)
				out.println(line);
			out.close();
		} catch (Exception e) {
			throw new OmhtkException("FileUtils.makeFile(): " + file, e);
		}
	}

	public static String[] readFile(File file) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset());
		String[] result = lines.toArray(new String[lines.size()]);
		return result;
	}

	// QUICK TEST
	//
//	public static void main(String[] args) throws IOException {
//		File testFile = File.createTempFile("TestileUtils", "txt");
//		createFile(testFile, "Line 1", "Line 2", "Line 3", 67, 3.4);
//		catFile(testFile);
//	}

}
