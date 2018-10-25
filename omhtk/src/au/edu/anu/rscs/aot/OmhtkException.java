package au.edu.anu.rscs.aot;

import fr.ens.biologie.generic.Textable;

/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
// NB: this was AotException before - but Exception have to stay local to their Library
public class OmhtkException extends RuntimeException {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 4121451020638650287L;

	public OmhtkException(Textable item, String message) {
		super("[on " + item + "]\n[" + message + "]");
	}

	public OmhtkException(String message) {
		super("[" + message + "]");
	}

	public OmhtkException(Exception e) {
		super(e);
	}

	public OmhtkException(String message, Exception e) {
		super("[" + message + "]\n[original exception: " + e + "]");
		e.printStackTrace();
	}

}
