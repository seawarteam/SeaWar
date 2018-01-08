package etatModif;

import partie.ControleurModif;
//import partie.EditCanonS;
import partie.Position;

public class EditPhare implements EtatModif {
	private static EditPhare etat = new EditPhare();
	private EditPhare(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	public void clique(Position p, ControleurModif c) {
		c.getEditeur().getMap().setCasePhare(p);
	}
	
	public void modifMap(ControleurModif c) {
		c.setEtat(Init.getEtat());
	}

	
	public void setCurrentJ(String str) {
		// TODO Auto-generated method stub
		
	}

	
	public void modifCanonP(ControleurModif c) {
		c.setEtat(EditCanonP.getEtat());
		
	}

	
	public void modifCanonS(ControleurModif c) {
		c.setEtat(EditCanonS.getEtat());
		
	}

	
}
