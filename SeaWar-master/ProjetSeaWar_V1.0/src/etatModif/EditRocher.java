package etatModif;

import partie.ControleurModif;
import partie.Position;

public class EditRocher implements EtatModif {
	private static EditRocher etat = new EditRocher();
	private EditRocher(){
		super();
	}
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	public void clique(Position p, ControleurModif c) {
		c.getEditeur().getMap().setCaseRocher(p);
	}
	
	public void modifMap(ControleurModif c) {
		//Sauvegarde carte
		c.setEtat(Init.getEtat());
	}

	
	public void modifCanonP(ControleurModif c) {
	}

	
	public void modifCanonS(ControleurModif c) {
	}

	
	public void modifRocher(ControleurModif c) {
	}

	
	public void modifEau(ControleurModif c) {
		c.setEtat(EditEau.getEtat());
	}

	
	public void modifPhare(ControleurModif c) {
		c.setEtat(EditPhare.getEtat());
	}

	

	
	public void modifBase(ControleurModif c, String str) {
		// TODO Auto-generated method stub
		
	}

	

}
