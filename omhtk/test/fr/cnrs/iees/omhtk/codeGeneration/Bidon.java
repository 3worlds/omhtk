package fr.cnrs.iees.omhtk.codeGeneration;

public class Bidon {

	int n;
	String s;
	@Override
	public String toString() {
		return n+s;
	}
	public Bidon(int n, String s) {
		super();
		this.n = n;
		this.s = s;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}		

}
