package etatModif;

import partie.Controleur;
import partie.ControleurModif;
import partie.Position;

public class ModifMap implements EtatModif {

	private static ModifMap etat = new ModifMap();
	private ModifMap(){
		super();
	}
	
	
	public static EtatModif getEtat(){
		return etat;
	}


	public void clique(Position pos, ControleurModif c) {
		// TODO Auto-generated method stub
		
	}

}
