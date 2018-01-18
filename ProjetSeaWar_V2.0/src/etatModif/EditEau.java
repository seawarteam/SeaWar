package etatModif;

import partie.ControleurModif;
//import partie.EditCanonS;
import partie.Position;

public class EditEau implements EtatModif{
	private static EditEau etat = new EditEau();
	private EditEau(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}


	public void clique(Position p, ControleurModif c) {
		c.getEditeur().getMap().ResetCouleur();
		c.getEditeur().getMap().setCaseEau(p);
		
	}
	

	public void modifCanonP(ControleurModif c) {
	}

	
	public void modifCanonS(ControleurModif c) {
	}

	
	public void modifRocher(ControleurModif c) {
		c.setEtat(EditRocher.getEtat());
	}

	
	public void modifEau(ControleurModif c) {
	}

	
	public void modifPhare(ControleurModif c) {
		c.setEtat(EditPhare.getEtat());
	}

	
	public void modifBase(ControleurModif c, String str) {
		c.setEtat(EditBase.getEtat());
		EditBase.getEtat().modifBase(c, str);
		
	}

	



	
}
