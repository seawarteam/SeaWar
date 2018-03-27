package etatModif;


import javax.swing.ListModel;

import partie.Controleur;
import partie.ControleurModif;
//import partie.EditCanonS;
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

	
	public void modifMap(ControleurModif c) {
		c.setEtat(EditCarte.getEtat());
	}
	
	public void modifCanonP(ControleurModif c) {
		c.setEtat(EditCanonP.getEtat());
	}

	/*
	public void modifCanonS(ControleurModif c) {
		c.setEtat(EditCanonS.getEtat());
	}*/



	
	public void modifRocher(ControleurModif c) {
	}


	
	public void modifEau(ControleurModif c) {
	}


	
	public void modifPhare(ControleurModif c) {
	}


	
	public void modifBase(ControleurModif c, String str) {
	}


	




	
	
}

