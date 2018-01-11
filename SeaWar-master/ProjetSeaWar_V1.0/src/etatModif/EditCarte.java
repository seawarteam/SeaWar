package etatModif;

import partie.ControleurModif;
import partie.Position;

public class EditCarte implements EtatModif {
	private static EditCarte etat = new EditCarte();
	private EditCarte(){
		super();
	}
	
	
	public static EtatModif getEtat(){
		return etat;
	}
	
	public void clique(Position pos, ControleurModif c) {
	}

	
	public void modifMap(ControleurModif c) {
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
		c.setEtat(EditPhare.getEtat());
	}



	
	public void modifBase(ControleurModif c, String str) {
		c.setEtat(EditBase.getEtat());
		
		
	}

}
