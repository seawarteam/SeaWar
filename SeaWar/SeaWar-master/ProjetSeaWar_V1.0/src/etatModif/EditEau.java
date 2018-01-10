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
		c.getEditeur().getMap().setCaseEau(p);
		
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
	}

	@Override
	public void modifPhare(ControleurModif c) {
		c.setEtat(EditPhare.getEtat());
	}

	@Override
	public void modifBase(ControleurModif c, String str) {
		// TODO Auto-generated method stub
		
	}

	



	
}
