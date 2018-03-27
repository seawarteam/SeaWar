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
	

	
	public void modifCanonP(ControleurModif c) {
	}

	
	public void modifCanonS(ControleurModif c) {
	}

	
	public void modifRocher(ControleurModif c) {
		c.setEtat(EditRocher.getEtat());
	}

	
	public void modifEau(ControleurModif c) {
		c.setEtat(EditEau.getEtat());
	}

	
	public void modifPhare(ControleurModif c) {
	}



	
	public void modifBase(ControleurModif c, String str) {
		c.setEtat(EditBase.getEtat());
		EditBase.getEtat().modifBase(c, str);
		
	}

	
}
