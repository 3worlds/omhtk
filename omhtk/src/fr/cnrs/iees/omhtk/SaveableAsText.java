/**************************************************************************
 *  OMHTK - One More Handy Tool Kit                                       *
 *                                                                        *
 *  Copyright 2021: Shayne R. Flint, Jacques Gignoux & Ian D. Davies      *
 *       shayne.flint@anu.edu.au                                          *
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  OMHTK is a bunch of useful, very generic interfaces for designing     *
 *  consistent class hierarchies, plus some other utilities. The kind of  *
 *  things you need in all software projects and keep rebuilding all the  * 
 *  time.                                                                 *
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
 *  along with OMHTK.
 *  If not, see <https://www.gnu.org/licenses/gpl.html>.                  *
 *                                                                        *
 **************************************************************************/
package fr.cnrs.iees.omhtk;

/**
 * <p>An interface for objects that can be saved as text (in text files or Strings) and later
 * re-loaded with a (static) {@code valueOf()} method.</p>
 *
 * <p>NOTE: classes implementing this interface <strong>must</strong> implement a
 * {@code static public void valueOf(String)} method
 * returning an instance built from its argument. Don't forget that this method
 * can only be implemented in the class it is going to instantiate (i.e. no inheritance).</p>
 *
 * @author Jacques Gignoux - 8 nov. 2018
 *
 */
public interface SaveableAsText {

	/** Utility constants for generating blocks of saved data */
	/** item separators */
	public static char[] ITEM_SEPARATORS =
		{',',';',':','.',' ','\t','\n','|','+','=','-','/','\\','_','\"','\''};
	/** item separator {@code ,} */
	public static char COMMA = 		ITEM_SEPARATORS[0];
	/** item separator {@code ;} */
	public static char SEMICOLON = 	ITEM_SEPARATORS[1];
	/** item separator {@code :} */
	public static char COLON = 		ITEM_SEPARATORS[2];
	/** item separator {@code .} */
	public static char DOT = 		ITEM_SEPARATORS[3];
	/** item separator ' ' */
	public static char BLANK = 		ITEM_SEPARATORS[4];
	/** item separator {@code \t} */
	public static char TAB = 		ITEM_SEPARATORS[5];
	/** item separator {@code \n} */
	public static char LINEBREAK = 	ITEM_SEPARATORS[6];
	/** item separator {@code |} */
	public static char VBAR = 		ITEM_SEPARATORS[7];
	/** item separator {@code +} */
	public static char PLUS = 		ITEM_SEPARATORS[8];
	/** item separator {@code =} */
	public static char EQUAL = 		ITEM_SEPARATORS[9];
	/** item separator {@code -} */
	public static char MINUS = 		ITEM_SEPARATORS[10];
	/** item separator {@code /} */
	public static char SLASH = 		ITEM_SEPARATORS[11];
	/** item separator {@code \} */
	public static char BACKSLASH = 	ITEM_SEPARATORS[12];
	/** item separator {@code _} */
	public static char UNDERSCORE =	ITEM_SEPARATORS[13];
	/** item separator {@code "} */
	public static char DOUBLEQUOTE= ITEM_SEPARATORS[14];
	/** item separator {@code '} */
	public static char SINGLEQUOTE= ITEM_SEPARATORS[15];
	//IDD - can't use as block delim for quotes because they're the same char

	/** block delimiters (come in pairs) */
	public static char[][] BLOCK_DELIMITERS =
		{{'{','}'},{'[',']'},{'(',')'},{'<','>'}};
	/** indexes to the above */
	/** index of an opening block delimiter */
	public static int BLOCK_OPEN = 0;
	/** index of a closing block delimiter */
	public static int BLOCK_CLOSE = 1;
	/** block delimiters {} */
	public static char[] BRACES = 				BLOCK_DELIMITERS[0];
	/** block delimiters [] */
	public static char[] SQUARE_BRACKETS = 		BLOCK_DELIMITERS[1];
	/** block delimiters () */
	public static char[] BRACKETS = 			BLOCK_DELIMITERS[2];
	/** block delimiters &lt;&gt; */
	public static char[] TRIANGULAR_BRACKETS = 	BLOCK_DELIMITERS[3];

