/**
 * <p>Defines additional {@link java.util.List List} types.</p>
 * <p>These classes are attempts to fix some issues with java lists:</p>
 * <ul>
 * <li>Iterators resistant to a modification of the list while looping on it (avoiding the dreadful
 * {@link java.util.ConcurrentModificationException ConcurrentModificationException}).</li>
 * <li>Iterator on a set of Lists (shallow merging, i.e. without copy of any list items).</li>
 * </ul>
 * 
 * @author Jacques Gignoux - 18 mai 2021
 *
 */
package au.edu.anu.omhtk.collections;