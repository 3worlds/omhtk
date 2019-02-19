package fr.cnrs.iees.versioning;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GenerateVersionManager {

	public static void main(String[] args) {
		String managerTemplate = System.getProperty("user.dir") // <home dir>/<eclipse workspace>/<project>
			+ File.separator + "src" 
			+ File.separator + GenerateVersionManager.class.getPackage().getName().replace('.',File.separatorChar) 
			+ File.separator + "VersionManager.java.template";
		File file = new File(managerTemplate);

		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (String s:lines)
				System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
