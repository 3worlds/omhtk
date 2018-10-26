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
package fr.ens.biologie.generic;

import au.edu.anu.rscs.aot.OmhtkException;

/**
 * <p>For relatively complex objects that can return information with various levels of detail.
 * Taken from former Aot Node methods.</p>
 * <p>NOTE: classes implementing this interface <strong>must</strong> implement a 
 * {@code static public void valueOf(String)} method
 * returning an instance built from its argument. Don't forget that this method
 * can only be implemented in the class it is going to instantiate (ie no inheritance).</p>
 * 
 * @author gignoux - 25 août 2017
 *
 */
public interface Textable {
	
	/** Utility constants for generating blocks of saved data */
	/** item separators */
	public static char[] ITEM_SEPARATORS = 	  
		{',',';',':','.',' ','\t','\n','|','+','=','-','/','\\','_'};
	public static char COMMA = 		ITEM_SEPARATORS[0];
	public static char SEMICOLON = 	ITEM_SEPARATORS[1];
	public static char COLON = 		ITEM_SEPARATORS[2];
	public static char DOT = 		ITEM_SEPARATORS[3];
	public static char BLANK = 		ITEM_SEPARATORS[4];
	public static char TAB = 		ITEM_SEPARATORS[5];
	public static char LINEBREAK = 	ITEM_SEPARATORS[6];
	public static char VBAR = 		ITEM_SEPARATORS[7];
	public static char PLUS = 		ITEM_SEPARATORS[8];
	public static char EQUAL = 		ITEM_SEPARATORS[9];
	public static char MINUS = 		ITEM_SEPARATORS[10];
	public static char SLASH = 		ITEM_SEPARATORS[11];
	public static char BACKSLASH = 	ITEM_SEPARATORS[12];
	public static char UNDERSCORE =	ITEM_SEPARATORS[13];

	/** block delimiters (come in pairs) */
	public static char[][] BLOCK_DELIMITERS = 
		{{'{','}'},{'[',']'},{'(',')'},{'<','>'}};
	public static char[] BRACES = 				BLOCK_DELIMITERS[0];
	public static char[] SQUARE_BRACKETS = 		BLOCK_DELIMITERS[1];
	public static char[] BRACKETS = 			BLOCK_DELIMITERS[2];
	public static char[] TRIANGULAR_BRACKETS = 	BLOCK_DELIMITERS[3];
	
	/**
	 * Displays the object as simply as possible, showing its unicity as an instance.
	 * Typically, it should show the object address + some indication of the object
	 * content. Or if the object has a unique ID (e.g. graph elements), it should
	 * use it.
	 * 
	 * @return
	 */
//	public String toSimpleString(); // bad name
	public default String toUniqueString() {
		// Typically, this method shoud return Object.toString(), which returns the
		// object reference. But this cannot be made the default behaviour in
		// an interface because super is not available in interfaces
		throw new OmhtkException("Unimplemented method: toUniqueString()");
	}

	/**
	 * Displays the object as simply as possible, with no guarantee that another 
	 * object will not return the same string. <br/>
	 * The returned description must fit on one single, short (~20 character), line
	 * of text.
	 * @return
	 */
	public default String toShortString() {
		return toString();
	}

	/**
	 * Displays the object with all the detailed of its content, for screen or console
	 * display purpose.
	 * (ex. for AOT Nodes: show all properties + list of edge ids)
	 * @return
	 */
//	public String toLongString(); // bad name
	public default String toDetailedString() {
		return toUniqueString()+" "+toString();
	}
	
	/**
	 * <p>Produces a String that can be saved to a text file and later re-loaded.</p>
	 * <p>This method (and all its variants) must return a {@code String} that allows to
	 * reconstruct the object from it using a {@code valueOf(...)} method.</p>
	 * <p>NOTE: the return String should NOT start and end with a block delimiter.
	 * This should only be done by the container of this object (which will know if block
	 * delimiters or separators should be used).</p>
	 * 
	 * @param blockDelimiters an array of pairs of block delimiters 
	 * (the second dimension of the array must be 2, with item 0 being the opening 
	 * block delimiter (e.g. '[') and the item 1 being the closing block delimiter
	 * (e.g. ']').
	 * @param itemSeparators an array of separators between repeated items
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char[][] blockDelimiters, char[] itemSeparators) {
		throw new OmhtkException("Unimplemented method: toSaveableString(char[][] blockDelimiters,char[] itemSeparators)");
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with no block delimiters.
	 * @param itemSeparators an array of separators between repeated items
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char[] itemSeparators) {
		return toSaveableString(null,itemSeparators);
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with no separators
	 * @param blockDelimiters an array of pairs of block delimiters 
	 * (the second dimension of the array must be 2, with item 0 being the opening 
	 * block delimiter (e.g. '[') and the item 1 being the closing block delimiter
	 * (e.g. ']').
	 * @return  the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char[][] blockDelimiters) {
		return toSaveableString(blockDelimiters,null);
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with no separators
	 * nor delimiters.
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString() {
		return toSaveableString(null,null);
	}
	
	/**
	 * As {@code toSaveableString(char[][],char[])}, but with only one pair of block
	 * delimiters and one item separator
	 * @param blockDelimiters an array of 2 chars with item 0 being the opening 
	 * block delimiter (e.g. '<') and the item 1 being the closing block delimiter
	 * (e.g. '>').
	 * @param itemSeparator a separator between repeated items
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char[] blockDelimiters, char itemSeparator) {
		char[][] bd = new char[1][];
		bd[0] = blockDelimiters;
		char[] is = new char[1];
		is[0] = itemSeparator;
		return toSaveableString(bd,is);
	}
	
	/**
	 * As {@code toSaveableString(char[][],char[])}, but with only one pair of block
	 * delimiters and one item separator
	 * @param startBlockDelimiter the opening block delimiter
	 * @param endBlockDelimiter the closing block delimiter
	 * @param itemSeparator the item separator
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char startBlockDelimiter, char endBlockDelimiter, char itemSeparator) {
		char[][] bd = new char[1][2];
		bd[0][0] = startBlockDelimiter;
		bd[0][1] = endBlockDelimiter;
		return toSaveableString(bd,null);
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with only one pair of block
	 * delimiters and no item separator
	 * @param startBlockDelimiter the opening block delimiter
	 * @param endBlockDelimiter the closing block delimiter
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	
	public default String toSaveableString(char startBlockDelimiter, char endBlockDelimiter) {
		char[][] bd = new char[1][2];
		bd[0][0] = startBlockDelimiter;
		bd[0][1] = endBlockDelimiter;
		return toSaveableString(bd,null);
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with no block
	 * delimiters and one item separator
	 * @param itemSeparator the item separator
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */

	public default String toSaveableString(char itemSeparator) {
		char[] is = new char[1];
		is[0] = itemSeparator;
		return toSaveableString(null,is);
	}


	/**
	 * This is the inherited method from {@code Object}.
	 * Keep it simple as it is used by the debugger to show object content
	 * Suggestion is to make it return one of the other methods depending
	 * on what debugging level is required (e.g. toUniqueString() when instance
	 * identity is important, toDetailedString() when everything is important,
	 * toShortString() when not much is important).
	 * 
	 * @return
	 */
//	public String toString();
	
}
