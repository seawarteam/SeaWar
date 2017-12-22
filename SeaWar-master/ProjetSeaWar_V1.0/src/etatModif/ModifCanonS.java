package etatModif;

import partie.Controleur;
import partie.ControleurModif;
import partie.Position;

public class ModifCanonS implements EtatModif {

	private static ModifCanonS etat = new ModifCanonS();
	private ModifCanonS(){
		super();
	}
	
	
	public static EtatModif getEtat(){
		return etat;
	}
	

	public void clique(Position pos, ControleurModif c) {
		// TODO Auto-generated method stub
		
	}

}
