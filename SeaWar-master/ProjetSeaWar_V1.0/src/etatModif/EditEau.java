package etatModif;

import partie.ControleurModif;
import partie.EditCanonS;
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
		c.setEtat(Init.getEtat());
	}

	@Override
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
