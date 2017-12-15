package etatActivite;

import partie.Controleur;
import partie.Position;

public class EtatOrientation implements EtatAction {

	private static EtatOrientation e = new EtatOrientation();
	private static Position position;

	private EtatOrientation() {
	}

	public void clique(Position pos, Controleur c) {
		
		
	}

	public static EtatOrientation getEtat() {
		return e;
	}

	public static void setPos(Position pos) {
		position = pos;
		
	}

}
