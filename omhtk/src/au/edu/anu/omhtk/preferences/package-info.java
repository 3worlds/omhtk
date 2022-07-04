/**
 * This package enhances {@link java.util.prefs.Preferences} to handle arrays
 * converted to Strings.
 * <p>
 * As implemented here {@link PrefImpl}, the backing store is ignored and the
 * data is saved to a specified file when flushed.
 * </p>
 * <p>
 * The intention is that preference data can be saved along with file specific
 * data for an application session so the appearance of the application is
 * stored with that particular session of which there can be any number. *
 * <p>
 * <img src="{@docRoot}/../doc/images/Preferences.svg" width="600" alt=
 * "Application preferences"/>
 * </p>
 * 
 * 
 * @author Ian Davies - Dec 11, 2018
 */
package au.edu.anu.omhtk.preferences;