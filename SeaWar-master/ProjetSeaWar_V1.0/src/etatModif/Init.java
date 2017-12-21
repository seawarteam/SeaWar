package etatModif;

import partie.Controleur;
import partie.ControleurModif;
import partie.Position;

public class Init implements EtatModif {
	
	private static Init etat = new Init();
	private Init(){
		super();
	}
	
	
	public static EtatModif getEtat(){
		return etat;
	}


	public void clique(Position pos, ControleurModif c) {
		// TODO Auto-generated method stub
		
	}
	
}

