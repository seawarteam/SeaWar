package etatModif;

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


	public void modifMap(ControleurModif c) {
		// TODO Auto-generated method stub
		
	}


	public void modifCanonP(ControleurModif c) {
		// TODO Auto-generated method stub
		
	}


	public void modifCanonS(ControleurModif c) {
		// TODO Auto-generated method stub
		
	}


	public void setCurrentJ(String str) {
		// TODO Auto-generated method stub
		
	}

}