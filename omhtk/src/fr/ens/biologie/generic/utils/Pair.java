package fr.ens.biologie.generic.utils;

/**
 * A kind of clone of javafx.util.Pair which is not available in OpenJDK but in Oracle JDK only.
 * cf bug #11 for details on why I implemented this class
 * 
 * @author Jacques Gignoux - 18 avr. 2019
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K,V> {
	
	private K key;
	private V value;
	
	public Pair(K k, V v) {
		super();
		key = k;
		value = v;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(key).append("=").append(value);
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(java.lang.Object o) {
		if (o==null)
			return false;
		try {
			Pair<K,V> p = (Pair<K,V>) o;
			boolean equalKeys = false;
			if (key==null)
				if (p.key==null)
					equalKeys = true;
				else
					return false;
			else 
				if (p.key==null)
					return false;
				else
					equalKeys = key.equals(p.key);
			boolean equalValues = false;
			if (value==null)
				if (p.value==null)
					equalValues = true;
				else 
					return false;
			else
				if (p.value==null)
					return false;
				else
					equalValues = value.equals(p.value);
			return equalKeys && equalValues;
		} catch (Exception e) {
			return false;
		}
	}
}
