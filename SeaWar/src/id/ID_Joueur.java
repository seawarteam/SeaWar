package id;

import java.io.Serializable;

public class ID_Joueur implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private static int nbID = 0;
	
	public ID_Joueur(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public static int getNewID() {
		return ++nbID;
	}
	
	public String toString() {
		return "" + id;
	}
	
	
}