	/** string delimiters (come in pairs) */
	public static char[][] STRING_DELIMITERS =
		{{'\"','\"'},{'\'','\''},{'«','»'},{'“','”'},{'‘','’'},{'„','“'}};
	/** index of an opening string delimiter */
	public static int STRING_OPEN = 0;
	/** index of a closing string delimiter */
	public static int STRING_CLOSE = 1;
	/** string delimiters " " */
	public static char[] DOUBLE_QUOTES = 		STRING_DELIMITERS[0];
	/** string delimiters ' ' */
	public static char[] SINGLE_QUOTES = 		STRING_DELIMITERS[1];
	/** string delimiters « » */
	public static char[] FRENCH_QUOTES = 		STRING_DELIMITERS[2];
	/** string delimiters “ ” */
	public static char[] AMERICAN_QUOTES = 		STRING_DELIMITERS[3];
	/** string delimiters ‘ ’ */
	public static char[] ENGLISH_QUOTES = 		STRING_DELIMITERS[4];
	/** string delimiters „ “ */
	public static char[] GERMAN_QUOTES = 		STRING_DELIMITERS[5];

	/**
	 * <p>Produces a String that can be saved to a text file (or any other text-based object) and later re-loaded.</p>
	 * <p>This method (and all its variants) must return a {@code String} that allows to
	 * reconstruct the object from it using a {@code valueOf(...)} method.</p>
	 * <p>The general idea is that some external class (a grammar, tokeniser or other thing
	 * aware of why the transformation to text is done) will call this method or its
	 * variants with two types of arguments, <em>block delimiters</em> and <em>item separators</em>.
	 * This way the exact set of tokens to use for a particular application is not
	 * hard-coded at this low level but is delegated to a class which contains the overall semantics.
	 * Here, we only deal with the semantics of saving/loading a particular object to/from
	 * text form.</p>
	 * <p>Block delimiters come in pairs (an opening and a closing character) while item separators
	 * are made of a single char. Item separators should be used to separate items of identical
	 * nature (e.g. cells of a table of objects of the same type). Block delimiters should
	 * be used to group things of various types together in a meaningful way.
	 * The detailed semantics of block delimiters and item separators may vary with implementations
	 * The important thing to bear in mind when writing such methods is that the generated
	 * String must enable one to reconstruct the object from it.</p>
	 * <p>The default implementation throws an Exception (i.e., it must be overriden).</p>
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
		
		throw new UnsupportedOperationException("Unimplemented method: toSaveableString(char[][] blockDelimiters,char[] itemSeparators)");
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with no block delimiters.
	 * <p>The default implementation calls {@link SaveableAsText#toSaveableString(char[][],char[]) toSaveableString(char[][],char[])} 
	 * with {@code null} blockDelimiters.</p>
	 * @param itemSeparators an array of separators between repeated items
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char[] itemSeparators) {
		return toSaveableString(null,itemSeparators);
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with no separators
	 * <p>The default implementation calls {@link SaveableAsText#toSaveableString(char[][],char[]) toSaveableString(char[][],char[])} 
	 * with {@code null} itemSeparators.</p>
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
	 * <p>The default implementation calls {@link SaveableAsText#toSaveableString(char[][],char[]) toSaveableString(char[][],char[])} 
	 * with {@code null} blockDelimiters and itemSeparators. Overwrite it if you
	 * want to use standard default delimiters and separators.</p>
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
	 * block delimiter (e.g. '&lt;') and the item 1 being the closing block delimiter
	 * (e.g. '&gt;').
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
		char[] is = new char[1];
		is[0] = itemSeparator;
		return toSaveableString(bd,is);
	}

	/**
	 * As {@code toSaveableString(char[][],char[])}, but with only one pair of block
	 * delimiters and no item separator
	 * <p>The default implementation calls {@link SaveableAsText#toSaveableString(char[][],char[]) toSaveableString(char[][],char[])} 
	 * with {@code null} itemSeparators.</p>
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
	 * <p>The default implementation calls {@link SaveableAsText#toSaveableString(char[][],char[]) toSaveableString(char[][],char[])} 
	 * with {@code null} blockDelimiters.</p>
	 * @param itemSeparator the item separator
	 * @return the object as a string that allows to reconstruct it with a {@code valueOf(...)}
	 * method
	 */
	public default String toSaveableString(char itemSeparator) {
		char[] is = new char[1];
		is[0] = itemSeparator;
		return toSaveableString(null,is);
	}


}
