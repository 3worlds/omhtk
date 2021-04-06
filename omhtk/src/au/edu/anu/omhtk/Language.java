package au.edu.anu.omhtk;

public class Language {
	private Language() {
	};

	private static final String lang = System.getProperty("user.language");
//	private static final String lang = "fr";
	
	public static String oq = "'";
	public static String cq = "'";
	static {
		if (French()) {
			oq = "«";
			cq = "»";
		} else if (Japanese()) {
			oq = "「";
			cq = "」";
		}
	}

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
