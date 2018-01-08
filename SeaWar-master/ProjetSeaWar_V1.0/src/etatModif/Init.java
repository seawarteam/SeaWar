package etatModif;


import javax.swing.ListModel;

import partie.Controleur;
import partie.ControleurModif;
import partie.EditCanonS;
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
		c.setEtat(EditRocher.getEtat());
	}
	
	public void modifCanonP(ControleurModif c) {
		c.setEtat(EditCanonP.getEtat());
	}


	@Override
	public void setCurrentJ(String nomJ) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void modifCanonS(ControleurModif c) {
		c.setEtat(EditCanonS.getEtat());
		
	}




	
	
}

