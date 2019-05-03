package fr.cnrs.iees.versioning;

public class VersionSettings {
	
	// Change these fields to suit the project ====================================================
	
	/** The organisation name as will appear in the ivy module specification - it is a good idea
	 * to keep it consistent with the project src directory (although not required).*/
	protected static String ORG = "fr.ens.biologie";
	
	/** The name of the ivy module (this will be the name of the generated jar file for 
	 * dependent projects).*/
	protected static String MODULE = "generics";
	
	/** The ivy status of the module: integration, milestone, or release are the ivy defaults
	 * But we can define ours like bronze, gold, silver, or crap, supercrap, ultracrap. */
	protected static String STATUS = "integration";
	
	/** The license under which this module (= jar) is distributed */
	protected static String LICENSE = "gpl3";
	
	/**The url to the text of the license */
	protected static String LICENSE_URL = "https://www.gnu.org/licenses/gpl-3.0.txt";
	
	/**A (long) description of the ivy module */
	protected static String DESCRIPTION = 
		"This module contains generic, all-purpose, basic utilities for almost any java project.";
	
	/**
	 * <p>Dependencies on other modules (they will be integrated in the ivy script).</p>
	 * 
	 * <p>This is a (n * 4) table of Strings.<br/>
	 * Every line is a new dependency.
	 * On every line, the 4 Strings must match the ivy fields:
	 * <dl>
	 * <dt>org</dt> <dd>for <em>organisation</em></dd> 
	 * <dt>name</dt> <dd> for the module <em>name</em></dd>
	 * <dt>rev</dt> <dd>for the <em>revision</em> or version number. The '+' can
	 * be conveniently used to specify 'any version'.</dd>
	 * <dt>m:classifier</dt> <dd>for the <em>type of artifact</em> within the module. It's actually a 
	 * maven field, not an ivy filed. It enables to identify
	 * different artifacts such as source, javadoc, linux or windows specific packagings. This field
	 * is optional, put 'null' if not needed. Valid values are: <em>sources, javadoc, _os</em>.
	 * 'sources' and 'javadoc' will be used directly, '_os' will be replaced by the OS family (ie linux, mac or win)
	 *  - to match the needs of javafx components. For other libraries, you should check in the maven
	 * central repository the actual file names by clicking on the 'View All' button in the
	 * top table, on the 'Files' table entry</em></dd>
	 * </dl> 
	 * The field can be empty (just needs the external braces).<br/>
	 * Example value: 
	 * <pre>{{"org.galaxy.jupiter","crap","1.0.+"},
	 * {"org.ocean.lostIsland","strungk","3.12.254"}}</pre> </p>
	 * <p>Wildcards for revision numbers are indicated <a href="http://ant.apache.org/ivy/history/master/ivyfile/dependency.html">there</a>.</p>
	 * 
	 */
	protected static String[][] DEPS = { 
		{"commons-io", "commons-io", "1.+", null},
	};
	
	/** The name of the main class to put in the jar manifest, if any. This enables users to
	 * run the jar using this class as the entry point. Of course this must be a fully qualified
	 * valid java class name found in the jar. 
	 */
	protected static String MAINCLASS = null;

}
