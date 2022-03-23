/**
 * This package extends {@link java.util.prefs.Preferences} to handle arrays
 * converted to Strings so the data can be visually examined in a text editor
 * and to avoid converting byte arrays to arrays of doubles, ints etc. As
 * implemented here {@link PrefImpl}, the backing store is ignored and the data
 * is saved to a specified file when flushed. The intention is that preference
 * data can be saved along with file specific data for an application session
 * i.e a particular project so the appearance of the application is stored with
 * that particular session of which there can be any number.
 * 
 * @author Ian Davies - Dec 11, 2018
 */
package au.edu.anu.omhtk.preferences;