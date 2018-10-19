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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static java.nio.file.StandardOpenOption.*;

/**
 * Utility to copy the license header at the top of source files.
 * 
 * CAUTION when editing this file - it may wipe out or trash your whole project !
 * 
 * @author Jacques Gignoux - Oct. 2018
 *
 */
public class LicenseManager {
	
	private static String licenseFile = "license-header.txt";
	private static String workDir = System.getProperty("user.dir");
	private static int n;

	// nice method copied from there: 
	// https://stackoverflow.com/questions/2056221/recursively-list-files-in-java
    private static void walk( String path ) {
        File root = new File( path );
        File[] list = root.listFiles();
        if (list == null) return;
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walk( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.print( "File:" + f.getAbsoluteFile() );
                addLicenseHeader(f);
            }
        }
    }
	
    private static void addLicenseHeader(File file) {
    	if (file.getName().endsWith(".java")) {
			try {
				List<String> fcontent = Files.readAllLines(file.toPath());
				if (fcontent.get(0).startsWith("package")) {
					List<String> ltext = Files.readAllLines(Paths.get(workDir,"doc","license",licenseFile));
					Files.write(file.toPath(), ltext, TRUNCATE_EXISTING);
					Files.write(file.toPath(), fcontent, APPEND);
					System.out.println(" updated with license information.");
					n++;
				}
				else
					System.out.println(" unchanged.");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else
    		System.out.println(" - not a java file");
    }
	
	public static void main(String[] args) {
		n=0;
		System.out.println("ATTACHING LICENSE INFORMATION HEADER TO SOURCE FILES...");
		walk(Paths.get(workDir,"src").toString());
		walk(Paths.get(workDir,"test").toString());
		walk(Paths.get(workDir,"scripts").toString());
		if (n<2)
			System.out.println(n+" source file updated\nEND.");
		else
			System.out.println(n+" source files updated\nEND.");
	}

}
