package etatActivite;

import partie.Controleur;
import partie.Position;

public class EtatInit implements EtatAction{

	private static EtatInit e = new EtatInit();
	
	private EtatInit() {	
	}
	
	public void clique(Position pos, Controleur c) {
		//getInfo();
		
	}
	
	public static EtatInit getEtat() {
		return e;
	}

}
