package etatModif;

import partie.Controleur;
import partie.ControleurModif;
import partie.Position;

public class ModifCanonP implements EtatModif {

	private static ModifCanonP etat = new ModifCanonP();
	private ModifCanonP(){
		super();
	}
	
	
	public static EtatModif getEtat(){
		return etat;
	}

	public void clique(Position pos, ControleurModif c) {
		// TODO Auto-generated method stub
		
	}

}
