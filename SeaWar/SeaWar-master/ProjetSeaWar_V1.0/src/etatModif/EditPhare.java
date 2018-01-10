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
		//Sauvegarde carte
		c.setEtat(Init.getEtat());
	}

	
	public void modifCanonP(ControleurModif c) {
	}

	
	public void modifCanonS(ControleurModif c) {
	}

	@Override
	public void modifRocher(ControleurModif c) {
		c.setEtat(EditRocher.getEtat());
	}

	@Override
	public void modifEau(ControleurModif c) {
		c.setEtat(EditEau.getEtat());
	}

	@Override
	public void modifPhare(ControleurModif c) {
	}



	@Override
	public void modifBase(ControleurModif c, String str) {
		// TODO Auto-generated method stub
		
	}

	
}
