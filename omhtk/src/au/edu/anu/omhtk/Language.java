package au.edu.anu.omhtk;

public class Language {
	private Language() {
	};

	private static final String lang = System.getProperty("user.language");

	public static boolean French() {
		return lang.equals("fr");
	}

	public static boolean Japanese() {
		return lang.equals("jp");
	}

	public static boolean Chinese() {
		return lang.equals("cn");
	}

}
