package etatModif;

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


	public void modifRocher(ControleurModif c) {
		// TODO Auto-generated method stub
		
	}


	public void modifEau(ControleurModif c) {
		// TODO Auto-generated method stub
		
	}


	public void modifPhare(ControleurModif c) {
		// TODO Auto-generated method stub
		
	}


	public void modifBase(ControleurModif c, String str) {
		// TODO Auto-generated method stub
		
	}

}
